/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.mapper.v_2017_10.guestrequests.resretrievers;

import it.bz.idm.alpinebits.mapping.utils.CollectionUtils;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAResRetrieveRS;

/**
 * Clean parent elements inside {@link OTAResRetrieveRS}
 * objects where necessary, to adhere to the AlpineBits standard.
 */
public final class ListParentCleaner {

    private ListParentCleaner() {
        // Empty
    }

    /**
     * Do a deep check of all list elements of the given
     * {@link OTAResRetrieveRS}.
     * <p>
     * If any list element is found to be null or empty,
     * remove the necessary parent element to adhere to
     * the AlpineBits standard.
     * <p>
     * This check-and-remove is performed on OTAResRetrieveRS
     * itself and deep down on all the member variables.
     *
     * @param ota do a deep check-and-remove of all
     *            list elements for this object
     */
    public static void checkAndRemoveOTAParents(OTAResRetrieveRS ota) {
        if (ota.getErrors() != null && CollectionUtils.isNullOrEmpty(ota.getErrors().getErrors())) {
            ota.setErrors(null);
        }

        if (ota.getReservationsList() != null
                && ota.getReservationsList().getHotelReservations() != null) {
            ota.getReservationsList().getHotelReservations().forEach(hotelReservation -> {
                ListParentCleaner.checkAndRemoveParentsForOTARoomStays(hotelReservation);
                ListParentCleaner.checkAndRemoveParentsForOTAResGuests(hotelReservation);
                ListParentCleaner.checkAndRemoveParentsForOTAResGlobalInfo(hotelReservation);
            });
        }
    }

    private static void checkAndRemoveParentsForOTAResGuests(
            OTAResRetrieveRS.ReservationsList.HotelReservation otaHotelReservation
    ) {
        if (otaHotelReservation.getResGuests() == null
                || otaHotelReservation.getResGuests().getResGuest() == null
                || otaHotelReservation.getResGuests().getResGuest().getProfiles() == null
                || otaHotelReservation.getResGuests().getResGuest().getProfiles().getProfileInfo() == null
                || otaHotelReservation.getResGuests().getResGuest().getProfiles().getProfileInfo().getProfile() == null
                || otaHotelReservation.getResGuests().getResGuest().getProfiles().getProfileInfo().getProfile().getCustomer() == null) {
            otaHotelReservation.setResGuests(null);
        }
    }

    private static void checkAndRemoveParentsForOTAResGlobalInfo(
            OTAResRetrieveRS.ReservationsList.HotelReservation otaHotelReservation
    ) {
        if (otaHotelReservation.getResGlobalInfo() != null) {
            OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo resGlobalInfo = otaHotelReservation.getResGlobalInfo();

            if (resGlobalInfo.getComments() == null || CollectionUtils.isNullOrEmpty(resGlobalInfo.getComments().getComments())) {
                resGlobalInfo.setComments(null);
            }

            if (resGlobalInfo.getHotelReservationIDs() == null
                    || CollectionUtils.isNullOrEmpty(resGlobalInfo.getHotelReservationIDs().getHotelReservationIDs())) {
                resGlobalInfo.setHotelReservationIDs(null);
            }

            if (resGlobalInfo.getProfiles() == null
                    || resGlobalInfo.getProfiles().getProfileInfo() == null
                    || resGlobalInfo.getProfiles().getProfileInfo().getProfile() == null) {
                resGlobalInfo.setProfiles(null);
            }
        }
    }

    private static void checkAndRemoveParentsForOTARoomStays(
            OTAResRetrieveRS.ReservationsList.HotelReservation hotelReservation
    ) {
        if (hotelReservation.getRoomStays() != null
                && CollectionUtils.isNullOrEmpty(hotelReservation.getRoomStays().getRoomStaies())) {
            hotelReservation.setRoomStays(null);
        } else {
            hotelReservation.getRoomStays().getRoomStaies().forEach(ListParentCleaner::checkAndRemoveParentsForOTAGuestCounts);
        }
    }


    private static void checkAndRemoveParentsForOTAGuestCounts(
            OTAResRetrieveRS.ReservationsList.HotelReservation.RoomStays.RoomStay roomStay
    ) {
        if (roomStay.getGuestCounts() != null
                && CollectionUtils.isNullOrEmpty(roomStay.getGuestCounts().getGuestCounts())) {
            roomStay.setGuestCounts(null);
        }
    }
}
