/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.routing.constants;


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
            ActionRequestParam.HANDSHAKING,
            ActionName.HANDSHAKING
    );

    // Housekeeping
    public static final Action GET_VERSION = of(
            ActionRequestParam.GET_VERSION,
            ActionName.GET_VERSION
    );
    public static final Action GET_CAPABILITIES = of(
            ActionRequestParam.GET_CAPABILITIES,
            ActionName.GET_CAPABILITIES
    );

    // FreeRooms
    public static final Action FREE_ROOMS_HOTEL_AVAIL_NOTIF_FREE_ROOMS = of(
            ActionRequestParam.FREE_ROOMS_HOTEL_AVAIL_NOTIF_FREE_ROOMS,
            ActionName.FREE_ROOMS_HOTEL_AVAIL_NOTIF
    );

    // GuestRequests
    public static final Action GUEST_REQUESTS_READ_GUEST_REQUESTS = of(
            ActionRequestParam.GUEST_REQUESTS_READ_GUEST_REQUESTS,
            ActionName.GUEST_REQUESTS_READ
    );
    public static final Action GUEST_REQUESTS_CONFIRM_GUEST_REQUESTS_READ = of(
            ActionRequestParam.GUEST_REQUESTS_CONFIRM_GUEST_REQUESTS_READ,
            ActionName.GUEST_REQUESTS_READ
    );
    public static final Action GUEST_REQUESTS_WRITE_GUEST_REQUESTS = of(
            ActionRequestParam.GUEST_REQUESTS_WRITE_GUEST_REQUESTS,
            ActionName.GUEST_REQUESTS_WRITE
    );

    // Inventory
    public static final Action INVENTORY_BASIC_PUSH = of(
            ActionRequestParam.INVENTORY_BASIC_PUSH,
            ActionName.INVENTORY_BASIC_PUSH
    );
    public static final Action INVENTORY_BASIC_PULL = of(
            ActionRequestParam.INVENTORY_BASIC_PULL,
            ActionName.INVENTORY_BASIC_PULL
    );
    public static final Action INVENTORY_HOTEL_INFO_PUSH = of(
            ActionRequestParam.INVENTORY_HOTEL_INFO_PUSH,
            ActionName.INVENTORY_HOTEL_INFO_PUSH
    );
    public static final Action INVENTORY_HOTEL_INFO_PULL = of(
            ActionRequestParam.INVENTORY_HOTEL_INFO_PULL,
            ActionName.INVENTORY_HOTEL_INFO_PULL
    );

    // RatePlans
    public static final Action RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_RATE_PLANS = of(
            ActionRequestParam.RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_RATE_PLANS,
            ActionName.RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_RATE_PLANS
    );

    // BaseRates
    public static final Action BASE_RATES_HOTEL_RATE_PLAN_BASE_RATES = of(
            ActionRequestParam.BASE_RATES_HOTEL_RATE_PLAN_BASE_RATES,
            ActionName.BASE_RATES_HOTEL_RATE_PLAN_BASE_RATES
    );

    private ActionRequestParam requestParameter;
    private ActionName name;

    private Action() {
        // Empty
    }

    public static Action of(ActionRequestParam requestParameter, ActionName name) {
        Action result = new Action();
        result.requestParameter = requestParameter;
        result.name = name;
        return result;
    }

    public ActionRequestParam getRequestParameter() {
        return requestParameter;
    }

    public ActionName getName() {
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
