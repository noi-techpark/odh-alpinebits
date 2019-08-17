/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.validation.ContextWithAction;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.NotLesserOrEqualValidationException;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for heading {@link GuestRoom} validator.
 */
public class GuestRoomBasicHeadingValidatorTest {

    private static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.GUEST_ROOM + "[0]");
    private static final String DEFAULT_CODE = "XYZ";

    @Test
    public void testValidate_ShouldThrow_WhenGuestRoomIsNull() {
        this.validateAndAssert(
                null,
                null,
                NullValidationException.class,
                ErrorMessage.EXPECT_GUEST_ROOM_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenGuestRoomCodeIsNull() {
        GuestRoom guestRoom = new GuestRoom();

        this.validateAndAssert(
                guestRoom,
                this.buildCtx(),
                NullValidationException.class,
                ErrorMessage.EXPECT_CODE_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenMinOccupancyIsNull() {
        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setCode(DEFAULT_CODE);

        this.validateAndAssert(
                guestRoom,
                this.buildCtx(),
                NullValidationException.class,
                ErrorMessage.EXPECT_MIN_OCCUPANCY_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenMaxOccupancyIsNull() {
        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setCode(DEFAULT_CODE);
        guestRoom.setMinOccupancy(BigInteger.ONE);

        this.validateAndAssert(
                guestRoom,
                this.buildCtx(),
                NullValidationException.class,
                ErrorMessage.EXPECT_MAX_OCCUPANCY_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenMaxOccupancyIsLesserThanMaxChildOccupancy() {
        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setCode(DEFAULT_CODE);
        guestRoom.setMinOccupancy(BigInteger.ONE);
        guestRoom.setMaxOccupancy(BigInteger.ONE);
        guestRoom.setMaxChildOccupancy(BigInteger.TEN);

        this.validateAndAssert(
                guestRoom,
                this.buildCtx(),
                NotLesserOrEqualValidationException.class,
                ErrorMessage.EXPECT_MAX_CHILD_OCCUPANCY_TO_BE_LESSER_OR_EQUAL_THAN_MAX_OCCUPANCY
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenMaxChildOccupancyIsLesserThanZero() {
        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setCode(DEFAULT_CODE);
        guestRoom.setMinOccupancy(BigInteger.ONE);
        guestRoom.setMaxOccupancy(BigInteger.ONE);
        guestRoom.setMaxChildOccupancy(BigInteger.valueOf(-1));

        String errorMessage = ErrorMessage.EXPECT_ZERO_TO_BE_LESSER_OR_EQUAL_THAN_MAX_CHILD_OCCUPANCY;
        this.validateAndAssert(
                guestRoom,
                this.buildCtx(),
                NotLesserOrEqualValidationException.class,
                errorMessage
        );
    }

    private void validateAndAssert(
            GuestRoom data,
            ContextWithAction ctx,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    ) {
        GuestRoomBasicHeadingValidator validator = new GuestRoomBasicHeadingValidator();

        // CHECKSTYLE:OFF
        Exception e = expectThrows(
                exceptionClass,
                () -> validator.validate(data, ctx, VALIDATION_PATH)
        );
        // CHECKSTYLE:ON
        assertEquals(e.getMessage().substring(0, errorMessage.length()), errorMessage);
    }

    private ContextWithAction buildCtx() {
        return this.buildCtx(null);
    }

    private ContextWithAction buildCtx(String action) {
        return new ContextWithAction(action);
    }

}