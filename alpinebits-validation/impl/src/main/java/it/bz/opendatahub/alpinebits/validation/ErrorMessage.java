/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation;

/**
 * Validation messages.
 */
public final class ErrorMessage {

    public static final String EXPECT_AFFILITATION_INFO_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.AFFILIATION_INFO);
    public static final String EXPECT_AMENITIES_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.AMENITIES);
    public static final String EXPECT_AVAIL_STATUS_MESSAGE_LIST_TO_BE_NOT_EMPTY =
            "The list of AvailStatusMessage elements is expected to contain at least one element";
    public static final String EXPECT_AVAIL_STATUS_MESSAGES_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.AVAIL_STATUS_MESSAGES);
    public static final String EXPECT_AVAILABILITY_INFORMATION_SUPPORT_FOR_DISTINCT_ROOMS =
            "Server doesn't handle availability information for distinct rooms ('OTA_HotelAvailNotif_accept_rooms' capability not set)";
    public static final String EXPECT_AVAILABILITY_INFORMATION_SUPPORT_FOR_ROOM_CATEGORIES =
            "Server doesn't support availability information for room categories ('OTA_HotelAvailNotif_accept_categories' capability not set)";
    public static final String EXPECT_BOOKING_LIMIT_FOR_DISTINCT_ROOM_TO_BE_0_OR_1 =
            "A distinct room can not have a booking limit different to 0 or 1 (a room can be available or not)";
    public static final String EXPECT_BOOKING_LIMIT_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.BOOKING_LIMIT);
    public static final String EXPECT_BOOKING_LIMIT_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.BOOKING_LIMIT);
    public static final String EXPECT_BOOKING_LIMIT_MESSAGE_TYPE_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.BOOKING_LIMIT_MESSAGE_TYPE);
    public static final String EXPECT_BOOKING_LIMIT_MESSAGE_TYPE_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.BOOKING_LIMIT_MESSAGE_TYPE);
    public static final String EXPECT_BOOKING_THRESHOLD_TO_BE_LESSER_OR_EQUAL_THAN_BOOKING_LIMIT =
            DefaultErrorMessage.expectArg0LesserOrEqualThanArg1ErrorMessage(Names.BOOKING_THRESHOLD, Names.BOOKING_LIMIT);
    public static final String EXPECT_BOOKING_THRESHOLD_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.BOOKING_THRESHOLD);
    public static final String EXPECT_BOOKING_THRESHOLD_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.BOOKING_THRESHOLD);
    public static final String EXPECT_CATEGORY_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.CATEGORY);
    public static final String EXPECT_CODE_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.CODE);
    public static final String EXPECT_COMPANY_NAME_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.COMPANY_NAME);
    public static final String EXPECT_CONTACT_INFOS_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.CONTACT_INFOS);
    public static final String EXPECT_CONTEXT_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.CONTEXT);
    public static final String EXPECT_DESCRIPTION_LIST_TO_BE_NOT_EMPTY =
            "TextItem must contain one ore more Description elements";
    public static final String EXPECT_DESCRIPTION_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.DESCRIPTION);
    public static final String EXPECT_DESCRIPTIONS_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.DESCRIPTIONS);
    public static final String EXPECT_DESCRIPTION_TO_EXIST =
            "The list of Description elements is expected to contain at least one element";
    public static final String EXPECT_END_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.END);
    public static final String EXPECT_FACILITY_INFO_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.FACILITY_INFO);
    public static final String EXPECT_GUEST_ROOM_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.GUEST_ROOM);
    public static final String EXPECT_HOTEL_AVAIL_NOTIF_RQ_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.OTA_HOTEL_AVAIL_NOTIF_RQ);
    public static final String EXPECT_HOTEL_CODE_AND_HOTEL_NAME_TO_BE_NOT_BOTH_NULL =
            "Expecting one of HotelCode or HotelName to be not null, but both of them are null";
    public static final String EXPECT_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_RQ_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.OTA_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_RQ);
    public static final String EXPECT_HOTEL_DESCRIPTIVE_CONTENT_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.HOTEL_DESCRIPTIVE_CONTENT);
    public static final String EXPECT_HOTEL_DESCRIPTIVE_CONTENTS_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.HOTEL_DESCRIPTIVE_CONTENTS);
    public static final String EXPECT_HOTEL_INFO_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.HOTEL_INFO);
    public static final String EXPECT_ID_CONTEXT_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.ID_CONTEXT);
    public static final String EXPECT_ID_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.ID);
    public static final String EXPECT_ID_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.ID);
    public static final String EXPECT_IMAGE_FORMAT_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.IMAGE_FORMAT);
    public static final String EXPECT_IMAGE_ITEM_LIST_TO_BE_NOT_EMPTY =
            "The list of ImageItem elements is expected to contain at least one element";
    public static final String EXPECT_IMAGE_ITEM_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.IMAGE_ITEM);
    public static final String EXPECT_IMAGE_ITEMS_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.IMAGE_ITEMS);
    public static final String EXPECT_IMAGE_ITEMS_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.IMAGE_ITEMS);
    public static final String EXPECT_INFO_CODE_25_TO_BE_DEFINED =
            "Required MultimediaDescription element with InfoCode=25 was not found";
    public static final String EXPECT_INFO_CODE_TO_BE_DEFINED =
            "The code %s is not defined in the list of OTA GuestRoom Info Codes (GRI)";
    public static final String EXPECT_INFO_CODE_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.INFO_CODE);
    public static final String EXPECT_INFO_CODE_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.INFO_CODE);
    public static final String EXPECT_INFORMATION_TYPE_CODE_TO_BE_DEFINED =
            "The code %s is not defined in the list of OTA Information Type Codes (INF)";
    public static final String EXPECT_INSTANCE_TO_BE_COMPLETE_SET =
            "Instance attribute must have the value 'CompleteSet'";
    public static final String EXPECT_INSTANCE_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.INSTANCE);
    public static final String EXPECT_INV_TYPE_CODE_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.INV_TYPE_CODE);
    public static final String EXPECT_LANGUAGE_ISO639_1_CODE_TO_BE_DEFINED =
            "The language code '%s' is not defined in ISO 639-1";
    public static final String EXPECT_LANGUAGE_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.LANGUAGE);
    public static final String EXPECT_MAX_CHILD_OCCUPANCY_TO_BE_LESSER_OR_EQUAL_THAN_MAX_OCCUPANCY =
            DefaultErrorMessage.expectArg0LesserOrEqualThanArg1ErrorMessage(Names.MAX_CHILD_OCCUPANCY, Names.MAX_OCCUPANCY);
    public static final String EXPECT_MAX_CHILD_OCCUPANCY_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.MAX_CHILD_OCCUPANCY);
    public static final String EXPECT_MAX_OCCUPANCY_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.MAX_OCCUPANCY);
    public static final String EXPECT_MAX_OCCUPANCY_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.MAX_OCCUPANCY);
    public static final String EXPECT_MIN_OCCUPANCY_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.MIN_OCCUPANCY);
    public static final String EXPECT_MIN_OCCUPANCY_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.MIN_OCCUPANCY);
    public static final String EXPECT_MULTIMEDIA_DESCRIPTIONS_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.MULTIMEDIA_DESCRIPTIONS);
    public static final String EXPECT_MULTIMEDIA_DESCRIPTIONS_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.MULTIMEDIA_DESCRIPTIONS);
    public static final String EXPECT_MULTIMEDIA_DESCRIPTIONS_TO_HAVE_AT_MOST_THREE_ELEMENTS
            = "At most 3 MultimediaDescription elements are allowed per GuestRoom";
    public static final String EXPECT_NO_DUPLICATE_INFO_CODE
            = "More than one MultimediaDescription elements with InfoCode '%s' found";
    public static final String EXPECT_NO_DUPLICATE_LANGUAGE_AND_TEXT_FORMAT =
            "The combination Language '%s' and TextFormat '%s' appears more than once in the Description elements";
    public static final String EXPECT_PICTURE_CATEGORY_CODE_TO_BE_DEFINED =
            "The code '%s' is not defined in the list of OTA Picture Category Codes (PIC)";
    public static final String EXPECT_PLAIN_TEXT_TO_EXIST =
            "The Language '%s' provides a single TextFormat of 'HTML', which is not allowed. A TextFormat of 'HTML'" +
                    " for a given language makes the use of an additional TextFormat of 'PlainText' mandatory" +
                    " for that language";
    public static final String EXPECT_POLICIES_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.POLICIES);
    public static final String EXPECT_REQUIRED_MULTIMEDIA_DESCRIPTION_TO_EXIST
            = "A MultimediaDescription element with InfoCode=25 is required";
    public static final String EXPECT_ROOM_AMENITY_CODE_TO_BE_DEFINED =
            "The code '%s' is not defined in the list of OTA Room Amenity Type Codes (RMA)";
    public static final String EXPECT_ROOM_AMENITY_CODE_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.ROOM_AMENITY_CODE);
    public static final String EXPECT_ROOM_CATEGORY_AND_DISTINCT_ROOM_TO_NOT_BE_MIXED =
            "It is not allowed to mix availability information for room categories and distinct rooms";
    public static final String EXPECT_ROOM_CLASSIFICATION_CODE_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.ROOM_CLASSIFICATION_CODE);
    public static final String EXPECT_ROOM_CLASSIFICATION_CODE_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.ROOM_CLASSIFICATION_CODE);
    public static final String EXPECT_ROOM_ID_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.ROOM_ID);
    public static final String EXPECT_SIZE_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.SIZE);
    public static final String EXPECT_STANDARD_OCCUPANCY_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.STANDARD_OCCUPANCY);
    public static final String EXPECT_STANDARD_OCCUPANCY_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.STANDARD_OCCUPANCY);
    public static final String EXPECT_START_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.START);
    public static final String EXPECT_STATUS_APPLICATION_CONTROL_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.STATUS_APPLICATION_CONTROL);
    public static final String EXPECT_STATUS_APPLICATION_CONTROL_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.STATUS_APPLICATION_CONTROL);
    public static final String EXPECT_TEXT_FORMAT_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.TEXT_FORMAT);
    public static final String EXPECT_TEXT_FORMAT_TO_BE_PLAINTEXT_OR_HTML =
            "TextFormat element expected to be 'PlainText' or 'HTML', but '%s' was found";
    public static final String EXPECT_TEXT_ITEM_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.TEXT_ITEM);
    public static final String EXPECT_TEXT_ITEMS_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.TEXT_ITEMS);
    public static final String EXPECT_TEXT_ITEMS_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.TEXT_ITEMS);
    public static final String EXPECT_TPA_EXTENSIONS_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.TPA_EXTENSIONS);
    public static final String EXPECT_TYPE_ROOM_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.TYPE_ROOM);
    public static final String EXPECT_TYPE_ROOM_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.TYPE_ROOM);
    public static final String EXPECT_TYPE_TO_BE_16_OR_35 =
            "Type attribute expected to have a value of 16 or 35";
    public static final String EXPECT_TYPE_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.TYPE);
    public static final String EXPECT_UNIQUE_ID_TO_BE_NOT_NULL =
            "The server doesn't support delta updates (OTA_HotelAvailNotif_accept_deltas capability)." +
                    " UniqueID element required.";
    public static final String EXPECT_URL_TO_BE_NOT_NULL =
            DefaultErrorMessage.expectNotNullErrorMessage(Names.URL);
    public static final String EXPECT_URL_TO_BE_NULL =
            DefaultErrorMessage.expectNullErrorMessage(Names.URL);
    public static final String EXPECT_ZERO_TO_BE_LESSER_OR_EQUAL_THAN_BOOKING_THRESHOLD =
            DefaultErrorMessage.expectArg0LesserOrEqualThanArg1ErrorMessage(Names.ZERO, Names.BOOKING_THRESHOLD);
    public static final String EXPECT_ZERO_TO_BE_LESSER_OR_EQUAL_THAN_MAX_CHILD_OCCUPANCY =
            DefaultErrorMessage.expectArg0LesserOrEqualThanArg1ErrorMessage(Names.ZERO, Names.MAX_CHILD_OCCUPANCY);


    private ErrorMessage() {
        // Empty
    }
}
