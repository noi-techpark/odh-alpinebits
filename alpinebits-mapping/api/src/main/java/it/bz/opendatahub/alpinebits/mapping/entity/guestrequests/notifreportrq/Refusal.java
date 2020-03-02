/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.notifreportrq;

/**
 * Each instance of this class corresponds to a refusal
 * entry, when a client sends read confirmations to
 * the server.
 */
public class Refusal {

    private Integer type;

    private Integer code;

    private String content;

    private String recordId;

    private String status;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Refusal{" +
                "type=" + type +
                ", code=" + code +
                ", content='" + content + '\'' +
                ", recordId='" + recordId + '\'' +
                ", status=" + status +
                '}';
    }
}
