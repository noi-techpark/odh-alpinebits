/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.common.constants;

/**
 * This enum defines all housekeeping actions.
 */
public enum HousekeepingActionEnum {

    /**
     * A client queries the server capabilities.
     */
    GET_CAPABLILITIES("getCapabilities"),

    /**
     * A client queries the server version.
     */
    GET_VERSION("getVersion");

    private final String action;

    HousekeepingActionEnum(final String action) {
        this.action = action;
    }

    /**
     * Returns the string representation of the current instance
     * of {@link HousekeepingActionEnum}.
     *
     * @return string representation of the current instance
     * of {@link HousekeepingActionEnum}.
     */
    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return action;
    }

}
