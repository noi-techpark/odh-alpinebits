/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.freerooms;

import java.util.List;

/**
 * The GuestRequestsReadRequest object is the counterpart
 * to the AlpineBits OTA_HotelAvailNotifRQ object.
 */
public class FreeRoomsRequest {

    private String hotelCode;

    private String hotelName;

    private UniqueId uniqueId;

    private List<AvailStatus> availStatuses;

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

    public UniqueId getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(UniqueId uniqueId) {
        this.uniqueId = uniqueId;
    }

    public List<AvailStatus> getAvailStatuses() {
        return availStatuses;
    }

    public void setAvailStatuses(List<AvailStatus> availStatuses) {
        this.availStatuses = availStatuses;
    }

    @Override
    public String toString() {
        return "FreeRoomsRequest{" +
                "hotelCode='" + hotelCode + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", uniqueId=" + uniqueId +
                ", availStatuses=" + availStatuses +
                '}';
    }
}
