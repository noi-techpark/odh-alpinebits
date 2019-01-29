/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.notifreportrq;

import java.util.List;

/**
 * The GuestRequestsConfirmationRequest object is the
 * counterpart to the AlpineBits OTANotifReportRQ object.
 */
public class GuestRequestsConfirmationRequest {

    private String success;

    private List<Acknowledge> acknowledges;

    private List<Refusal> refusals;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Acknowledge> getAcknowledges() {
        return acknowledges;
    }

    public void setAcknowledges(List<Acknowledge> acknowledges) {
        this.acknowledges = acknowledges;
    }

    public List<Refusal> getRefusals() {
        return refusals;
    }

    public void setRefusals(List<Refusal> refusals) {
        this.refusals = refusals;
    }

    @Override
    public String toString() {
        return "GuestRequestsConfirmationRequest{" +
                "success='" + success + '\'' +
                ", acknowledges=" + acknowledges +
                ", refusals=" + refusals +
                '}';
    }
}
