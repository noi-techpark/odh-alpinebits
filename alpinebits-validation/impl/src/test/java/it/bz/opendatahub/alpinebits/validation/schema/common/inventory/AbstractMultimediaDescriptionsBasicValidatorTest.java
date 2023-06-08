// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.common.inventory;

import it.bz.opendatahub.alpinebits.common.constants.Iso6391;
import it.bz.opendatahub.alpinebits.common.constants.OTACodeInformationType;
import it.bz.opendatahub.alpinebits.validation.EmptyCollectionValidationException;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.NotNullValidationException;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.context.inventory.InventoryContext;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ImageItemsType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MultimediaDescriptionType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MultimediaDescriptionsType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.TextDescriptionType.Description;
import it.bz.opendatahub.alpinebits.xml.schema.ota.TextItemsType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.TextItemsType.TextItem;
import org.testng.annotations.Test;

/**
 * Abstract tests for {@link MultimediaDescriptionsType} Basic validator.
 */
public abstract class AbstractMultimediaDescriptionsBasicValidatorTest {

    protected static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.MULTIMEDIA_DESCRIPTIONS);

    @Test
    public void testValidate_ShouldThrow_WhenMultimediaDescriptionsIsNull() {
        this.validateAndAssert(
                null,
                null,
                NullValidationException.class,
                ErrorMessage.EXPECT_MULTIMEDIA_DESCRIPTIONS_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenContexctIsNull() {
        MultimediaDescriptionsType md = new MultimediaDescriptionsType();

        this.validateAndAssert(
                md,
                null,
                NullValidationException.class,
                ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenMultimediaDescriptionsIsEmpty() {
        MultimediaDescriptionsType md = new MultimediaDescriptionsType();

        this.validateAndAssert(
                md,
                this.buildCtx(),
                EmptyCollectionValidationException.class,
                ErrorMessage.EXPECT_REQUIRED_MULTIMEDIA_DESCRIPTION_TO_EXIST
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenToManyMultimediaDescriptions() {
        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(new MultimediaDescriptionType());
        md.getMultimediaDescriptions().add(new MultimediaDescriptionType());
        md.getMultimediaDescriptions().add(new MultimediaDescriptionType());
        md.getMultimediaDescriptions().add(new MultimediaDescriptionType());

        this.validateAndAssert(
                md,
                this.buildCtx(),
                ValidationException.class,
                ErrorMessage.EXPECT_MULTIMEDIA_DESCRIPTIONS_TO_HAVE_AT_MOST_THREE_ELEMENTS
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenInfoCodeIsNull() {
        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(new MultimediaDescriptionType());

        this.validateAndAssert(
                md,
                this.buildCtx(),
                NullValidationException.class,
                ErrorMessage.EXPECT_INFO_CODE_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenInfoCodeIsLesserThanOne() {
        String code = "0";

        MultimediaDescriptionType description = new MultimediaDescriptionType();
        description.setInfoCode(code);

        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(description);

        String errorMessage = String.format(ErrorMessage.EXPECT_INFORMATION_TYPE_CODE_TO_BE_DEFINED, code);
        this.validateAndAssert(
                md,
                this.buildCtx(),
                ValidationException.class,
                errorMessage
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenInfoCodeIsGreaterThanGreatestINF() {
        String code = "27";

        MultimediaDescriptionType description = new MultimediaDescriptionType();
        description.setInfoCode(code);

        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(description);

        String errorMessage = String.format(ErrorMessage.EXPECT_INFORMATION_TYPE_CODE_TO_BE_DEFINED, code);
        this.validateAndAssert(
                md,
                this.buildCtx(),
                ValidationException.class,
                errorMessage
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenDuplicateInfoCodes() {
        String code = OTACodeInformationType.ADDRESS.getCode();

        MultimediaDescriptionType description1 = new MultimediaDescriptionType();
        description1.setInfoCode(code);

        MultimediaDescriptionType description2 = new MultimediaDescriptionType();
        description2.setInfoCode(code);

        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(description1);
        md.getMultimediaDescriptions().add(description2);

        String errorMessage = String.format(ErrorMessage.EXPECT_NO_DUPLICATE_INFO_CODE, code);
        this.validateAndAssert(
                md,
                this.buildCtx(),
                ValidationException.class,
                errorMessage
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenInfoCode25IsNull() {
        MultimediaDescriptionType description = new MultimediaDescriptionType();
        description.setInfoCode("1");

        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(description);

        this.validateAndAssert(
                md,
                this.buildCtx(),
                NullValidationException.class,
                ErrorMessage.EXPECT_INFO_CODE_25_TO_BE_DEFINED
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenInfoCode25TextItemsIsNull() {
        MultimediaDescriptionType description = new MultimediaDescriptionType();
        description.setInfoCode("25");

        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(description);

        this.validateAndAssert(
                md,
                this.buildCtx(),
                NullValidationException.class,
                ErrorMessage.EXPECT_TEXT_ITEMS_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenInfoCode25ImageItemsIsNotNull() {
        MultimediaDescriptionType description = new MultimediaDescriptionType();
        description.setInfoCode("25");
        description.setTextItems(new TextItemsType());
        description.setImageItems(new ImageItemsType());

        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(description);

        this.validateAndAssert(
                md,
                this.buildCtx(),
                NotNullValidationException.class,
                ErrorMessage.EXPECT_IMAGE_ITEMS_TO_BE_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenInfoCode1TextItemsIsNull() {
        MultimediaDescriptionType description1 = this.buildValidInfoCode25MultimediaDescription();

        MultimediaDescriptionType description2 = new MultimediaDescriptionType();
        description2.setInfoCode("1");

        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(description1);
        md.getMultimediaDescriptions().add(description2);

        this.validateAndAssert(
                md,
                this.buildCtx(),
                NullValidationException.class,
                ErrorMessage.EXPECT_TEXT_ITEMS_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenInfoCode1ImageItemsIsNotNull() {
        MultimediaDescriptionType description1 = this.buildValidInfoCode25MultimediaDescription();

        MultimediaDescriptionType description2 = new MultimediaDescriptionType();
        description2.setInfoCode("1");
        description2.setTextItems(this.buildValidInfoCode25TextItems());
        description2.setImageItems(new ImageItemsType());

        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(description1);
        md.getMultimediaDescriptions().add(description2);

        this.validateAndAssert(
                md,
                this.buildCtx(),
                NotNullValidationException.class,
                ErrorMessage.EXPECT_IMAGE_ITEMS_TO_BE_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenInfoCode23TextItemsIsNotNull() {
        MultimediaDescriptionType description1 = this.buildValidInfoCode25MultimediaDescription();

        MultimediaDescriptionType description2 = new MultimediaDescriptionType();
        description2.setInfoCode("23");
        description2.setTextItems(new TextItemsType());

        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(description1);
        md.getMultimediaDescriptions().add(description2);

        this.validateAndAssert(
                md,
                this.buildCtx(),
                NotNullValidationException.class,
                ErrorMessage.EXPECT_TEXT_ITEMS_TO_BE_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenInfoCode23ImageItemsIsNull() {
        MultimediaDescriptionType description1 = this.buildValidInfoCode25MultimediaDescription();

        MultimediaDescriptionType description2 = new MultimediaDescriptionType();
        description2.setInfoCode("23");

        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(description1);
        md.getMultimediaDescriptions().add(description2);

        this.validateAndAssert(
                md,
                this.buildCtx(),
                NullValidationException.class,
                ErrorMessage.EXPECT_IMAGE_ITEMS_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenInfoCode23InnerImageItemsIsNull() {
        MultimediaDescriptionType description1 = this.buildValidInfoCode25MultimediaDescription();

        MultimediaDescriptionType description2 = new MultimediaDescriptionType();
        description2.setInfoCode("23");
        description2.setImageItems(new ImageItemsType());

        MultimediaDescriptionsType md = new MultimediaDescriptionsType();
        md.getMultimediaDescriptions().add(description1);
        md.getMultimediaDescriptions().add(description2);

        this.validateAndAssert(
                md,
                this.buildCtx(),
                EmptyCollectionValidationException.class,
                ErrorMessage.EXPECT_IMAGE_ITEM_LIST_TO_BE_NOT_EMPTY
        );
    }

    protected abstract void validateAndAssert(
            MultimediaDescriptionsType data,
            InventoryContext ctx,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    );

    private MultimediaDescriptionType buildValidInfoCode25MultimediaDescription() {
        TextItemsType validInfo25TextItems = buildValidInfoCode25TextItems();

        MultimediaDescriptionType multimediaDescription = new MultimediaDescriptionType();
        multimediaDescription.setInfoCode("25");
        multimediaDescription.setTextItems(validInfo25TextItems);
        return multimediaDescription;
    }

    private TextItemsType buildValidInfoCode25TextItems() {
        Description description = new Description();
        description.setLanguage(Iso6391.ABKHAZIAN.getCode());
        description.setTextFormat(Names.PLAIN_TEXT);

        TextItem textItem = new TextItem();
        textItem.getDescriptions().add(description);

        TextItemsType validInfo25TextItems = new TextItemsType();
        validInfo25TextItems.getTextItems().add(textItem);
        return validInfo25TextItems;
    }


    private InventoryContext buildCtx() {
        return this.buildCtx(null);
    }

    private InventoryContext buildCtx(String action) {
        return new InventoryContext(action);
    }

}