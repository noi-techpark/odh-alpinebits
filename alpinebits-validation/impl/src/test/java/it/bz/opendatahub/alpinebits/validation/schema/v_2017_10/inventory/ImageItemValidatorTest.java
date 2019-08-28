/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.ImageItems.ImageItem;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.ImageItems.ImageItem.ImageFormat;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link ImageItemValidator}.
 */
public class ImageItemValidatorTest {

    private static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.IMAGE_ITEM);

    @Test
    public void testValidate_ShouldThrow_WhenImageItemIsNull() {
        this.validateAndAssert(null, NullValidationException.class, ErrorMessage.EXPECT_IMAGE_ITEM_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenCategoryIsNull() {
        ImageItem imageItem = new ImageItem();

        this.validateAndAssert(imageItem, NullValidationException.class, ErrorMessage.EXPECT_CATEGORY_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenImageFormatIsNull() {
        ImageItem imageItem = new ImageItem();
        imageItem.setCategory(BigInteger.ONE);

        this.validateAndAssert(imageItem, NullValidationException.class, ErrorMessage.EXPECT_IMAGE_FORMAT_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenURLIsNull() {
        ImageItem imageItem = new ImageItem();
        imageItem.setCategory(BigInteger.ONE);

        ImageFormat imageFormat = new ImageFormat();
        imageItem.setImageFormat(imageFormat);

        this.validateAndAssert(imageItem, NullValidationException.class, ErrorMessage.EXPECT_URL_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenPictureCategoryCodeIsLesserThanOne() {
        ImageItem imageItem = new ImageItem();

        BigInteger categoryCode = BigInteger.ZERO;
        imageItem.setCategory(categoryCode);

        ImageFormat imageFormat = new ImageFormat();
        imageFormat.setURL("some url");
        imageItem.setImageFormat(imageFormat);

        String errorMessage = String.format(ErrorMessage.EXPECT_PICTURE_CATEGORY_CODE_TO_BE_DEFINED, categoryCode);
        this.validateAndAssert(imageItem, ValidationException.class, errorMessage);
    }

    @Test
    public void testValidate_ShouldThrow_WhenPictureCategoryCodeIsGreaterThanGreastPIC() {
        ImageItem imageItem = new ImageItem();

        BigInteger categoryCode = BigInteger.valueOf(24);
        imageItem.setCategory(categoryCode);

        ImageFormat imageFormat = new ImageFormat();
        imageFormat.setURL("some url");
        imageItem.setImageFormat(imageFormat);

        String errorMessage = String.format(ErrorMessage.EXPECT_PICTURE_CATEGORY_CODE_TO_BE_DEFINED, categoryCode);
        this.validateAndAssert(imageItem, ValidationException.class, errorMessage);
    }

    private void validateAndAssert(
            ImageItem data,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    ) {
        ImageItemValidator validator = new ImageItemValidator();

        // CHECKSTYLE:OFF
        Exception e = expectThrows(
                exceptionClass,
                () -> validator.validate(data, null, VALIDATION_PATH)
        );
        // CHECKSTYLE:ON
        assertEquals(e.getMessage().substring(0, errorMessage.length()), errorMessage);
    }
}