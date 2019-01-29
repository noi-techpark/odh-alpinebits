/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.constants;

/**
 * This class provides constants for all known
 * AlpineBits actions for version 2017-10.
 */
public final class AlpineBitsAction {

    // Housekeeping
    public static final String GET_VERSION = "getVersion";
    public static final String GET_CAPABILITIES = "getCapabilities";

    // FreeRooms
    public static final String FREE_ROOMS_HOTEL_AVAIL_NOTIF_FREE_ROOMS = "OTA_HotelAvailNotif:FreeRooms";

    // GuestRequests
    public static final String GUEST_REQUESTS_READ_GUEST_REQUESTS = "OTA_Read:GuestRequests";
    public static final String GUEST_REQUESTS_NOTIF_REPORT_GUEST_REQUESTS = "OTA_NotifReport:GuestRequests";

    // Inventory
    public static final String INVENTORY_BASIC_PUSH = "OTA_HotelDescriptiveContentNotif:Inventory";
    public static final String INVENTORY_BASIC_PULL = "OTA_HotelDescriptiveInfo:Inventory";
    public static final String INVENTORY_HOTEL_INFO_PUSH = "OTA_HotelDescriptiveContentNotif:Info";
    public static final String INVENTORY_HOTEL_INFO_PULL = "OTA_HotelDescriptiveInfo:Info";

    // FreeRooms
    public static final String RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_RATE_PLANS = "OTA_HotelRatePlanNotif:RatePlans";

    // BaseRates
    public static final String BASE_RATES_HOTEL_RATE_PLAN_BASE_RATES = "OTA_HotelRatePlan:BaseRates";

    private AlpineBitsAction() {
        // Empty
    }

}
