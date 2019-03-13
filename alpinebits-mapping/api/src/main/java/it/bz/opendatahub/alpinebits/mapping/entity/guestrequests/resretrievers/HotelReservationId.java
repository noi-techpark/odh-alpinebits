/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers;

/**
 * This class contains various unique and non unique
 * identifiers that the trading partners associate with a given reservation.
 * <p>
 * Note, that this class does not represent any identifier for
 * {@link HotelReservation}. Instead, it provides additional information
 * (e.g. Online campaign information) for a HotelReservation.
 */
public class HotelReservationId {

    private int resIdType;

    private String resIdValue;

    private String resIdSource;

    private String resIdSourceContext;

    public int getResIdType() {
        return resIdType;
    }

    public void setResIdType(int resIdType) {
        this.resIdType = resIdType;
    }

    public String getResIdValue() {
        return resIdValue;
    }

    public void setResIdValue(String resIdValue) {
        this.resIdValue = resIdValue;
    }

    public String getResIdSource() {
        return resIdSource;
    }

    public void setResIdSource(String resIdSource) {
        this.resIdSource = resIdSource;
    }

    public String getResIdSourceContext() {
        return resIdSourceContext;
    }

    public void setResIdSourceContext(String resIdSourceContext) {
        this.resIdSourceContext = resIdSourceContext;
    }

    @Override
    public String toString() {
        return "HotelReservationId{" +
                "resIdType=" + resIdType +
                ", resIdValue='" + resIdValue + '\'' +
                ", resIdSource='" + resIdSource + '\'' +
                ", resIdSourceContext='" + resIdSourceContext + '\'' +
                '}';
    }
}
