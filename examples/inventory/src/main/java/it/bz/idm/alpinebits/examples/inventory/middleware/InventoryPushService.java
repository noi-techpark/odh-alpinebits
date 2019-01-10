/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.inventory.middleware;

import it.bz.idm.alpinebits.common.exception.AlpineBitsException;
import it.bz.idm.alpinebits.examples.inventory.entity.HotelDescriptiveContentEntity;
import it.bz.idm.alpinebits.examples.inventory.entity.ImageItemEntity;
import it.bz.idm.alpinebits.examples.inventory.entity.RoomCategoryEntity;
import it.bz.idm.alpinebits.examples.inventory.mapper.HotelDescriptiveContentEntityMapperInstances;
import it.bz.idm.alpinebits.examples.inventory.mapper.ImageItemEntityMapperInstances;
import it.bz.idm.alpinebits.mapping.entity.GenericResponse;
import it.bz.idm.alpinebits.mapping.entity.Warning;
import it.bz.idm.alpinebits.mapping.entity.inventory.GuestRoom;
import it.bz.idm.alpinebits.mapping.entity.inventory.HotelDescriptiveContent;
import it.bz.idm.alpinebits.mapping.entity.inventory.HotelDescriptiveContentNotifRequest;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This service handles the DB persistence for
 * {@link InventoryPushMiddleware}.
 */
public class InventoryPushService {

    private final EntityManager em;

    public InventoryPushService(EntityManager em) {
        this.em = em;
    }

    public GenericResponse writeBasic(HotelDescriptiveContentNotifRequest hotelDescriptiveContentNotifRequest) {
        String hotelCode = hotelDescriptiveContentNotifRequest.getHotelDescriptiveContent().getHotelCode();
        String hotelName = hotelDescriptiveContentNotifRequest.getHotelDescriptiveContent().getHotelName();

        // If no guest rooms are defined, delete all guest rooms
        // for the given hotelCode and hotelName and return
        if (hotelDescriptiveContentNotifRequest.getHotelDescriptiveContent().getGuestRooms().isEmpty()) {
            this.deleteAllRoomCategories(hotelCode, hotelName);
            return GenericResponse.success();
        }

        // Build HotelDescriptiveContentEntity from request
        HotelDescriptiveContentEntity receivedHotelDescriptiveContentEntity = HotelDescriptiveContentEntityMapperInstances
                .HOTEL_DESCRIPTIVE_CONTENT_MAPPER
                .toHotelDescriptiveContentEntity(hotelDescriptiveContentNotifRequest.getHotelDescriptiveContent());

        // Try to find HotelDescriptiveContentEntity for given hotelCode and hotelName
        Optional<HotelDescriptiveContentEntity> persistedHotelDescriptiveContentEntity
                = this.findHotelDescriptiveContentEntities(hotelCode, hotelName);

        // If HotelDescriptiveContentEntity for hotelCode and hotelName is found,
        // then iterate through all room categories and update categories and rooms
        if (persistedHotelDescriptiveContentEntity.isPresent()) {
            this.updateHotelDescriptiveContentEntity(
                    persistedHotelDescriptiveContentEntity.get(),
                    receivedHotelDescriptiveContentEntity
            );
            return GenericResponse.success();
        }

        // No HotelDescriptiveContentEntity for hotelCode and hotelName found,
        // so just store the data and return
        this.persistAll(receivedHotelDescriptiveContentEntity);
        return GenericResponse.success();
    }

    public GenericResponse writeHotelInfo(HotelDescriptiveContentNotifRequest hotelDescriptiveContentNotifRequest) {
        String hotelCode = hotelDescriptiveContentNotifRequest.getHotelDescriptiveContent().getHotelCode();
        String hotelName = hotelDescriptiveContentNotifRequest.getHotelDescriptiveContent().getHotelName();

        // Try to find HotelDescriptiveContentEntity for given hotelCode and hotelName
        Optional<HotelDescriptiveContentEntity> persistedHotelDescriptiveContentEntity
                = this.findHotelDescriptiveContentEntities(hotelCode, hotelName);

        // If no HotelDescriptiveContentEntity was found, return a warning
        if (!persistedHotelDescriptiveContentEntity.isPresent()) {
            return GenericResponse.warning(
                    Warning.withoutRecordId(
                            Warning.UNKNOWN,
                            "No entry found for HotelCode " + hotelCode + " and HotelName " + hotelName
                    )
            );
        } else {
            List<Warning> warnings = this.updateHotelInfo(
                    persistedHotelDescriptiveContentEntity.get(),
                    hotelDescriptiveContentNotifRequest.getHotelDescriptiveContent()
            );

            return GenericResponse.warning(warnings);
        }
    }

    private void deleteAllRoomCategories(String hotelCode, String hotelName) {
        // Find HotelDescriptiveContentEntity for hotelCode and hotelName defined in parameter
        this.findHotelDescriptiveContentEntities(hotelCode, hotelName)
                .ifPresent(hotelDescriptiveContentEntity -> {
                    EntityTransaction tx = em.getTransaction();
                    tx.begin();
                    hotelDescriptiveContentEntity.getRoomCategories().clear();
                    tx.commit();
                });
    }

    private Optional<HotelDescriptiveContentEntity> findHotelDescriptiveContentEntities(String hotelCode, String hotelName) {
        List<HotelDescriptiveContentEntity> entities = em.createQuery(
                "select h from HotelDescriptiveContentEntity h " +
                        "where h.hotelCode like :hotelCode " +
                        "and h.hotelName like :hotelName",
                HotelDescriptiveContentEntity.class
        )
                .setParameter("hotelCode", hotelCode)
                .setParameter("hotelName", hotelName)
                .getResultList();

        // If more than one entry was found, something is wrong
        if (entities.size() > 1) {
            throw new AlpineBitsException(
                    "More than one entries found for hotelCode " + hotelCode + " and hotelName " + hotelName,
                    500
            );
        }

        return entities.isEmpty() ? Optional.empty() : Optional.of(entities.get(0));
    }

    private void persistAll(HotelDescriptiveContentEntity hotelDescriptiveContentEntity) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(hotelDescriptiveContentEntity);
        tx.commit();
    }

    private void updateHotelDescriptiveContentEntity(
            HotelDescriptiveContentEntity persistedEntity,
            HotelDescriptiveContentEntity receivedEntity
    ) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // Build a map of persisted room category codes for faster lookup
        Map<String, RoomCategoryEntity> persistedRoomCategoryCodes = persistedEntity.getRoomCategories()
                .stream()
                .collect(Collectors.toMap(RoomCategoryEntity::getCode, Function.identity()));

        for (RoomCategoryEntity roomCategory : receivedEntity.getRoomCategories()) {
            // Try to find persisted room category with same code as current room category
            RoomCategoryEntity persistedRoomCategory = persistedRoomCategoryCodes.get(roomCategory.getCode());

            if (persistedRoomCategory != null) {
                // If there is a persisted room category with the same code as the current room category,
                // simply replace the persisted room category's data
                this.updateRoomCategory(persistedRoomCategory, roomCategory);
            } else if (roomCategory.getGuestRoomId() != null) {
                // If there is no persisted room category with the same code as from the received room category,
                // but there is a guestroom ID ("ID" on element "GuestRoom" in AlpineBits schema), search for
                // persisted room category with that ID
                persistedRoomCategory = persistedRoomCategoryCodes.get(roomCategory.getGuestRoomId());
                if (persistedRoomCategory != null) {
                    // If there is a persisted room category with the code identical to the ID of
                    // the current room category, then:
                    // a) update the persisted room category's code with the current room category code
                    // b) replace the persisted room category's data
                    persistedRoomCategory.setCode(roomCategory.getCode());
                    this.updateRoomCategory(persistedRoomCategory, roomCategory);
                }
            } else {
                persistedEntity.getRoomCategories().add(roomCategory);
            }
        }

        tx.commit();
    }

    private void updateRoomCategory(RoomCategoryEntity roomCategory, RoomCategoryEntity updates) {
        roomCategory.setMinOccupancy(updates.getMinOccupancy());
        roomCategory.setMaxOccupancy(updates.getMaxOccupancy());
        roomCategory.setMaxChildOccupancy(updates.getMaxChildOccupancy());
        roomCategory.setTypeRoom(updates.getTypeRoom());

        roomCategory.getRooms().clear();
        roomCategory.getRooms().addAll(updates.getRooms());

        roomCategory.getRoomAmenityCodes().clear();
        roomCategory.getRoomAmenityCodes().addAll(updates.getRoomAmenityCodes());

        roomCategory.getLongNames().clear();
        roomCategory.getLongNames().addAll(updates.getLongNames());

        roomCategory.getDescriptions().clear();
        roomCategory.getDescriptions().addAll(updates.getDescriptions());

        roomCategory.getPictures().clear();
        roomCategory.getPictures().addAll(updates.getPictures());

        roomCategory.getHotelInfoPictures().clear();
        roomCategory.getHotelInfoPictures().addAll(updates.getHotelInfoPictures());
    }

    private List<Warning> updateHotelInfo(
            HotelDescriptiveContentEntity persistedEntity,
            HotelDescriptiveContent receivedContent
    ) {
        List<Warning> warnings = new ArrayList<>();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // Build a map of persisted room category codes for faster lookup
        Map<String, RoomCategoryEntity> persistedRoomCategoryCodes = persistedEntity.getRoomCategories()
                .stream()
                .collect(Collectors.toMap(RoomCategoryEntity::getCode, Function.identity()));

        for (GuestRoom guestRoom : receivedContent.getGuestRooms()) {
            // Try to find persisted room category with same code as current room category
            RoomCategoryEntity persistedRoomCategory = persistedRoomCategoryCodes.get(guestRoom.getCode());

            if (persistedRoomCategory == null) {
                // Add a warning, if there is no persisted room category with the same code as the guestRoom
                warnings.add(
                        Warning.withoutRecordId(
                                Warning.UNKNOWN,
                                "No GuestRoom with code " + guestRoom.getCode() + " found"
                        )
                );
            } else {
                // If there is a persisted room category with the same code as the guestRoom,
                // replace the persisted room category hotelInfo with the new data
                List<ImageItemEntity> imageItemEntity = guestRoom.getHotelInfoPictures()
                        .stream()
                        .map(ImageItemEntityMapperInstances.IMAGE_ITEM_ENTITY_MAPPER::toImageItemEntity)
                        .collect(Collectors.toList());

                persistedRoomCategory.getHotelInfoPictures().clear();
                persistedRoomCategory.getHotelInfoPictures().addAll(imageItemEntity);
            }
        }

        tx.commit();

        return warnings;
    }

}
