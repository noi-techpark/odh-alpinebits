/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.dbserver.middleware.guestrequests;

import it.bz.idm.alpinebits.examples.dbserver.entity.guestrequests.read.HotelReservationEntity;
import it.bz.idm.alpinebits.examples.dbserver.mapper.GuestRequestsEntityMapperInstances;
import it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers.GuestRequestsReadResponse;
import it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers.HotelReservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This service handles the DB persistence for
 * {@link GuestRequestsReadMiddleware}.
 */
public class GuestRequestsReadService {

    private static final Logger LOG = LoggerFactory.getLogger(GuestRequestsReadService.class);

    private final EntityManager em;

    public GuestRequestsReadService(EntityManager em) {
        this.em = em;
    }

    /**
     * Read GuestRequests for the given <code>clientIdentifier</code> from DB.
     *
     * @param clientIdentifier this identifier is used to read only
     *                         unconfirmed GuestRequests for the given
     *                         <code>clientIdentifier</code>
     * @return a {@link GuestRequestsReadResponse} containing the outcome of
     * this method.
     */
    public GuestRequestsReadResponse readGuestRequests(String clientIdentifier) {
        LOG.info("Reading Guest Requests for client {}", clientIdentifier);

        List<HotelReservationEntity> results = em
                .createQuery(
                        "select h from HotelReservationEntity h " +
                                "where not exists (" +
                                "  select c from ConfirmationEntity c " +
                                "  where c.id.hotelReservation = h " +
                                "  and c.id.client = :clientIdentifier" +
                                ")",
                        HotelReservationEntity.class
                )
                .setParameter("clientIdentifier", clientIdentifier)
                .getResultList();

        List<HotelReservation> hotelReservations = results.stream()
                .map(GuestRequestsEntityMapperInstances.HOTEL_RESERVATION_MAPPER::toHotelReservation)
                .collect(Collectors.toList());

        GuestRequestsReadResponse guestRequestsReadResponse = new GuestRequestsReadResponse();
        guestRequestsReadResponse.setHotelReservations(hotelReservations);
        guestRequestsReadResponse.setSuccess("");

        return guestRequestsReadResponse;
    }
}
