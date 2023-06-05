// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.constants;

/**
 * Here you can find an enumeration of all OTA Error Warning Type codes (EWT).
 */
public enum OTACodeErrorWarningType {
    UNKNOWN("1"),
    NO_IMPLEMENTATION("2"),
    BIZ_RULE("3"),
    AUTHENTICATION("4"),
    AUTHENTICATION_TIMEOUT("5"),
    AUTHORIZATION("6"),
    PROTOCOL_VIOLATION("7"),
    TRANSACTION_MODEL("8"),
    AUTHENTICAL_MODEL("9"),
    REQUIRED_FIELD_MISSING("10"),
    ADVISORY("11"),
    PROCESSING_EXCEPTION("12"),
    APPLICATION_ERROR("13");

    private final String code;

    OTACodeErrorWarningType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static boolean isCodeDefined(String code) {
        for (OTACodeErrorWarningType value : values()) {
            if (value.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
