/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.constants;

/**
 * This class provides constants for all known
 * AlpineBits capabilities for version 2017-10.
 */
public final class AlpineBitsCapability {

    public static final String HANDSHAKING = "action_OTA_Ping";

    // Housekeeping
    public static final String GET_VERSION = "action_getVersion";
    public static final String GET_CAPABILITIES = "action_getCapabilities";

    // FreeRooms
    public static final String FREE_ROOMS_HOTEL_AVAIL_NOTIF = "action_OTA_HotelAvailNotif";
    public static final String FREE_ROOMS_HOTEL_AVAIL_NOTIF_ACCEPT_ROOMS = "OTA_HotelAvailNotif_accept_rooms";
    public static final String FREE_ROOMS_HOTEL_AVAIL_NOTIF_ACCEPT_CATEGORIES = "OTA_HotelAvailNotif_accept_categories";
    public static final String FREE_ROOMS_HOTEL_AVAIL_NOTIF_ACCEPT_DELTAS = "OTA_HotelAvailNotif_accept_deltas";
    public static final String FREE_ROOMS_HOTEL_AVAIL_NOTIF_ACCEPT_BOOKING_THRESHOLD = "OTA_HotelAvailNotif_accept_BookingThreshold";

    // GuestRequests
    public static final String GUEST_REQUESTS_READ = "action_OTA_Read";
    public static final String GUEST_REQUESTS_WRITE = "action_OTA_HotelResNotif_GuestRequests";

    // Inventory
    public static final String INVENTORY_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_INVENTORY =
            "action_OTA_HotelDescriptiveContentNotif_Inventory";
    public static final String INVENTORY_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_INVENTORY_USE_ROOMS =
            "OTA_HotelDescriptiveContentNotif_Inventory_use_rooms";
    public static final String INVENTORY_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_OCCUPANCY_CHILDREN =
            "OTA_HotelDescriptiveContentNotif_Inventory_occupancy_children";
    public static final String INVENTORY_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_INFO =
            "action_OTA_HotelDescriptiveContentNotif_Info";
    public static final String INVENTORY_HOTEL_DESCRIPTIVE_INFO_INVENTORY =
            "action_OTA_HotelDescriptiveInfo_Inventory";
    public static final String INVENTORY_HOTEL_DESCRIPTIVE_INFO_INFO =
            "action_OTA_HotelDescriptiveInfo_Info";

    // RatePlans
    public static final String RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_RATE_PLANS =
            "action_OTA_HotelRatePlanNotif_RatePlans";
    public static final String RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_ACCEPT_ARRIVAL_DOW =
            "OTA_HotelRatePlanNotif_accept_ArrivalDOW";
    public static final String RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_ACCEPT_DEPARTURE_DOW =
            "OTA_HotelRatePlanNotif_accept_DepartureDOW";
    public static final String RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_ACCEPT_RATE_PLAN_BOOKING_RULE =
            "OTA_HotelRatePlanNotif_accept_RatePlan_BookingRule";
    public static final String RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_ACCEPT_RATE_PLAN_ROOM_TYPE_BOOKING_RULE =
            "OTA_HotelRatePlanNotif_accept_RatePlan_RoomType_BookingRule";
    public static final String RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_ACCEPT_RATE_PLAN_MIXED_BOOKING_RULE =
            "OTA_HotelRatePlanNotif_accept_RatePlan_mixed_BookingRule";
    public static final String RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_ACCEPT_SUPPLEMENTS =
            "OTA_HotelRatePlanNotif_accept_Supplements";
    public static final String RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_ACCEPT_FREE_NIGHTS_OFFER =
            "OTA_HotelRatePlanNotif_accept_FreeNightsOffers";
    public static final String RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_ACCEPT_FAMILY_OFFERS =
            "OTA_HotelRatePlanNotif_accept_FamilyOffers";
    public static final String RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_ACCEPT_OVERLAY =
            "OTA_HotelRatePlanNotif_accept_overlay";
    public static final String RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_ACCEPT_RATE_PLAN_JOIN =
            "OTA_HotelRatePlanNotif_accept_RatePlanJoin";
    public static final String RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_ACCEPT_OFFER_RULE_BOOKING_OFFSET =
            "OTA_HotelRatePlanNotif_accept_OfferRule_BookingOffset";
    public static final String RATE_PLANS_HOTEL_RATE_PLAN_NOTIF_ACCEPT_OFFER_RULE_DOWLOS =
            "OTA_HotelRatePlanNotif_accept_OfferRule_DOWLOS";

    // BaseRates
    public static final String BASE_RATES_HOTEL_RATE_PLAN_BASE_RATES = "action_OTA_HotelRatePlan_BaseRates";
    public static final String BASE_RATES_HOTEL_RATE_PLAN_BASE_RATES_DELTAS = "OTA_HotelRatePlan_BaseRates_deltas";

    private AlpineBitsCapability() {
        // Empty
    }
}
