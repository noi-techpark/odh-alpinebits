/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.inventory;

import it.bz.idm.alpinebits.mapping.entity.Error;

import java.util.List;

/**
 * The HotelDescriptiveInfoResponse for AlpineBits Inventory/Basic
 * (pull) and Inventory/HotelInfo (pull) server response.
 */
public class HotelDescriptiveInfoResponse {

    private HotelDescriptiveContent hotelDescriptiveContent;

    private String success;

    private List<Error> errors;

    public HotelDescriptiveContent getHotelDescriptiveContent() {
        return hotelDescriptiveContent;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public void setHotelDescriptiveContent(HotelDescriptiveContent hotelDescriptiveContent) {
        this.hotelDescriptiveContent = hotelDescriptiveContent;
    }

    @Override
    public String toString() {
        return "HotelDescriptiveInfoResponse{" +
                "hotelDescriptiveContent=" + hotelDescriptiveContent +
                ", success='" + success + '\'' +
                ", errors=" + errors +
                '}';
    }
}
