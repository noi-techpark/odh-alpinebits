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
import it.bz.opendatahub.alpinebits.xml.schema.ota.VideoDescriptionType.VideoFormat;
import it.bz.opendatahub.alpinebits.xml.schema.ota.VideoItemsType.VideoItem;
import org.testng.annotations.Test;

/**
 * Abstract tests for {@link VideoItem} validator.
 */
public abstract class AbstractVideoItemValidatorTest {

    protected static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.VIDEO_ITEM);

    @Test
    public void testValidate_ShouldThrow_WhenVideoItemIsNull() {
        this.validateAndAssert(null, NullValidationException.class, ErrorMessage.EXPECT_VIDEO_ITEM_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenCategoryIsNull() {
        VideoItem videoItem = new VideoItem();

        this.validateAndAssert(videoItem, NullValidationException.class, ErrorMessage.EXPECT_CATEGORY_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenVideoFormatIsNull() {
        VideoItem videoItem = new VideoItem();
        videoItem.setCategory("1");

        this.validateAndAssert(videoItem, NullValidationException.class, ErrorMessage.EXPECT_VIDEO_FORMAT_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenURLIsNull() {
        VideoItem videoItem = new VideoItem();
        videoItem.setCategory("1");

        VideoFormat videoFormat = new VideoFormat();
        videoItem.getVideoFormats().add(videoFormat);

        this.validateAndAssert(videoItem, NullValidationException.class, ErrorMessage.EXPECT_URL_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenPictureCategoryCodeIsLesserThanOne() {
        VideoItem videoItem = new VideoItem();

        String categoryCode = "0";
        videoItem.setCategory(categoryCode);

        VideoFormat videoFormat = new VideoFormat();
        videoFormat.setURL("some url");
        videoItem.getVideoFormats().add(videoFormat);

        String errorMessage = String.format(ErrorMessage.EXPECT_PICTURE_CATEGORY_CODE_TO_BE_DEFINED, categoryCode);
        this.validateAndAssert(videoItem, ValidationException.class, errorMessage);
    }

    @Test
    public void testValidate_ShouldThrow_WhenPictureCategoryCodeIsGreaterThanGreatestPIC() {
        VideoItem videoItem = new VideoItem();

        String categoryCode = "24";
        videoItem.setCategory(categoryCode);

        VideoFormat videoFormat = new VideoFormat();
        videoFormat.setURL("some url");
        videoItem.getVideoFormats().add(videoFormat);

        String errorMessage = String.format(ErrorMessage.EXPECT_PICTURE_CATEGORY_CODE_TO_BE_DEFINED, categoryCode);
        this.validateAndAssert(videoItem, ValidationException.class, errorMessage);
    }

    protected abstract void validateAndAssert(
            VideoItem data,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    );
}