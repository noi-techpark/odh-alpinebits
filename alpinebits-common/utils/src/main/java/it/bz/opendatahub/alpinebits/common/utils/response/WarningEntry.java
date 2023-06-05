// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.utils.response;

import it.bz.opendatahub.alpinebits.common.constants.OTACodeErrorWarningType;

import java.util.Objects;

/**
 * Use this class as parameter for some those methods in {@link MessageAcknowledgementTypeBuilder}
 * that build Warning outcomes.
 */
public final class WarningEntry {

    private final String message;
    private final Type type;

    public WarningEntry(String message, Type type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WarningEntry that = (WarningEntry) o;
        return Objects.equals(message, that.message) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, type);
    }

    @Override
    public String toString() {
        return "WarningEntry{" +
                "message='" + message + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    /**
     * This enum represents a subset of all OTA Error Warning Type codes (EWT)
     * that are allowed to be used for AlpineBits Warning outcomes.
     */
    public enum Type {
        UNKNOWN(OTACodeErrorWarningType.UNKNOWN),
        NO_IMPLEMENTATION(OTACodeErrorWarningType.NO_IMPLEMENTATION),
        BIZ_RULE(OTACodeErrorWarningType.BIZ_RULE),
        AUTHENTICATION(OTACodeErrorWarningType.AUTHENTICATION),
        AUTHENTICATION_TIMEOUT(OTACodeErrorWarningType.AUTHENTICATION_TIMEOUT),
        AUTHORIZATION(OTACodeErrorWarningType.AUTHORIZATION),
        PROTOCOL_VIOLATION(OTACodeErrorWarningType.PROTOCOL_VIOLATION),
        TRANSACTION_MODEL(OTACodeErrorWarningType.TRANSACTION_MODEL),
        AUTHENTICAL_MODEL(OTACodeErrorWarningType.AUTHENTICAL_MODEL),
        REQUIRED_FIELD_MISSING(OTACodeErrorWarningType.REQUIRED_FIELD_MISSING),
        PROCESSING_EXCEPTION(OTACodeErrorWarningType.PROCESSING_EXCEPTION),
        APPLICATION_ERROR(OTACodeErrorWarningType.APPLICATION_ERROR);

        private final String code;

        Type(OTACodeErrorWarningType errorWarningType) {
            this.code = errorWarningType.getCode();
        }

        public String getCode() {
            return code;
        }
    }

}