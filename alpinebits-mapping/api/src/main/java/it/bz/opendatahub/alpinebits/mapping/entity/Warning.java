/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity;

/**
 * A general error object that contains information
 * about an error.
 */
public class Warning {

    public static final int UNKNOWN = 1;
    public static final int NO_IMPLEMENTATION = 2;
    public static final int BIZ_RULE = 3;
    public static final int AUTHENTICATION = 4;
    public static final int AUTHENTICATION_TIMEOUT = 5;
    public static final int AUTHORIZATION = 6;
    public static final int PROTOCOL_VIOLATION = 7;
    public static final int TRANSACTION_MODEL = 8;
    public static final int AUTHENTICAL_MODEL = 9;
    public static final int REQUIRED_FIELD_MISSING = 10;
    public static final int ADVISORY = 11;
    public static final int PROCESSING_EXCEPTION = 12;
    public static final int APPLICATION_ERROR = 13;

    private String content;

    private Integer type;

    private String recordId;

    public static Warning withoutRecordId(Integer type, String content) {
        Warning warning = new Warning();
        warning.setType(type);
        warning.setContent(content);
        warning.setRecordId(null);
        return warning;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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
        return "Warning{" +
                "content='" + content + '\'' +
                ", type=" + type +
                ", recordId='" + recordId + '\'' +
                '}';
    }
}
