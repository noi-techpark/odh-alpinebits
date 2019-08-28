/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.constants;

/**
 * Here you can find an enumeration of all OTA Guest Room Info codes (GRI).
 */
public enum OTACodeGuestRoomInfo {
    ACCESSIBLE_ROOMS(1),
    NONSMOKING_ROOMS(2),
    SUITES(3),
    BUNGALOWS_AND_VILLAS(4),
    FLOORS(5),
    EXECUTIVE_FLOOR(6),
    ROOMS_THAT_WORK(7),
    AVAILABLE_ROOMS(8),
    AVAILABLE_SUITES(9),
    DOUBLE_BEDROOMS(10),
    KING_BEDROOMS(11),
    TOTAL_ROOMS(12),
    APARTMENTS(13),
    QUEEN_BEDROOMS(14),
    PENTHOUSES(15),
    STUDIOS(16),
    FIRST_FLOOR_ROOMS(17),
    SMOKING_ROOMS(18),
    TWIN_BEDROOMS(19),
    DRIVE_UP_ROOMS(20),
    ROOMS_WITH_INTERNET_ACCESS(21),
    FREESTANDING_UNITS(22),
    AIR_CONDITIONED_GUEST_ROOMS(23),
    CONCIERGE_LEVELS(24),
    CONDOS(25),
    CLUB_LEVELS(26),
    TOTAL_AVAILABLE_ROOMS_AND_SUITES(27),
    TOTAL_ROOMS_AND_SUITES(28),
    EMPLOYEES_ON_PROPERTY(29),
    EMPLOYEES_WORKING_FOR_PROPERTY(30),
    SEPARATE_FLOORS_FOR_WOMEN(31),
    BUILDINGS(32),
    ACCOMMODATIONS_WITH_BALCONY(33),
    ADJOINING_ROOMS_OR_SUITES(34),
    CONNECTING_ROOMS_OR_SUITES(35),
    FAMILY_OVERSIZED_ACCOMMODATIONS(36),
    SINGLE_BEDDED_ACCOMMODATIONS(37),
    CABIN(38),
    COTTAGE(39),
    LOFT(40),
    PARLOUR(41),
    ROOM(42),
    LANAI(43),
    BUNGALOW(44),
    VILLA(45),
    EFFICIENCY(46),
    ALL_ROOMS_NON_SMOKING(47),
    DOUBLE_DOUBLE_BEDROOMS(48),
    KING_KING_BEDROOMS(49),
    QUEEN_QUEEN_BEDROOMS(50),
    TWIN_TWIN_BEDROOMS(51),
    APARTMENT_FOR_1(52),
    APARTMENT_FOR_2(53),
    APARTMENT_FOR_3(54),
    APARTMENT_FOR_4(55),
    APARTMENT_FOR_6(56),
    ONE_ROOM_CABIN(57),
    ONE_BEDROOM_CABIN(58),
    TWO_BEDROOM_CABIN(59),
    JUNIOR_SUITE(60),
    JACUZZI_SUITE(61),
    RUN_OF_THE_HOUSE(62),
    LARGE_SUITE(63),
    ONE_BEDROOM_SUITE(64),
    TWO_BEDROOM_SUITE(65),
    THREE_BEDROOM_SUITE(66),
    VILLA_FOR_1(67),
    VILLA_FOR_2(68),
    VILLA_FOR_3(69),
    VILLA_FOR_6(70),
    VILLA_FOR_8(71),
    SINGLE_WITH_PULLOUT(72),
    BUSINESS_PLAN(73),
    BUSINESS_CLASS(74),
    CLASSIC(75),
    COMFORT(76),
    DELUXE(77),
    DELUXE_SUITE(78),
    ECONOMY(79),
    LUXURY(80),
    PREMIER(81),
    STANDARD(82),
    SUPERIOR(83);

    private final int code;

    OTACodeGuestRoomInfo(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static boolean isCodeDefined(int code) {
        for (OTACodeGuestRoomInfo value : values()) {
            if (value.code == code) {
                return true;
            }
        }
        return false;
    }
}
