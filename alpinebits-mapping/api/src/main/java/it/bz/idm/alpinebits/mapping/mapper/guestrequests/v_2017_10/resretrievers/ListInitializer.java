/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.mapper.guestrequests.v_2017_10.resretrievers;

import it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers.Customer;
import it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers.GlobalInfo;
import it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers.GuestRequestsReadResponse;
import it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers.HotelReservation;

import java.util.ArrayList;

/**
 * Initialize lists inside {@link GuestRequestsReadResponse} objects
 * where necessary.
 */
public final class ListInitializer {

    private ListInitializer() {
        // Empty
    }

    /**
     * Do a deep null-check of all list elements of the given
     * {@link GuestRequestsReadResponse} and initialize any
     * list that is null.
     * <p>
     * This check-and-set is performed on GuestRequestsReadResponse
     * itself deep down on all the member variables.
     * <p>
     * There are at least two advantages when this method is applied to
     * a GuestRequestsReadResponse:
     * <ul>
     * <li>
     * the mapper result will be more uniform, because there exists always
     * an instance for any list element
     * </li>
     * <li>
     * further usage of the result object is facilitated (no null checks necessary)
     * </li>
     * </ul>.
     *
     * @param guestRequestsReadResponse do a deep check-and-set of all
     *                                   list elements for this object
     */
    public static void checkAndSetLists(GuestRequestsReadResponse guestRequestsReadResponse) {
        if (guestRequestsReadResponse.getErrors() == null) {
            guestRequestsReadResponse.setErrors(new ArrayList<>());
        }

        if (guestRequestsReadResponse.getHotelReservations() == null) {
            guestRequestsReadResponse.setHotelReservations(new ArrayList<>());
        }

        guestRequestsReadResponse.getHotelReservations().forEach(ListInitializer::checkAndSetHotelReservationLists);
    }

    private static void checkAndSetHotelReservationLists(HotelReservation hotelReservation) {
        if (hotelReservation.getRoomStays() == null) {
            hotelReservation.setRoomStays(new ArrayList<>());
        }

        hotelReservation.getRoomStays().forEach(roomStay -> {
            if (roomStay.getGuestCounts() == null) {
                roomStay.setGuestCounts(new ArrayList<>());
            }
        });

        if (hotelReservation.getCustomer() != null) {
            Customer customer = hotelReservation.getCustomer();
            if (customer.getTelephones() == null) {
                customer.setTelephones(new ArrayList<>());
            }
        }

        if (hotelReservation.getGlobalInfo() != null) {
            GlobalInfo globalInfo = hotelReservation.getGlobalInfo();
            if (globalInfo.getHotelReservationIds() == null) {
                globalInfo.setHotelReservationIds(new ArrayList<>());
            }
            if (globalInfo.getIncludedServices() == null) {
                globalInfo.setIncludedServices(new ArrayList<>());
            }
        }
    }
}
