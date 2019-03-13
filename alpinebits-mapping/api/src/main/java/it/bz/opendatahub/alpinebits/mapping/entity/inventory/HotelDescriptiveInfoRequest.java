/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.inventory;

/**
 * The HotelDescriptiveInfoRequest for AlpineBits Inventory/Basic
 * (pull) and Inventory/HotelInfo (pull) client requests.
 */
public class HotelDescriptiveInfoRequest {

    private String hotelCode;

    private String hotelName;

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

    @Override
    public String toString() {
        return "HotelDescriptiveInfoRequest{" +
                "hotelCode='" + hotelCode + '\'' +
                ", hotelName='" + hotelName + '\'' +
                '}';
    }
}
