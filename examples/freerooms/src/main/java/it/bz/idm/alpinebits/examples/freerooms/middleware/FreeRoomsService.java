/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.freerooms.middleware;

import it.bz.idm.alpinebits.common.exception.AlpineBitsException;
import it.bz.idm.alpinebits.examples.freerooms.entity.AvailStatusEntity;
import it.bz.idm.alpinebits.examples.freerooms.entity.FreeRoomsEntity;
import it.bz.idm.alpinebits.examples.freerooms.mapper.AvailStatusEntityMapperInstances;
import it.bz.idm.alpinebits.mapping.entity.Error;
import it.bz.idm.alpinebits.mapping.entity.GenericResponse;
import it.bz.idm.alpinebits.mapping.entity.Warning;
import it.bz.idm.alpinebits.mapping.entity.freerooms.AvailStatus;
import it.bz.idm.alpinebits.mapping.entity.freerooms.FreeRoomsRequest;
import it.bz.idm.alpinebits.mapping.entity.freerooms.UniqueId;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This service handles the DB persistence for
 * {@link FreeRoomsMiddleware}.
 */
public class FreeRoomsService {

    private final EntityManager em;

    public FreeRoomsService(EntityManager em) {
        this.em = em;
    }

    public GenericResponse persistFreeRooms(FreeRoomsRequest freeRoomsRequest) {
        // Return an error if rooms and categories are mixed
        if (this.hasMixedRoomAndCategoryInfo(freeRoomsRequest.getAvailStatuses())) {
            return GenericResponse.error(
                    Error.withDefaultType(
                            Error.CODE_UNABLE_TO_PROCESS,
                            "Mixing rooms and categories in a single request is not allowed"
                    )
            );
        }

        // Handle complete information
        if (this.isCompleteInformation(freeRoomsRequest.getUniqueId())) {
            return this.handleCompleteInformation(freeRoomsRequest);
        }

        // Handle delta
        if (this.isDeltaInformation(freeRoomsRequest.getUniqueId())) {
            return this.handleDeltaInformation(freeRoomsRequest);
        }

        // Default info
        return GenericResponse.warning(
                Warning.withoutRecordId(
                        Warning.NO_IMPLEMENTATION,
                        "Request was not handled by any business logic"
                )
        );
    }

    private boolean hasMixedRoomAndCategoryInfo(List<AvailStatus> availStatuses) {
        if (availStatuses == null || availStatuses.isEmpty()) {
            return false;
        }

        boolean hasSpecificRoom = false;
        boolean hasRoomCategory = false;

        for (AvailStatus availStatus : availStatuses) {
            if (availStatus.getInvCode() != null && availStatus.getInvTypeCode() != null) {
                hasRoomCategory = true;
            } else if (availStatus.getInvCode() != null) {
                hasSpecificRoom = true;
            }
        }

        return hasSpecificRoom && hasRoomCategory;
    }

    private boolean isCompleteInformation(UniqueId uniqueId) {
        // Note that this implementation handles messages with a type of 35
        // the same way it handles messages with type 16
        // (35 = purge based on internal business rules)
        return uniqueId != null
                && UniqueId.COMPLETE_SET.equals(uniqueId.getInstance())
                && ("16".equals(uniqueId.getType()) || "35".equals(uniqueId.getType()));
    }

    private boolean isDeltaInformation(UniqueId uniqueId) {
        return uniqueId == null;
    }

    private GenericResponse handleCompleteInformation(FreeRoomsRequest freeRoomsRequest) {
        String hotelCode = freeRoomsRequest.getHotelCode();
        String hotelName = freeRoomsRequest.getHotelName();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        List<FreeRoomsEntity> freeRoomsEntities = getFreeRoomsEntities(hotelCode, hotelName);

        if (freeRoomsEntities.size() == 1) {
            FreeRoomsEntity freeRoomsEntity = freeRoomsEntities.get(0);

            freeRoomsEntity.getAvailStatuses().clear();

            List<AvailStatusEntity> availStatusEntities = freeRoomsRequest.getAvailStatuses()
                    .stream()
                    .map(AvailStatusEntityMapperInstances.AVAIL_STATUS_ENTITY_MAPPER::toAvailStatusEntity)
                    .peek(availStatusEntity -> availStatusEntity.setFreeRoom(freeRoomsEntity))
                    .collect(Collectors.toList());
            freeRoomsEntities.get(0).getAvailStatuses().addAll(availStatusEntities);
        } else {
            FreeRoomsEntity freeRoomsEntity = buildNewFreeRoomsEntity(freeRoomsRequest);
            em.persist(freeRoomsEntity);
        }

        tx.commit();

        return GenericResponse.success();
    }

    private GenericResponse handleDeltaInformation(FreeRoomsRequest freeRoomsRequest) {
        String hotelCode = freeRoomsRequest.getHotelCode();
        String hotelName = freeRoomsRequest.getHotelName();

        List<FreeRoomsEntity> freeRoomsEntities = getFreeRoomsEntities(hotelCode, hotelName);

        if (freeRoomsEntities.size() == 1) {
            FreeRoomsEntity freeRoomsEntity = freeRoomsEntities.get(0);
            // Return a Warning if:
            // - request data contains only room categories but persisted data contains single rooms
            // - request data contains only single rooms but persisted data contains room categories
            // The reason is, that room categories and single rooms can not be easily matched. It is
            // a business decision, how the match should be implemented. In this case, a Warning is
            // returned
            if (this.isDeltaUpdatePossible(freeRoomsRequest.getAvailStatuses(), freeRoomsEntity.getAvailStatuses())) {
                // Build map of availStatusEntities for faster lookup
                Map<String, AvailStatusEntity> entityMap = this.buildAvailStatusEntityMap(freeRoomsEntity.getAvailStatuses());

                EntityTransaction tx = em.getTransaction();
                tx.begin();

                for (AvailStatus availStatus : freeRoomsRequest.getAvailStatuses()) {
                    String roomAndCategoryKey = this.roomAndCategoryToKey(availStatus.getInvTypeCode(), availStatus.getInvCode());

                    AvailStatusEntity statusEntity = entityMap.get(roomAndCategoryKey);
                    if (statusEntity == null) {
                        statusEntity = AvailStatusEntityMapperInstances
                                .AVAIL_STATUS_ENTITY_MAPPER
                                .toAvailStatusEntity(availStatus);
                        statusEntity.setFreeRoom(freeRoomsEntity);
                        em.persist(statusEntity);
                    } else {
                        statusEntity.setStart(availStatus.getStart());
                        statusEntity.setEnd(availStatus.getEnd());
                        statusEntity.setInvTypeCode(availStatus.getInvTypeCode());
                        statusEntity.setInvCode(availStatus.getInvCode());
                        statusEntity.setBookingLimit(availStatus.getBookingLimit());
                        statusEntity.setBookingThreshold(availStatus.getBookingThreshold());
                        statusEntity.setBookingLimitMessageType(availStatus.getBookingLimitMessageType());
                    }
                }

                tx.commit();
            } else {
                return GenericResponse.warning(
                        Warning.withoutRecordId(
                                Warning.BIZ_RULE,
                                "It is not poiss"
                        )
                );
            }
        } else {
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            FreeRoomsEntity freeRoomsEntity = buildNewFreeRoomsEntity(freeRoomsRequest);
            em.persist(freeRoomsEntity);

            tx.commit();
        }

        return GenericResponse.success();
    }

    private Map<String, AvailStatusEntity> buildAvailStatusEntityMap(List<AvailStatusEntity> availStatuses) {
        Map<String, AvailStatusEntity> entityMap = new HashMap<>();
        for (AvailStatusEntity a : availStatuses) {
            String key = this.roomAndCategoryToKey(a.getInvTypeCode(), a.getInvCode());
            entityMap.put(key, a);
        }
        return entityMap;
    }

    private boolean isDeltaUpdatePossible(
            List<AvailStatus> requestAvailStatuses,
            List<AvailStatusEntity> persistedAvailStatuses
    ) {
        if (requestAvailStatuses == null
                || requestAvailStatuses.isEmpty()
                || persistedAvailStatuses == null
                || persistedAvailStatuses.isEmpty()) {
            return true;
        }

        AvailStatus firstRequestAvailStatus = requestAvailStatuses.get(0);
        AvailStatusEntity firstPersistedAvailStatus = persistedAvailStatuses.get(0);

        boolean requestAndPersistenceHaveRoomCategory = firstRequestAvailStatus.getInvTypeCode() != null
                && firstPersistedAvailStatus.getInvTypeCode() != null;
        boolean requestAndPersistenceDontHaveRoomCategory = firstRequestAvailStatus.getInvTypeCode() == null
                && firstPersistedAvailStatus.getInvTypeCode() == null;

        return requestAndPersistenceHaveRoomCategory || requestAndPersistenceDontHaveRoomCategory;
    }

    private List<FreeRoomsEntity> getFreeRoomsEntities(String hotelCode, String hotelName) {
        List<FreeRoomsEntity> freeRoomsEntities = em.createQuery(
                "select f from FreeRoomsEntity f " +
                        "where f.hotelCode like :hotelCode " +
                        "and f.hotelName like :hotelName",
                FreeRoomsEntity.class
        )
                .setParameter("hotelCode", hotelCode)
                .setParameter("hotelName", hotelName)
                .getResultList();

        if (freeRoomsEntities.size() > 1) {
            throw new AlpineBitsException(
                    "More than one FreeRooms entries found for hotelCode " + hotelCode + " and hotelName " + hotelName,
                    500
            );
        }
        return freeRoomsEntities;
    }

    private FreeRoomsEntity buildNewFreeRoomsEntity(FreeRoomsRequest freeRoomsRequest) {
        FreeRoomsEntity freeRoomsEntity = new FreeRoomsEntity();
        freeRoomsEntity.setHotelCode(freeRoomsRequest.getHotelCode());
        freeRoomsEntity.setHotelName(freeRoomsRequest.getHotelName());

        List<AvailStatusEntity> availStatusEntities = freeRoomsRequest.getAvailStatuses()
                .stream()
                .map(AvailStatusEntityMapperInstances.AVAIL_STATUS_ENTITY_MAPPER::toAvailStatusEntity)
                .peek(availStatusEntity -> availStatusEntity.setFreeRoom(freeRoomsEntity))
                .collect(Collectors.toList());
        freeRoomsEntity.setAvailStatuses(availStatusEntities);
        return freeRoomsEntity;
    }

    private String roomAndCategoryToKey(String invTypeCode, String invType) {
        return invTypeCode + ":" + invType;
    }
}
