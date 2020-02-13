/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity;

/**
 * Status for {@link Error} and {@link Warning}.
 */
public final class Status {
    public static final String ALPINEBITS_SEND_FREEROOMS = "ALPINEBITS_SEND_FREEROOMS";
    public static final String ALPINEBITS_SEND_RATEPLANS = "ALPINEBITS_SEND_RATEPLANS";
    public static final String ALPINEBITS_SEND_INVENTORY = "ALPINEBITS_SEND_INVENTORY";

    private Status() {
        // Empty
    }
}
