/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms.context.AvailStatusMessagesContext;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ.AvailStatusMessages;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.StatusApplicationControl;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ.UniqueID;

import java.math.BigInteger;
import java.util.List;

/**
 * Use this validator to validate {@link UniqueID}
 * objects (AlpineBits 2017-10).
 */
public class AvailStatusMessagesValidator implements Validator<AvailStatusMessages, AvailStatusMessagesContext> {

    public static final String ELEMENT_NAME = Names.AVAIL_STATUS_MESSAGES;

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final StatusApplicationControlValidator statusApplicationControlValidator = new StatusApplicationControlValidator();

    @Override
    public void validate(AvailStatusMessages availStatusMessages, AvailStatusMessagesContext ctx, ValidationPath path) {
        VALIDATOR.expectNotNull(availStatusMessages, ErrorMessage.EXPECT_AVAIL_STATUS_MESSAGES_TO_BE_NOT_NULL, path);
        VALIDATOR.expectNotNull(ctx, ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL, path);

        VALIDATOR.expectHotelCodeAndNameNotBothNull(
                availStatusMessages.getHotelCode(),
                availStatusMessages.getHotelName(),
                ErrorMessage.EXPECT_HOTEL_CODE_AND_HOTEL_NAME_TO_BE_NOT_BOTH_NULL,
                path.withAttribute(String.format("%s/%s", Names.HOTEL_CODE, Names.HOTEL_NAME))
        );

        VALIDATOR.expectNonEmptyCollection(
                availStatusMessages.getAvailStatusMessages(),
                ErrorMessage.EXPECT_AVAIL_STATUS_MESSAGE_LIST_TO_BE_NOT_EMPTY,
                path.withElement(Names.AVAIL_STATUS_MESSAGE_LIST)
        );

        List<AvailStatusMessage> messages = availStatusMessages.getAvailStatusMessages();

        if (this.isRoomReset(ctx.getInstance(), availStatusMessages.getAvailStatusMessages())) {
            // Room reset means, that there exists only one AvailStatusMessage
            // element without any attributes and elements. This has to be validated.

            this.validateRoomReset(messages.get(0), path.withElement(Names.AVAIL_STATUS_MESSAGE).withIndex(0));
        } else {
            // Ordinary FreeRooms update

            this.validateAvailStatusMessages(messages, ctx, path);
        }
    }

    private boolean isRoomReset(String instance, List<AvailStatusMessage> messages) {
        // A criteria for FreeRooms reset is, that
        // there is only one AvailStatusMessage message
        if (messages.size() != 1) {
            return false;
        }

        // A criteria for FreeRooms reset is, that
        // the instance is "CompleteSet"
        if (!OTAHotelAvailNotifRQValidator.COMPLETE_SET.equals(instance)) {
            return false;
        }

        // A criteria for FreeRooms reset is, that
        // the single AvailStatusMessage has no child element and attributes
        AvailStatusMessage message = messages.get(0);
        return message.getBookingLimit() == null
            && message.getBookingThreshold() == null
            && message.getBookingLimitMessageType() == null
            && message.getStatusApplicationControl() == null;
    }

    private void validateRoomReset(AvailStatusMessage message, ValidationPath path) {
        VALIDATOR.expectNull(
                message.getBookingLimit(),
                ErrorMessage.EXPECT_BOOKING_LIMIT_TO_BE_NULL,
                path.withElement(Names.BOOKING_LIMIT)
        );
        VALIDATOR.expectNull(
                message.getBookingLimitMessageType(),
                ErrorMessage.EXPECT_BOOKING_LIMIT_MESSAGE_TYPE_TO_BE_NULL,
                path.withElement(Names.BOOKING_LIMIT_MESSAGE_TYPE)
        );
        VALIDATOR.expectNull(
                message.getBookingThreshold(),
                ErrorMessage.EXPECT_BOOKING_THRESHOLD_TO_BE_NULL,
                path.withElement(Names.BOOKING_THRESHOLD)
        );
        VALIDATOR.expectNull(
                message.getStatusApplicationControl(),
                ErrorMessage.EXPECT_STATUS_APPLICATION_CONTROL_TO_BE_NULL,
                path.withElement(Names.STATUS_APPLICATION_CONTROL)
        );
    }

    private void validateAvailStatusMessages(
            List<AvailStatusMessage> messages,
            AvailStatusMessagesContext ctx,
            ValidationPath path
    ) {
        boolean hasRoomCategory = false;
        boolean hasDistinctRoom = false;

        for (int i = 0; i < messages.size(); i++) {
            AvailStatusMessage message = messages.get(i);

            ValidationPath indexedPath = path.withElement(Names.AVAIL_STATUS_MESSAGE).withIndex(i);

            VALIDATOR.expectNotNull(
                    message.getBookingLimit(),
                    ErrorMessage.EXPECT_BOOKING_LIMIT_TO_BE_NOT_NULL,
                    indexedPath.withAttribute(Names.BOOKING_LIMIT)
            );

            VALIDATOR.expectNotNull(
                    message.getBookingLimitMessageType(),
                    ErrorMessage.EXPECT_BOOKING_LIMIT_MESSAGE_TYPE_TO_BE_NOT_NULL,
                    indexedPath.withAttribute(Names.BOOKING_LIMIT_MESSAGE_TYPE)
            );

            // Validate BookingThreshold
            this.validateBookingThreshold(message, ctx.isFreeButNotBookableSupported(), indexedPath);

            // Validate StatusApplicationControl
            StatusApplicationControl statusApplicationControl = message.getStatusApplicationControl();
            this.statusApplicationControlValidator.validate(
                    statusApplicationControl,
                    null,
                    indexedPath.withElement(StatusApplicationControlValidator.ELEMENT_NAME)
            );

            // Validate for room category / distinct room
            if (this.isForRoomCategory(statusApplicationControl)) {
                // Validate room category availability info
                this.validateForRoomCategory(ctx, path);
                hasRoomCategory = true;
            } else {
                int bookingLimit = message.getBookingLimit().intValue();
                // Validate distinct room availability info
                this.validateForDistinctRooms(ctx, bookingLimit, path);
                hasDistinctRoom = true;
            }

            // Check that room category information and distinct room
            // information is not mixed
            if (hasRoomCategory && hasDistinctRoom) {
                VALIDATOR.throwValidationException(
                        ErrorMessage.EXPECT_ROOM_CATEGORY_AND_DISTINCT_ROOM_TO_NOT_BE_MIXED,
                        path
                );
            }
        }
    }

    private void validateBookingThreshold(AvailStatusMessage message, boolean supportsFreeButNotBookable, ValidationPath path) {
        // Check if Server has OTA_HotelAvailNotif_accept_BookingThreshold
        // capability
        if (supportsFreeButNotBookable) {
            // If the OTA_HotelAvailNotif_accept_BookingThresholdcapability
            // is set, a client must send the BookingThreshold attribute.
            // In this case, the BookingThreshold must be validated.
            VALIDATOR.expectNotNull(
                    message.getBookingThreshold(),
                    ErrorMessage.EXPECT_BOOKING_THRESHOLD_TO_BE_NOT_NULL,
                    path.withAttribute(Names.BOOKING_THRESHOLD)
            );

            VALIDATOR.expectArg0LesserOrEqualThanArg1(
                    message.getBookingThreshold(),
                    message.getBookingLimit(),
                    ErrorMessage.EXPECT_BOOKING_THRESHOLD_TO_BE_LESSER_OR_EQUAL_THAN_BOOKING_LIMIT,
                    path.withAttribute(String.format("%s/%s", Names.BOOKING_THRESHOLD, Names.BOOKING_LIMIT))
            );

            VALIDATOR.expectArg0LesserOrEqualThanArg1(
                    BigInteger.ZERO,
                    message.getBookingThreshold(),
                    ErrorMessage.EXPECT_ZERO_TO_BE_LESSER_OR_EQUAL_THAN_BOOKING_THRESHOLD,
                    path.withAttribute(Names.MAX_CHILD_OCCUPANCY)
            );
        } else {
            // If the OTA_HotelAvailNotif_accept_BookingThreshold capability
            // is not set, a client must NOT send the BookingThreshold attribute.
            VALIDATOR.expectNull(
                    message.getBookingThreshold(),
                    ErrorMessage.EXPECT_BOOKING_THRESHOLD_TO_BE_NULL,
                    path.withAttribute(Names.BOOKING_THRESHOLD)
            );
        }
    }

    private void validateForRoomCategory(AvailStatusMessagesContext ctx, ValidationPath path) {
        // Check that server supports availability information for room categories
        // (OTA_HotelAvailNotif_accept_categories capability)
        if (!ctx.isRoomCategoriesSupported()) {
            VALIDATOR.throwValidationException(
                    ErrorMessage.EXPECT_AVAILABILITY_INFORMATION_SUPPORT_FOR_ROOM_CATEGORIES,
                    path.withElement(StatusApplicationControlValidator.ELEMENT_NAME)
            );
        }
    }

    private void validateForDistinctRooms(AvailStatusMessagesContext ctx, int bookingLimit, ValidationPath path) {
        // Check that server supports availability information for distinct rooms
        // (OTA_HotelAvailNotif_accept_rooms capability)
        if (!ctx.isDistinctRoomsSupported()) {
            VALIDATOR.throwValidationException(
                    ErrorMessage.EXPECT_AVAILABILITY_INFORMATION_SUPPORT_FOR_DISTINCT_ROOMS,
                    path.withElement(StatusApplicationControlValidator.ELEMENT_NAME)
            );
        }

        // A BookingLimit for a distinct room different
        // to 0 or 1 makes no sense. The room is available
        // (BookingLimit = 1) or not (BookingLimit = 0).
        if (bookingLimit != 0 && bookingLimit != 1) {
            VALIDATOR.throwValidationException(
                    ErrorMessage.EXPECT_BOOKING_LIMIT_FOR_DISTINCT_ROOM_TO_BE_0_OR_1,
                    path
            );
        }
    }

    private boolean isForRoomCategory(StatusApplicationControl statusApplicationControl) {
        return statusApplicationControl.getInvCode() == null;
    }
}
