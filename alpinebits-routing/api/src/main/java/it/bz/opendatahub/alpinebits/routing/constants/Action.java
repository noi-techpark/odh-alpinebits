/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.routing.constants;


import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsCapability;

import java.util.Objects;

/**
 * This class provides routing constants for all known AlpineBits actions used as
 * action name in the routing configuration.
 * <p>
 * Unfortunately, the term "action" has many meanings in AlpineBits. In the
 * context of this class, an action corresponds to the action name in the routing
 * configuration, e.g. "action_OTA_Ping" for the Handshaking request.
 */
public final class Action {

    // Handshaking
    public static final Action HANDSHAKING = of(
            AlpineBitsAction.HANDSHAKING,
            AlpineBitsCapability.HANDSHAKING
    );

    // Housekeeping
    public static final Action GET_VERSION = of(
            AlpineBitsAction.GET_VERSION,
            AlpineBitsCapability.GET_VERSION
    );
    public static final Action GET_CAPABILITIES = of(
            AlpineBitsAction.GET_CAPABILITIES,
            AlpineBitsCapability.GET_CAPABILITIES
    );

    // FreeRooms
    public static final Action FREE_ROOMS_HOTEL_AVAIL_NOTIF_FREE_ROOMS = of(
            AlpineBitsAction.FREE_ROOMS_HOTEL_AVAIL_NOTIF_FREE_ROOMS,
            AlpineBitsCapability.FREE_ROOMS_HOTEL_AVAIL_NOTIF
    );

    // GuestRequests
    public static final Action GUEST_REQUESTS_READ_GUEST_REQUESTS = of(
            AlpineBitsAction.GUEST_REQUESTS_READ_GUEST_REQUESTS,
            AlpineBitsCapability.GUEST_REQUESTS_READ
    );
    public static final Action GUEST_REQUESTS_CONFIRM_GUEST_REQUESTS_READ = of(
            AlpineBitsAction.GUEST_REQUESTS_NOTIF_REPORT_GUEST_REQUESTS,
            AlpineBitsCapability.GUEST_REQUESTS_READ
    );
    public static final Action GUEST_REQUESTS_WRITE_GUEST_REQUESTS = of(
            AlpineBitsAction.GUEST_REQUESTS_WRITE_GUEST_REQUESTS,
            AlpineBitsCapability.GUEST_REQUESTS_WRITE
    );

    // Inventory
    public static final Action INVENTORY_BASIC_PUSH = of(
            AlpineBitsAction.INVENTORY_BASIC_PUSH,
            AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_INVENTORY
    );
    public static final Action INVENTORY_BASIC_PULL = of(
            AlpineBitsAction.INVENTORY_BASIC_PULL,
            AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_INFO_INVENTORY
    );
    public static final Action INVENTORY_HOTEL_INFO_PUSH = of(
            AlpineBitsAction.INVENTORY_HOTEL_INFO_PUSH,
            AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_INFO
    );
    public static final Action INVENTORY_HOTEL_INFO_PULL = of(
            AlpineBitsAction.INVENTORY_HOTEL_INFO_PULL,
            AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_INFO_INFO
    );

    // RatePlans
    public static final Action RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_RATE_PLANS = of(
            AlpineBitsAction.RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_RATE_PLANS,
            AlpineBitsCapability.RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_RATE_PLANS
    );

    // BaseRates
    public static final Action BASE_RATES_HOTEL_RATE_PLAN_BASE_RATES = of(
            AlpineBitsAction.BASE_RATES_HOTEL_RATE_PLAN_BASE_RATES,
            AlpineBitsCapability.BASE_RATES_HOTEL_RATE_PLAN_BASE_RATES
    );

    private String requestParameter;
    private String name;

    private Action() {
        // Empty
    }

    public static Action of(String requestParameter, String name) {
        Action result = new Action();
        result.requestParameter = requestParameter;
        result.name = name;
        return result;
    }

    public String getRequestParameter() {
        return requestParameter;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Action action = (Action) o;
        return Objects.equals(requestParameter, action.requestParameter) &&
                Objects.equals(name, action.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestParameter, name);
    }

    @Override
    public String toString() {
        return "Action{" +
                "requestParameter=" + requestParameter +
                ", name=" + name +
                '}';
    }

}
