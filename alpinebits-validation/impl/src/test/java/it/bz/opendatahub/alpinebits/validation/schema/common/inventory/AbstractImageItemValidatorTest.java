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
import it.bz.opendatahub.alpinebits.xml.schema.ota.ImageDescriptionType.ImageFormat;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ImageItemsType.ImageItem;
import org.testng.annotations.Test;

/**
 * Abstract tests for {@link ImageItem} validator.
 */
public abstract class AbstractImageItemValidatorTest {

    protected static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.IMAGE_ITEM);

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
        imageItem.setCategory("1");

        this.validateAndAssert(imageItem, NullValidationException.class, ErrorMessage.EXPECT_IMAGE_FORMAT_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenURLIsNull() {
        ImageItem imageItem = new ImageItem();
        imageItem.setCategory("1");

        ImageFormat imageFormat = new ImageFormat();
        imageItem.getImageFormats().add(imageFormat);

        this.validateAndAssert(imageItem, NullValidationException.class, ErrorMessage.EXPECT_URL_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenPictureCategoryCodeIsLesserThanOne() {
        ImageItem imageItem = new ImageItem();

        String categoryCode = "0";
        imageItem.setCategory(categoryCode);

        ImageFormat imageFormat = new ImageFormat();
        imageFormat.setURL("some url");
        imageItem.getImageFormats().add(imageFormat);

        String errorMessage = String.format(ErrorMessage.EXPECT_PICTURE_CATEGORY_CODE_TO_BE_DEFINED, categoryCode);
        this.validateAndAssert(imageItem, ValidationException.class, errorMessage);
    }

    @Test
    public void testValidate_ShouldThrow_WhenPictureCategoryCodeIsGreaterThanGreastPIC() {
        ImageItem imageItem = new ImageItem();

        String categoryCode = "24";
        imageItem.setCategory(categoryCode);

        ImageFormat imageFormat = new ImageFormat();
        imageFormat.setURL("some url");
        imageItem.getImageFormats().add(imageFormat);

        String errorMessage = String.format(ErrorMessage.EXPECT_PICTURE_CATEGORY_CODE_TO_BE_DEFINED, categoryCode);
        this.validateAndAssert(imageItem, ValidationException.class, errorMessage);
    }

    protected abstract void validateAndAssert(
            ImageItem data,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    );
}