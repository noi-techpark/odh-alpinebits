/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers;

/**
 * This enum defines the valid reservation status
 * for a {@link HotelReservation}.
 */
public enum ReservationStatus {
    REQUESTED("Requested"),
    RESERVED("Reserved"),
    MODIFY("Modify"),
    CANCELLED("Cancelled");

    private final String value;

    ReservationStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Try to find a matching ReservationStatus value for the
     * given String <code>s</code>.
     * <p>
     * The match is searched using the {@link ReservationStatus#toString()}
     * method. If a match is found, the corresponding ReservationStatus
     * is returned. If no match is found, <code>null</code> is returned.
     *
     * @param s string used to find a matching ReservationStatus
     * @return ReservationStatus whose toString() result matches
     * the parameter <code>s</code>, null if no such match could be found
     */
    public static ReservationStatus fromString(String s) {
        if (s == null) {
            return null;
        }
        for (ReservationStatus reservationStatus : ReservationStatus.values()) {
            if (s.equals(reservationStatus.toString())) {
                return reservationStatus;
            }
        }
        return null;
    }
}
