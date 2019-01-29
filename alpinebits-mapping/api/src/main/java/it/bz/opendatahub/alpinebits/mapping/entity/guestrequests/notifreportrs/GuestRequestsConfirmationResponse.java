/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.notifreportrs;

import it.bz.opendatahub.alpinebits.mapping.entity.Error;

import java.util.List;

/**
 * The GuestRequestsConfirmationResponse object is the
 * counterpart to the AlpineBits OTANotifReportRS object.
 */
public class GuestRequestsConfirmationResponse {

    private String success;

    private List<Error> errors;

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

    @Override
    public String toString() {
        return "GuestRequestsConfirmationResponse{" +
                "success='" + success + '\'' +
                ", errors=" + errors +
                '}';
    }
}
