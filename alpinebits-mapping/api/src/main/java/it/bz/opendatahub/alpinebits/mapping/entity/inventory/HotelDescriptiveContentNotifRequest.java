/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.inventory;

/**
 * The HotelDescriptiveContentNotifRequest for AlpineBits Inventory/Basic
 * (push) and Inventory/HotelInfo (push) client requests.
 */
public class HotelDescriptiveContentNotifRequest {

    private HotelDescriptiveContent hotelDescriptiveContent;

    public HotelDescriptiveContent getHotelDescriptiveContent() {
        return hotelDescriptiveContent;
    }

    public void setHotelDescriptiveContent(HotelDescriptiveContent hotelDescriptiveContent) {
        this.hotelDescriptiveContent = hotelDescriptiveContent;
    }

    @Override
    public String toString() {
        return "HotelDescriptiveContentNotifRequest{" +
                "hotelDescriptiveContent=" + hotelDescriptiveContent +
                '}';
    }
}
