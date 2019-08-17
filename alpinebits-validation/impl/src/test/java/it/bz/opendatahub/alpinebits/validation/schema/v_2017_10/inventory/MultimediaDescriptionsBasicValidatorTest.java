/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.common.constants.Iso6391;
import it.bz.opendatahub.alpinebits.common.constants.OTACodeInformationType;
import it.bz.opendatahub.alpinebits.validation.ContextWithAction;
import it.bz.opendatahub.alpinebits.validation.EmptyCollectionValidationException;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.NotNullValidationException;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.ImageItems;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.TextItems;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.TextItems.TextItem;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.TextItems.TextItem.Description;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link MultimediaDescriptionsBasicValidator}.
 */
public class MultimediaDescriptionsBasicValidatorTest {

    private static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.MULTIMEDIA_DESCRIPTIONS);

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
        MultimediaDescriptions md = new MultimediaDescriptions();

        this.validateAndAssert(
                md,
                null,
                NullValidationException.class,
                ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenMultimediaDescriptionsIsEmpty() {
        MultimediaDescriptions md = new MultimediaDescriptions();

        this.validateAndAssert(
                md,
                this.buildCtx(),
                EmptyCollectionValidationException.class,
                ErrorMessage.EXPECT_REQUIRED_MULTIMEDIA_DESCRIPTION_TO_EXIST
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenToManyMultimediaDescriptions() {
        MultimediaDescriptions md = new MultimediaDescriptions();
        md.getMultimediaDescriptions().add(new MultimediaDescription());
        md.getMultimediaDescriptions().add(new MultimediaDescription());
        md.getMultimediaDescriptions().add(new MultimediaDescription());
        md.getMultimediaDescriptions().add(new MultimediaDescription());

        this.validateAndAssert(
                md,
                this.buildCtx(),
                ValidationException.class,
                ErrorMessage.EXPECT_MULTIMEDIA_DESCRIPTIONS_TO_HAVE_AT_MOST_THREE_ELEMENTS
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenInfoCodeIsNull() {
        MultimediaDescriptions md = new MultimediaDescriptions();
        md.getMultimediaDescriptions().add(new MultimediaDescription());

        this.validateAndAssert(
                md,
                this.buildCtx(),
                NullValidationException.class,
                ErrorMessage.EXPECT_INFO_CODE_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenInfoCodeIsLesserThanOne() {
        BigInteger code = BigInteger.ZERO;

        MultimediaDescription description = new MultimediaDescription();
        description.setInfoCode(code);

        MultimediaDescriptions md = new MultimediaDescriptions();
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
        BigInteger code = BigInteger.valueOf(27);

        MultimediaDescription description = new MultimediaDescription();
        description.setInfoCode(code);

        MultimediaDescriptions md = new MultimediaDescriptions();
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
        BigInteger code = BigInteger.valueOf(OTACodeInformationType.ADDRESS.getCode());

        MultimediaDescription description1 = new MultimediaDescription();
        description1.setInfoCode(code);

        MultimediaDescription description2 = new MultimediaDescription();
        description2.setInfoCode(code);

        MultimediaDescriptions md = new MultimediaDescriptions();
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
        MultimediaDescription description = new MultimediaDescription();
        description.setInfoCode(BigInteger.ONE);

        MultimediaDescriptions md = new MultimediaDescriptions();
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
        MultimediaDescription description = new MultimediaDescription();
        description.setInfoCode(BigInteger.valueOf(25));

        MultimediaDescriptions md = new MultimediaDescriptions();
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
        MultimediaDescription description = new MultimediaDescription();
        description.setInfoCode(BigInteger.valueOf(25));
        description.setTextItems(new TextItems());
        description.setImageItems(new ImageItems());

        MultimediaDescriptions md = new MultimediaDescriptions();
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
        MultimediaDescription description1 = this.buildValidInfoCode25MultimediaDescription();

        MultimediaDescription description2 = new MultimediaDescription();
        description2.setInfoCode(BigInteger.ONE);

        MultimediaDescriptions md = new MultimediaDescriptions();
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
        MultimediaDescription description1 = this.buildValidInfoCode25MultimediaDescription();

        MultimediaDescription description2 = new MultimediaDescription();
        description2.setInfoCode(BigInteger.ONE);
        description2.setTextItems(this.buildValidInfoCode25TextItems());
        description2.setImageItems(new ImageItems());

        MultimediaDescriptions md = new MultimediaDescriptions();
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
        MultimediaDescription description1 = this.buildValidInfoCode25MultimediaDescription();

        MultimediaDescription description2 = new MultimediaDescription();
        description2.setInfoCode(BigInteger.valueOf(23));
        description2.setTextItems(new TextItems());

        MultimediaDescriptions md = new MultimediaDescriptions();
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
        MultimediaDescription description1 = this.buildValidInfoCode25MultimediaDescription();

        MultimediaDescription description2 = new MultimediaDescription();
        description2.setInfoCode(BigInteger.valueOf(23));

        MultimediaDescriptions md = new MultimediaDescriptions();
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
        MultimediaDescription description1 = this.buildValidInfoCode25MultimediaDescription();

        MultimediaDescription description2 = new MultimediaDescription();
        description2.setInfoCode(BigInteger.valueOf(23));
        description2.setImageItems(new ImageItems());

        MultimediaDescriptions md = new MultimediaDescriptions();
        md.getMultimediaDescriptions().add(description1);
        md.getMultimediaDescriptions().add(description2);

        this.validateAndAssert(
                md,
                this.buildCtx(),
                EmptyCollectionValidationException.class,
                ErrorMessage.EXPECT_IMAGE_ITEM_LIST_TO_BE_NOT_EMPTY
        );
    }

    private void validateAndAssert(
            MultimediaDescriptions data,
            ContextWithAction ctx,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    ) {
        MultimediaDescriptionsBasicValidator validator = new MultimediaDescriptionsBasicValidator();

        // CHECKSTYLE:OFF
        Exception e = expectThrows(
                exceptionClass,
                () -> validator.validate(data, ctx, VALIDATION_PATH)
        );
        // CHECKSTYLE:ON
        assertEquals(e.getMessage().substring(0, errorMessage.length()), errorMessage);
    }

    private MultimediaDescription buildValidInfoCode25MultimediaDescription() {
        TextItems validInfo25TextItems = buildValidInfoCode25TextItems();

        MultimediaDescription multimediaDescription = new MultimediaDescription();
        multimediaDescription.setInfoCode(BigInteger.valueOf(25));
        multimediaDescription.setTextItems(validInfo25TextItems);
        return multimediaDescription;
    }

    private TextItems buildValidInfoCode25TextItems() {
        Description description = new Description();
        description.setLanguage(Iso6391.ABKHAZIAN.getCode());
        description.setTextFormat(Names.PLAIN_TEXT);

        TextItem textItem = new TextItem();
        textItem.getDescriptions().add(description);

        TextItems validInfo25TextItems = new TextItems();
        validInfo25TextItems.setTextItem(textItem);
        return validInfo25TextItems;
    }


    private ContextWithAction buildCtx() {
        return this.buildCtx(null);
    }

    private ContextWithAction buildCtx(String action) {
        return new ContextWithAction(action);
    }

}