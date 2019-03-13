/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.notifreportrq;

/**
 * Each instance of this class corresponds to an acknowledge
 * entry, when a client sends read confirmations to
 * the server.
 */
public class Acknowledge {

    private Integer type;

    private String recordId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @Override
    public String toString() {
        return "Acknowledge{" +
                "type=" + type +
                ", recordId='" + recordId + '\'' +
                '}';
    }
}
