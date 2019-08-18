/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms;

import it.bz.opendatahub.alpinebits.validation.EmptyCollectionValidationException;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.NotLesserOrEqualValidationException;
import it.bz.opendatahub.alpinebits.validation.NotNullValidationException;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms.context.AvailStatusMessagesContext;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ.AvailStatusMessages;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.StatusApplicationControl;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link AvailStatusMessagesValidator}.
 */
public class AvailStatusMessagesValidatorTest {

    private static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.AVAIL_STATUS_MESSAGES);

    private static final String DEFAULT_HOTEL_CODE = "XYZ";
    private static final String DEFAULT_INV_CODE = "ABC";
    private static final String DEFAULT_INV_TYPE_CODE = "DEF";
    private static final String DEFAULT_BOOKING_LIMIT_MESSAGE_TYPE = "SetLimit";

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
        AvailStatusMessage availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
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
        AvailStatusMessage availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
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
        AvailStatusMessage availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
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
        AvailStatusMessage availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
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
        AvailStatusMessage availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
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
        AvailStatusMessage availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
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
        AvailStatusMessage availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
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
        AvailStatusMessage availStatusMessage = this.buildValidRoomCategoryAvailStatusMessage();
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
        AvailStatusMessage availStatusMessage1 = this.buildValidRoomCategoryAvailStatusMessage();

        AvailStatusMessage availStatusMessage2 = this.buildValidRoomCategoryAvailStatusMessage();
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

    private void validateAndAssert(
            AvailStatusMessages data,
            AvailStatusMessagesContext ctx,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    ) {
        AvailStatusMessagesValidator validator = new AvailStatusMessagesValidator();

        // CHECKSTYLE:OFF
        Exception e = expectThrows(
                exceptionClass,
                () -> validator.validate(data, ctx, VALIDATION_PATH)
        );
        // CHECKSTYLE:ON
        assertEquals(e.getMessage().substring(0, errorMessage.length()), errorMessage);
    }

    private AvailStatusMessages buildValidAvailStatusMessages(AvailStatusMessage... messages) {
        AvailStatusMessages availStatusMessages = new AvailStatusMessages();
        availStatusMessages.setHotelCode(DEFAULT_HOTEL_CODE);

        if (messages != null) {
            availStatusMessages.getAvailStatusMessages().addAll(Arrays.asList(messages));
        }

        return availStatusMessages;
    }

    private AvailStatusMessage buildValidRoomCategoryAvailStatusMessage() {
        StatusApplicationControl statusApplicationControl = this.buildValidStatusApplicationControl();
        AvailStatusMessage availStatusMessage = new AvailStatusMessage();
        availStatusMessage.setStatusApplicationControl(statusApplicationControl);
        availStatusMessage.setBookingLimit(BigInteger.ONE);
        availStatusMessage.setBookingLimitMessageType(DEFAULT_BOOKING_LIMIT_MESSAGE_TYPE);
        return availStatusMessage;
    }

    private StatusApplicationControl buildValidStatusApplicationControl() {
        StatusApplicationControl statusApplicationControl = new StatusApplicationControl();
        statusApplicationControl.setStart(LocalDate.of(2017, 10, 1));
        statusApplicationControl.setEnd(LocalDate.of(2017, 10, 31));
        statusApplicationControl.setInvTypeCode(DEFAULT_INV_TYPE_CODE);
        return statusApplicationControl;
    }
}