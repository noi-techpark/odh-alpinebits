/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.routing.constants;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsCapability;

import java.util.Objects;

/**
 * This class provides routing constants for all known AlpineBits request actions.
 * <p>
 * Unfortunately, the term "action" has many meanings in AlpineBits. In the
 * context of this class, an action corresponds to the action name in the routing
 * configuration, e.g. "action_OTA_Ping" for the Handshaking request.
 */
public final class ActionName {

    // Handshaking
    public static final ActionName HANDSHAKING = of(AlpineBitsCapability.HANDSHAKING);

    // Housekeeping
    public static final ActionName GET_VERSION = of(AlpineBitsCapability.GET_VERSION);
    public static final ActionName GET_CAPABILITIES = of(AlpineBitsCapability.GET_CAPABILITIES);

    // FreeRooms
    public static final ActionName FREE_ROOMS_HOTEL_AVAIL_NOTIF = of(AlpineBitsCapability.FREE_ROOMS_HOTEL_AVAIL_NOTIF);

    // GuestRequests
    public static final ActionName GUEST_REQUESTS_READ = of(AlpineBitsCapability.GUEST_REQUESTS_READ);
    public static final ActionName GUEST_REQUESTS_WRITE = of(AlpineBitsCapability.GUEST_REQUESTS_WRITE);

    // Inventory
    public static final ActionName INVENTORY_BASIC_PUSH =
            of(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_INVENTORY);
    public static final ActionName INVENTORY_BASIC_PULL =
            of(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_INFO_INVENTORY);
    public static final ActionName INVENTORY_HOTEL_INFO_PUSH =
            of(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_INFO);
    public static final ActionName INVENTORY_HOTEL_INFO_PULL =
            of(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_INFO_INFO);

    // RatePlans
    public static final ActionName RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_RATE_PLANS =
            of(AlpineBitsCapability.RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_RATE_PLANS);

    // BaseRates
    public static final ActionName BASE_RATES_HOTEL_RATE_PLAN_BASE_RATES =
            of(AlpineBitsCapability.BASE_RATES_HOTEL_RATE_PLAN_BASE_RATES);

    private String value;

    private ActionName() {
        // Empty
    }

    public static ActionName of(String value) {
        ActionName result = new ActionName();
        result.value = value;
        return result;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActionName that = (ActionName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ActionName{" +
                "value='" + value + '\'' +
                '}';
    }
}
