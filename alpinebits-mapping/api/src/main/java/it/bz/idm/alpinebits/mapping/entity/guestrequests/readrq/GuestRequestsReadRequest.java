/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.guestrequests.readrq;

import java.time.ZonedDateTime;

/**
 * The GuestRequestsReadRequest object is the counterpart
 * to the AlpineBits OTAReadRQ object.
 */
public class GuestRequestsReadRequest {

    private String hotelCode;

    private String hotelName;

    private ZonedDateTime start;

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "GuestRequestsReadRequest{" +
                "hotelCode='" + hotelCode + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", start=" + start +
                '}';
    }
}
