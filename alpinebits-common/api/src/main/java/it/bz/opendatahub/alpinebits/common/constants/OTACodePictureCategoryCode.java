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
 * Here you can find an enumeration of all OTA Picture Category Codes (PIC).
 */
public enum OTACodePictureCategoryCode {
    EXTERIOR_VIEW("1"),
    LOBBY_VIEW("2"),
    POOL_VIEW("3"),
    RESTAURANT("4"),
    HEALTH_CLUB("5"),
    GUEST_ROOM("6"),
    SUITE("7"),
    MEETING_ROOM("8"),
    BALLROOM("9"),
    GOLF_COURSE("10"),
    BEACH("11"),
    SPA("12"),
    BAR_LOUNGE("13"),
    RECREATIONAL_FACILITY("14"),
    LOGO("15"),
    BASICS("16"),
    MAP("17"),
    PROMOTIONAL("18"),
    HOT_NEWS("19"),
    MISCELLANEOUS("20"),
    GUEST_ROOM_AMENITY("21"),
    PROPERTY_AMENITY("22"),
    BUSINESS_CENTER("23");

    private final String code;

    OTACodePictureCategoryCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static boolean isCodeDefined(String code) {
        for (OTACodePictureCategoryCode value : values()) {
            if (value.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
