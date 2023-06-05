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
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.xml.schema.ota.FacilityInfoType.GuestRooms.GuestRoom.Amenities;
import it.bz.opendatahub.alpinebits.xml.schema.ota.FacilityInfoType.GuestRooms.GuestRoom.Amenities.Amenity;
import org.testng.annotations.Test;

/**
 * Abstract tests for {@link Amenities} validator.
 */
public abstract class AbstractAmenitiesValidatorTest {

    protected static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.AMENITIES);

    @Test
    public void testValidate_ShouldThrow_WhenRoomAmenityCodeIsNull() {
        Amenity amenity = new Amenity();

        Amenities amenities = new Amenities();
        amenities.getAmenities().add(amenity);

        this.validateAndAssert(amenities, NullValidationException.class, ErrorMessage.EXPECT_ROOM_AMENITY_CODE_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenRoomAmenityCodeIsLesserThanOne() {
        String code = "0";

        Amenity amenity = new Amenity();
        amenity.setRoomAmenityCode(code);

        Amenities amenities = new Amenities();
        amenities.getAmenities().add(amenity);

        String errorMessage = String.format(ErrorMessage.EXPECT_ROOM_AMENITY_CODE_TO_BE_DEFINED, code);
        this.validateAndAssert(amenities, ValidationException.class, errorMessage);
    }

    @Test
    public void testValidate_ShouldThrow_WhenRoomAmenityCodeIsGreaterThanGreatestRMA() {
        String code = "284";

        Amenity amenity = new Amenity();
        amenity.setRoomAmenityCode(code);

        Amenities amenities = new Amenities();
        amenities.getAmenities().add(amenity);

        String errorMessage = String.format(ErrorMessage.EXPECT_ROOM_AMENITY_CODE_TO_BE_DEFINED, code);
        this.validateAndAssert(amenities, ValidationException.class, errorMessage);
    }

    protected abstract void validateAndAssert(Amenities data, Class<? extends ValidationException> exceptionClass, String errorMessage);

}