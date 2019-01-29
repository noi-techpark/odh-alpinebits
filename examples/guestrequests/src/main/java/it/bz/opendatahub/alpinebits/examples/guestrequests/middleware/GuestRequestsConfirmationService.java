/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.guestrequests.middleware;

import it.bz.opendatahub.alpinebits.examples.guestrequests.entity.read.HotelReservationEntity;
import it.bz.opendatahub.alpinebits.examples.guestrequests.entity.confirmation.ConfirmationEntity;
import it.bz.opendatahub.alpinebits.examples.guestrequests.entity.confirmation.ConfirmationEntityId;
import it.bz.opendatahub.alpinebits.mapping.entity.Error;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.notifreportrq.GuestRequestsConfirmationRequest;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.notifreportrs.GuestRequestsConfirmationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 * This service handles the DB persistence for
 * {@link GuestRequestsConfirmationMiddleware}.
 */
public class GuestRequestsConfirmationService {

    private static final Logger LOG = LoggerFactory.getLogger(GuestRequestsConfirmationService.class);

    private final EntityManager em;

    public GuestRequestsConfirmationService(EntityManager em) {
        this.em = em;
    }

    /**
     * Persist GuestRequest confirmations into DB.
     * <p>
     * A GuestRequest confirmation is either acknowledged or refused.
     * Both outcomes are handled by this method and appropriate
     * values persisted to the DB.
     *
     * @param clientIdentifier                 this identifier is stored along with
     *                                         the confirmations to ensure, that only
     *                                         unconfirmed GuestRequests are returned
     *                                         for the given <code>clientIdentifier</code>
     * @param confirmationRequest contains the confirmation data
     * @return a {@link GuestRequestsConfirmationResponse} containing the outcome of
     * this method. Possible errors are contained inside this response
     */
    public GuestRequestsConfirmationResponse persistGuestRequestConfirmations(
            String clientIdentifier,
            GuestRequestsConfirmationRequest confirmationRequest
    ) {
        int confirmationCount = confirmationRequest.getAcknowledges().size() + confirmationRequest.getRefusals().size();
        LOG.info("Writing {} Guest Request confirmations for client {}", confirmationCount, clientIdentifier);

        // List of persistence errors
        List<Error> errors = new ArrayList<>();

        // Begin transaction
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // Persist acknowledgements
        List<Error> acknowledgementPersistenceErrors = this.persistAcknowledgements(em, clientIdentifier, confirmationRequest);
        // Add persistence errors to error list
        errors.addAll(acknowledgementPersistenceErrors);

        // Persist refusals
        List<Error> refusalPersistenceErrors = this.persistRefusals(em, clientIdentifier, confirmationRequest);
        // Add persistence errors to error list
        errors.addAll(refusalPersistenceErrors);

        // Commit transaction
        tx.commit();

        // Prepare response
        GuestRequestsConfirmationResponse response = new GuestRequestsConfirmationResponse();

        if (errors.isEmpty()) {
            // If no persistence errors occurred, set success flag
            response.setSuccess("");
        } else {
            // If persistence errors occurred, set errors in response
            response.setErrors(errors);
        }

        return response;
    }

    private List<Error> persistAcknowledgements(
            EntityManager em,
            String clientIdentifier,
            GuestRequestsConfirmationRequest guestRequestsConfirmationRequest
    ) {
        List<Error> errors = new ArrayList<>();

        guestRequestsConfirmationRequest
                .getAcknowledges()
                .forEach(acknowledge -> {
                    HotelReservationEntity hotelReservationEntity = em.find(HotelReservationEntity.class, acknowledge.getRecordId());

                    if (hotelReservationEntity == null) {
                        Error error = this.buildErrorForUnknownHotelReservationEntity(acknowledge.getRecordId());
                        errors.add(error);
                        return;
                    }

                    ConfirmationEntity confirmationEntity = initConfirmationEntity(em, hotelReservationEntity, clientIdentifier);
                    confirmationEntity.setAcknowledged(true);
                    confirmationEntity.setRefusalType(null);
                    confirmationEntity.setRefusalCode(null);
                    confirmationEntity.setRefusalContent(null);
                });

        return errors;
    }

    private List<Error> persistRefusals(
            EntityManager em,
            String clientIdentifier,
            GuestRequestsConfirmationRequest guestRequestsConfirmationRequest
    ) {
        List<Error> errors = new ArrayList<>();

        guestRequestsConfirmationRequest
                .getRefusals()
                .forEach(refusal -> {
                    HotelReservationEntity hotelReservationEntity = em.find(HotelReservationEntity.class, refusal.getRecordId());

                    if (hotelReservationEntity == null) {
                        Error error = this.buildErrorForUnknownHotelReservationEntity(refusal.getRecordId());
                        errors.add(error);
                        return;
                    }

                    ConfirmationEntity confirmationEntity = initConfirmationEntity(em, hotelReservationEntity, clientIdentifier);
                    confirmationEntity.setAcknowledged(false);
                    confirmationEntity.setRefusalType(refusal.getType());
                    confirmationEntity.setRefusalCode(refusal.getCode());
                    confirmationEntity.setRefusalContent(refusal.getContent());
                });
        return errors;
    }

    private Error buildErrorForUnknownHotelReservationEntity(String hotelReservationId) {
        String message = "Hotel reservation with ID " + hotelReservationId + " not found";
        LOG.warn(message);
        return Error.withDefaultType(450, message);
    }

    private ConfirmationEntity initConfirmationEntity(EntityManager em, HotelReservationEntity hotelReservationEntity, String clientIdentifier) {
        ConfirmationEntityId id = new ConfirmationEntityId();
        id.setHotelReservation(hotelReservationEntity);
        id.setClient(clientIdentifier);

        ConfirmationEntity confirmationEntity = em.find(ConfirmationEntity.class, id);
        if (confirmationEntity == null) {
            confirmationEntity = new ConfirmationEntity();
            confirmationEntity.setId(id);
            em.persist(confirmationEntity);
        } else {
            LOG.warn("Hotel reservation with ID {} already confirmed for client {}", hotelReservationEntity.getId(), clientIdentifier);
        }

        return confirmationEntity;
    }
}
