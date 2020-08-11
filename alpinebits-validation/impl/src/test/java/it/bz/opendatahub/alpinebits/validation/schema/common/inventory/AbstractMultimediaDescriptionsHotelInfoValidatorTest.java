/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.common.inventory;

import it.bz.opendatahub.alpinebits.validation.EmptyCollectionValidationException;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.NotNullValidationException;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ImageItemsType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MultimediaDescriptionType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MultimediaDescriptionsType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.TextItemsType;
import org.testng.annotations.Test;

/**
 * Abstract tests for {@link MultimediaDescriptionsType} HotelInfo Validator.
 */
public abstract class AbstractMultimediaDescriptionsHotelInfoValidatorTest {

    protected static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.MULTIMEDIA_DESCRIPTIONS);

    @Test
    public void testValidate_ShouldThrow_WhenInfoCodeIsNotNull() {
        MultimediaDescriptionType description = new MultimediaDescriptionType();
        description.setInfoCode("1");

        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(description);

        this.validateAndAssert(md, NotNullValidationException.class, ErrorMessage.EXPECT_INFO_CODE_TO_BE_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenTextItemsIsNotNull() {
        MultimediaDescriptionType description = new MultimediaDescriptionType();
        description.setTextItems(new TextItemsType());

        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(description);

        this.validateAndAssert(md, NotNullValidationException.class, ErrorMessage.EXPECT_TEXT_ITEMS_TO_BE_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenImageItemsIsNull() {
        MultimediaDescriptionType description = new MultimediaDescriptionType();

        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(description);

        this.validateAndAssert(md, NullValidationException.class, ErrorMessage.EXPECT_IMAGE_ITEMS_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenImageItemsIsEmpty() {
        MultimediaDescriptionType description = new MultimediaDescriptionType();
        description.setImageItems(new ImageItemsType());

        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(description);

        this.validateAndAssert(md, EmptyCollectionValidationException.class, ErrorMessage.EXPECT_IMAGE_ITEM_LIST_TO_BE_NOT_EMPTY);
    }

    protected abstract void validateAndAssert(
            MultimediaDescriptionsType data,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    );
}