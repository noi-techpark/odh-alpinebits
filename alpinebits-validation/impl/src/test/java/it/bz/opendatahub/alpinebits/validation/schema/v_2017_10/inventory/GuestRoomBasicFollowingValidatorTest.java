/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.NotNullValidationException;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for following {@link GuestRoom} validator.
 */
public class GuestRoomBasicFollowingValidatorTest {

    private static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.GUEST_ROOM + "[0]");

    private static final String DEFAULT_CODE = "XYZ";

    @Test
    public void testValidate_ShouldThrow_WhenGuestRoomIsNull() {
        this.validateAndAssert(null, NullValidationException.class, ErrorMessage.EXPECT_GUEST_ROOM_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenGuestRoomCodeIsNull() {
        GuestRoomBasicFollowingValidator validator = new GuestRoomBasicFollowingValidator();
        GuestRoom guestRoom = new GuestRoom();

        this.validateAndAssert(guestRoom, NullValidationException.class, ErrorMessage.EXPECT_CODE_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenGuestRoomMinOccupancyIsNotNull() {
        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setCode(DEFAULT_CODE);
        guestRoom.setMinOccupancy(BigInteger.ONE);

        this.validateAndAssert(guestRoom, NotNullValidationException.class, ErrorMessage.EXPECT_MIN_OCCUPANCY_TO_BE_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenGuestRoomMaxOccupancyIsNotNull() {
        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setCode(DEFAULT_CODE);
        guestRoom.setMaxOccupancy(BigInteger.ONE);

        this.validateAndAssert(guestRoom, NotNullValidationException.class, ErrorMessage.EXPECT_MAX_OCCUPANCY_TO_BE_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenGuestRoomMaxChildOccupancyIsNotNull() {
        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setCode(DEFAULT_CODE);
        guestRoom.setMaxChildOccupancy(BigInteger.ONE);

        this.validateAndAssert(guestRoom, NotNullValidationException.class, ErrorMessage.EXPECT_MAX_CHILD_OCCUPANCY_TO_BE_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenGuestRoomIDIsNotNull() {
        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setCode(DEFAULT_CODE);
        guestRoom.setID("");

        this.validateAndAssert(guestRoom, NotNullValidationException.class, ErrorMessage.EXPECT_ID_TO_BE_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenGuestRoomAmenitiesIsNotNull() {
        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setCode(DEFAULT_CODE);
        guestRoom.setAmenities(new GuestRoom.Amenities());

        this.validateAndAssert(guestRoom, NotNullValidationException.class, ErrorMessage.EXPECT_AMENITIES_TO_BE_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenGuestRoomMultimediaDescriptionsIsNotNull() {
        GuestRoom guestRoom = new GuestRoom();
        guestRoom.setCode(DEFAULT_CODE);
        guestRoom.setMultimediaDescriptions(new GuestRoom.MultimediaDescriptions());

        this.validateAndAssert(guestRoom, NotNullValidationException.class, ErrorMessage.EXPECT_MULTIMEDIA_DESCRIPTIONS_TO_BE_NULL);
    }

    private void validateAndAssert(
            GuestRoom data,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    ) {
        GuestRoomBasicFollowingValidator validator = new GuestRoomBasicFollowingValidator();

        // CHECKSTYLE:OFF
        Exception e = expectThrows(
                exceptionClass,
                () -> validator.validate(data, null, VALIDATION_PATH)
        );
        // CHECKSTYLE:ON
        assertEquals(e.getMessage().substring(0, errorMessage.length()), errorMessage);
    }

}