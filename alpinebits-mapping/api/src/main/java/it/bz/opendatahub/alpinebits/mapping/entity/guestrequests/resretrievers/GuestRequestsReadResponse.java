/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers;

import it.bz.opendatahub.alpinebits.mapping.entity.Error;
import it.bz.opendatahub.alpinebits.mapping.entity.Warning;

import java.util.List;

/**
 * The GuestRequestsReadResponse object is the counterpart
 * to the AlpineBits the OTAResRetrieveRS object.
 */
public class GuestRequestsReadResponse {

    private String success;

    private List<Error> errors;

    private List<Warning> warnings;

    private List<HotelReservation> hotelReservations;

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

    public List<Warning> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Warning> warnings) {
        this.warnings = warnings;
    }

    public List<HotelReservation> getHotelReservations() {
        return hotelReservations;
    }

    public void setHotelReservations(List<HotelReservation> hotelReservations) {
        this.hotelReservations = hotelReservations;
    }

    @Override
    public String toString() {
        return "GuestRequestsReadResponse{" +
                "success='" + success + '\'' +
                ", errors=" + errors +
                ", warnings=" + warnings +
                ", hotelReservations=" + hotelReservations +
                '}';
    }
}
