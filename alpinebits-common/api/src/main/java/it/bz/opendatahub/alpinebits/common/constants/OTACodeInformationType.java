/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.constants;

/**
 * Here you can find an enumeration of all OTA Information Type codes (INF).
 */
public enum OTACodeInformationType {
    DESCRIPTION(1),
    POLICY(2),
    MARKETING(3),
    SPECIAL_INSTRUCTIONS(4),
    OTHER(5),
    AMENITIES(6),
    ATTRACTIONS(7),
    AWARDS(8),
    CORPORATE_LOCATIONS(9),
    DINING(10),
    DRIVING_DIRECTIONS(11),
    FACILITIES(12),
    RECREATION(13),
    SAFETY(14),
    SERVICES(15),
    TRANSPORTATION(16),
    SHORT_DESCRIPTION(17),
    ADVISORY(18),
    GEOCODES(19),
    LOCATION(20),
    ADDRESS(21),
    CONTACT(22),
    PICTURES(23),
    DESCRIPTIVE_CONTENT(24),
    LONG_NAME(25),
    ALIAS_NAME(26);

    private final int code;

    OTACodeInformationType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static boolean isCodeDefined(int code) {
        for (OTACodeInformationType value : values()) {
            if (value.code == code) {
                return true;
            }
        }
        return false;
    }
}
