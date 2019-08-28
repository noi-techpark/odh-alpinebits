/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.validation.EmptyCollectionValidationException;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.NotNullValidationException;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.ImageItems;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.TextItems;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link MultimediaDescriptionsHotelInfoValidator}.
 */
public class MultimediaDescriptionsHotelInfoValidatorTest {

    private static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.MULTIMEDIA_DESCRIPTIONS);

    @Test
    public void testValidate_ShouldThrow_WhenInfoCodeIsNotNull() {
        MultimediaDescription description = new MultimediaDescription();
        description.setInfoCode(BigInteger.ONE);

        MultimediaDescriptions md = new MultimediaDescriptions();
        md.getMultimediaDescriptions().add(description);

        this.validateAndAssert(md, NotNullValidationException.class, ErrorMessage.EXPECT_INFO_CODE_TO_BE_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenTextItemsIsNotNull() {
        MultimediaDescription description = new MultimediaDescription();
        description.setTextItems(new TextItems());

        MultimediaDescriptions md = new MultimediaDescriptions();
        md.getMultimediaDescriptions().add(description);

        this.validateAndAssert(md, NotNullValidationException.class, ErrorMessage.EXPECT_TEXT_ITEMS_TO_BE_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenImageItemsIsNull() {
        MultimediaDescription description = new MultimediaDescription();

        MultimediaDescriptions md = new MultimediaDescriptions();
        md.getMultimediaDescriptions().add(description);

        this.validateAndAssert(md, NullValidationException.class, ErrorMessage.EXPECT_IMAGE_ITEMS_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenImageItemsIsEmpty() {
        MultimediaDescription description = new MultimediaDescription();
        description.setImageItems(new ImageItems());

        MultimediaDescriptions md = new MultimediaDescriptions();
        md.getMultimediaDescriptions().add(description);

        this.validateAndAssert(md, EmptyCollectionValidationException.class, ErrorMessage.EXPECT_IMAGE_ITEM_LIST_TO_BE_NOT_EMPTY);
    }

    private void validateAndAssert(
            MultimediaDescriptions data,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    ) {
        MultimediaDescriptionsHotelInfoValidator validator = new MultimediaDescriptionsHotelInfoValidator();

        // CHECKSTYLE:OFF
        Exception e = expectThrows(
                exceptionClass,
                () -> validator.validate(data, null, VALIDATION_PATH)
        );
        // CHECKSTYLE:ON
        assertEquals(e.getMessage().substring(0, errorMessage.length()), errorMessage);
    }
}