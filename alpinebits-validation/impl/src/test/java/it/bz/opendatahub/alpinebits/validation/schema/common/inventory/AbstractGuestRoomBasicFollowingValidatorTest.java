// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.common.inventory;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.NotNullValidationException;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.xml.schema.ota.FacilityInfoType.GuestRooms.GuestRoom;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MultimediaDescriptionsType;
import org.testng.annotations.Test;

import java.math.BigInteger;

/**
 * Abstract tests for following {@link GuestRoom} validator.
 */
public abstract class AbstractGuestRoomBasicFollowingValidatorTest {

    protected static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.GUEST_ROOM + "[0]");

    protected static final String DEFAULT_CODE = "XYZ";

    @Test
    public void testValidate_ShouldThrow_WhenGuestRoomIsNull() {
        this.validateAndAssert(null, NullValidationException.class, ErrorMessage.EXPECT_GUEST_ROOM_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenGuestRoomCodeIsNull() {
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
        guestRoom.setMultimediaDescriptions(new MultimediaDescriptionsType());

        this.validateAndAssert(guestRoom, NotNullValidationException.class, ErrorMessage.EXPECT_MULTIMEDIA_DESCRIPTIONS_TO_BE_NULL);
    }

    protected abstract void validateAndAssert(GuestRoom data, Class<? extends ValidationException> exceptionClass, String errorMessage);

}