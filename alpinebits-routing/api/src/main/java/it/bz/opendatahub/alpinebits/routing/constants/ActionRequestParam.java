/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.routing.constants;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;

import java.util.Objects;

/**
 * This class provides routing constants for all known AlpineBits request actions.
 * <p>
 * Unfortunately, the term "action" has many meanings in AlpineBits. In the
 * context of this class, an action corresponds to the action name in the routing
 * configuration, e.g. "action_OTA_Ping" for the Handshaking request.
 */
public final class ActionRequestParam {

    // Handshaking
    public static final ActionRequestParam HANDSHAKING = of(AlpineBitsAction.HANDSHAKING);

    // Housekeeping
    public static final ActionRequestParam GET_VERSION = of(AlpineBitsAction.GET_VERSION);
    public static final ActionRequestParam GET_CAPABILITIES = of(AlpineBitsAction.GET_CAPABILITIES);

    // FreeRooms
    public static final ActionRequestParam FREE_ROOMS_HOTEL_AVAIL_NOTIF_FREE_ROOMS =
            of(AlpineBitsAction.FREE_ROOMS_HOTEL_AVAIL_NOTIF_FREE_ROOMS);

    // GuestRequests
    public static final ActionRequestParam GUEST_REQUESTS_READ_GUEST_REQUESTS =
            of(AlpineBitsAction.GUEST_REQUESTS_READ_GUEST_REQUESTS);
    public static final ActionRequestParam GUEST_REQUESTS_CONFIRM_GUEST_REQUESTS_READ =
            of(AlpineBitsAction.GUEST_REQUESTS_NOTIF_REPORT_GUEST_REQUESTS);
    public static final ActionRequestParam GUEST_REQUESTS_WRITE_GUEST_REQUESTS =
            of(AlpineBitsAction.GUEST_REQUESTS_WRITE_GUEST_REQUESTS);

    // Inventory
    public static final ActionRequestParam INVENTORY_BASIC_PUSH = of(AlpineBitsAction.INVENTORY_BASIC_PUSH);
    public static final ActionRequestParam INVENTORY_BASIC_PULL = of(AlpineBitsAction.INVENTORY_BASIC_PULL);
    public static final ActionRequestParam INVENTORY_HOTEL_INFO_PUSH = of(AlpineBitsAction.INVENTORY_HOTEL_INFO_PUSH);
    public static final ActionRequestParam INVENTORY_HOTEL_INFO_PULL = of(AlpineBitsAction.INVENTORY_HOTEL_INFO_PULL);

    // RatePlans
    public static final ActionRequestParam RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_RATE_PLANS =
            of(AlpineBitsAction.RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_RATE_PLANS);

    // BaseRates
    public static final ActionRequestParam BASE_RATES_HOTEL_RATE_PLAN_BASE_RATES =
            of(AlpineBitsAction.BASE_RATES_HOTEL_RATE_PLAN_BASE_RATES);

    private String value;

    private ActionRequestParam() {
        // Empty
    }

    public static ActionRequestParam of(String value) {
        ActionRequestParam result = new ActionRequestParam();
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
        ActionRequestParam that = (ActionRequestParam) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ActionRequestParam{" +
                "value='" + value + '\'' +
                '}';
    }
}
