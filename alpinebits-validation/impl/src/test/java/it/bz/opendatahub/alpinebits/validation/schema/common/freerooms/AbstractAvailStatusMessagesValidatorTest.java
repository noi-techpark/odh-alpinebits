// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.common.freerooms;

import it.bz.opendatahub.alpinebits.validation.EmptyCollectionValidationException;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.NotLesserOrEqualValidationException;
import it.bz.opendatahub.alpinebits.validation.NotNullValidationException;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.AvailStatusMessagesContext;
import it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms.AvailStatusMessagesValidator;
import it.bz.opendatahub.alpinebits.xml.schema.ota.AvailStatusMessageType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRQ.AvailStatusMessages;
import org.testng.annotations.Test;

import java.math.BigInteger;

/**
 * Abstract tests for {@link AvailStatusMessagesValidator}.
 */
public abstract class AbstractAvailStatusMessagesValidatorTest {

    protected static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.AVAIL_STATUS_MESSAGES);

    protected static final String STATUS_APPLICATION_CONTROL_START = "2017-10-01";
    protected static final String STATUS_APPLICATION_CONTROL_END = "2017-10-31";
    protected static final String DEFAULT_HOTEL_CODE = "XYZ";
    protected static final String DEFAULT_INV_TYPE_CODE = "DEF";
    protected static final String DEFAULT_BOOKING_LIMIT_MESSAGE_TYPE = "SetLimit";

    private static final String DEFAULT_INV_CODE = "ABC";

    @Test
    public void testValidate_ShouldThrow_WhenAvailStatusMessagesIsNull() {
        this.validateAndAssert(
                null,
                null,
                NullValidationException.class,
                ErrorMessage.EXPECT_AVAIL_STATUS_MESSAGES_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenContextIsNull() {
        AvailStatusMessages availStatusMessages = new AvailStatusMessages();

        this.validateAndAssert(
                availStatusMessages,
                null,
                NullValidationException.class,
                ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenBothHotelCodeAndHotelNameAreMissing() {
        AvailStatusMessages availStatusMessages = new AvailStatusMessages();

        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().build();

        this.validateAndAssert(
                availStatusMessages,
                ctx,
                ValidationException.class,
                ErrorMessage.EXPECT_HOTEL_CODE_AND_HOTEL_NAME_TO_BE_NOT_BOTH_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenAvailStatusMessageListIsEmpty() {
        AvailStatusMessages availStatusMessages = this.buildValidAvailStatusMessages();

        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().build();

        this.validateAndAssert(
                availStatusMessages,
                ctx,
                EmptyCollectionValidationException.class,
                ErrorMessage.EXPECT_AVAIL_STATUS_MESSAGE_LIST_TO_BE_NOT_EMPTY
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenBookingLimitIsNull() {
        AvailStatusMessageType availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
        availStatusMessage.setBookingLimit(null);
        AvailStatusMessages availStatusMessages = this.buildValidAvailStatusMessages(availStatusMessage);

        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().build();

        this.validateAndAssert(
                availStatusMessages,
                ctx,
                NullValidationException.class,
                ErrorMessage.EXPECT_BOOKING_LIMIT_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenFreeButNotBookableSupportedAndBookingThresholdIsNull() {
        AvailStatusMessageType availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
        AvailStatusMessages availStatusMessages = this.buildValidAvailStatusMessages(availStatusMessage);

        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().withFreeButNotBookableSupport().build();

        this.validateAndAssert(
                availStatusMessages,
                ctx,
                NullValidationException.class,
                ErrorMessage.EXPECT_BOOKING_THRESHOLD_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenFreeButNotBookableSupportedAndBookingLimitIsLesserThanBookingThreshold() {
        AvailStatusMessageType availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
        availStatusMessage.setBookingLimit(BigInteger.ONE);
        availStatusMessage.setBookingThreshold(BigInteger.TEN);
        AvailStatusMessages availStatusMessages = this.buildValidAvailStatusMessages(availStatusMessage);

        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().withFreeButNotBookableSupport().build();

        this.validateAndAssert(
                availStatusMessages,
                ctx,
                NotLesserOrEqualValidationException.class,
                ErrorMessage.EXPECT_BOOKING_THRESHOLD_TO_BE_LESSER_OR_EQUAL_THAN_BOOKING_LIMIT
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenFreeButNotBookableSupportedAndBookingThresholdIsLesserThanZero() {
        AvailStatusMessageType availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
        availStatusMessage.setBookingThreshold(BigInteger.valueOf(-1));
        AvailStatusMessages availStatusMessages = this.buildValidAvailStatusMessages(availStatusMessage);

        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().withFreeButNotBookableSupport().build();

        this.validateAndAssert(
                availStatusMessages,
                ctx,
                NotLesserOrEqualValidationException.class,
                ErrorMessage.EXPECT_ZERO_TO_BE_LESSER_OR_EQUAL_THAN_BOOKING_THRESHOLD
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenFreeButNotBookableUnsupportedAndBookingThresholdIsNotNull() {
        AvailStatusMessageType availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
        availStatusMessage.setBookingThreshold(BigInteger.ONE);
        AvailStatusMessages availStatusMessages = this.buildValidAvailStatusMessages(availStatusMessage);

        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().build();

        this.validateAndAssert(
                availStatusMessages,
                ctx,
                NotNullValidationException.class,
                ErrorMessage.EXPECT_BOOKING_THRESHOLD_TO_BE_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenRoomCategoryUnsupportedAndHasRoomCategoryInfo() {
        AvailStatusMessageType availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
        AvailStatusMessages availStatusMessages = this.buildValidAvailStatusMessages(availStatusMessage);

        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().build();

        this.validateAndAssert(
                availStatusMessages,
                ctx,
                ValidationException.class,
                ErrorMessage.EXPECT_AVAILABILITY_INFORMATION_SUPPORT_FOR_ROOM_CATEGORIES
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenDistinctRoomsUnsupportedAndHasDistinctRoomsInfo() {
        AvailStatusMessageType availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
        // Setting InvCode != null makes this AvailStatusMessage a "Distinct Room" one
        availStatusMessage.getStatusApplicationControl().setInvCode(DEFAULT_INV_CODE);
        AvailStatusMessages availStatusMessages = this.buildValidAvailStatusMessages(availStatusMessage);

        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().build();

        this.validateAndAssert(
                availStatusMessages,
                ctx,
                ValidationException.class,
                ErrorMessage.EXPECT_AVAILABILITY_INFORMATION_SUPPORT_FOR_DISTINCT_ROOMS
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenDistinctRoomsAndBookingLimitGreaterThanOne() {
        AvailStatusMessageType availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
        // Setting InvCode != null makes this AvailStatusMessage a "Distinct Room" one
        availStatusMessage.getStatusApplicationControl().setInvCode(DEFAULT_INV_CODE);
        availStatusMessage.setBookingLimit(BigInteger.valueOf(2));
        AvailStatusMessages availStatusMessages = this.buildValidAvailStatusMessages(availStatusMessage);

        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().withDistinctRoomsSupport().build();

        this.validateAndAssert(
                availStatusMessages,
                ctx,
                ValidationException.class,
                ErrorMessage.EXPECT_BOOKING_LIMIT_FOR_DISTINCT_ROOM_TO_BE_0_OR_1
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenRoomCategoryAndDistinctRoomsIsMixed() {
        AvailStatusMessageType availStatusMessage1 = this.buildValidRoomCategoryAvailStatusMessage();

        AvailStatusMessageType availStatusMessage2 = this.buildValidRoomCategoryAvailStatusMessage();
        // Setting InvCode != null makes this AvailStatusMessage a "Distinct Room" one
        availStatusMessage2.getStatusApplicationControl().setInvCode(DEFAULT_INV_CODE);

        AvailStatusMessages availStatusMessages = this.buildValidAvailStatusMessages(availStatusMessage1, availStatusMessage2);

        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder()
                .withRoomsCategoriesSupport()
                .withDistinctRoomsSupport()
                .build();

        this.validateAndAssert(
                availStatusMessages,
                ctx,
                ValidationException.class,
                ErrorMessage.EXPECT_ROOM_CATEGORY_AND_DISTINCT_ROOM_TO_NOT_BE_MIXED
        );
    }

    protected abstract void validateAndAssert(
            AvailStatusMessages data,
            AvailStatusMessagesContext ctx,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    );

    protected abstract AvailStatusMessages buildValidAvailStatusMessages(AvailStatusMessageType... messages);

    protected abstract AvailStatusMessageType buildValidRoomCategoryAvailStatusMessage();

}