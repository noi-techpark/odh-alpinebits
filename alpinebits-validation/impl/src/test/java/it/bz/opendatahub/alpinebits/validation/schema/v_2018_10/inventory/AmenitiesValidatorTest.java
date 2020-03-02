/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.inventory;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.Amenities;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.Amenities.Amenity;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link AmenitiesValidator}.
 */
public class AmenitiesValidatorTest {

    private static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.AMENITIES);

    @Test
    public void testValidate_ShouldThrow_WhenRoomAmenityCodeIsNull() {
        Amenity amenity = new Amenity();

        Amenities amenities = new Amenities();
        amenities.getAmenities().add(amenity);

        this.validateAndAssert(amenities, NullValidationException.class, ErrorMessage.EXPECT_ROOM_AMENITY_CODE_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenRoomAmenityCodeIsLesserThanOne() {
        BigInteger code = BigInteger.ZERO;

        Amenity amenity = new Amenity();
        amenity.setRoomAmenityCode(code);

        Amenities amenities = new Amenities();
        amenities.getAmenities().add(amenity);

        String errorMessage = String.format(ErrorMessage.EXPECT_ROOM_AMENITY_CODE_TO_BE_DEFINED, code);
        this.validateAndAssert(amenities, ValidationException.class, errorMessage);
    }

    @Test
    public void testValidate_ShouldThrow_WhenRoomAmenityCodeIsGreaterThanGreatestRMA() {
        BigInteger code = BigInteger.valueOf(284);

        Amenity amenity = new Amenity();
        amenity.setRoomAmenityCode(code);

        Amenities amenities = new Amenities();
        amenities.getAmenities().add(amenity);

        String errorMessage = String.format(ErrorMessage.EXPECT_ROOM_AMENITY_CODE_TO_BE_DEFINED, code);
        this.validateAndAssert(amenities, ValidationException.class, errorMessage);
    }

    private void validateAndAssert(
            Amenities data,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    ) {
        AmenitiesValidator validator = new AmenitiesValidator();

        // CHECKSTYLE:OFF
        Exception e = expectThrows(
                exceptionClass,
                () -> validator.validate(data, null, VALIDATION_PATH)
        );
        // CHECKSTYLE:ON
        assertEquals(e.getMessage().substring(0, errorMessage.length()), errorMessage);
    }

}