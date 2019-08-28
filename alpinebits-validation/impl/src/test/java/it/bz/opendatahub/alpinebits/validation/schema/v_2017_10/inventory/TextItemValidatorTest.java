/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.validation.EmptyCollectionValidationException;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.TextItems.TextItem;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link TextItemValidator}.
 */
public class TextItemValidatorTest {

    private static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.TEXT_ITEM);

    @Test
    public void testValidate_ShouldThrow_WhenTextItemIsNull() {
        this.validateAndAssert(null, NullValidationException.class, ErrorMessage.EXPECT_TEXT_ITEM_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenDescriptionsIsEmpty() {
        TextItem textItem = new TextItem();

        this.validateAndAssert(
                textItem,
                EmptyCollectionValidationException.class,
                ErrorMessage.EXPECT_DESCRIPTION_LIST_TO_BE_NOT_EMPTY
        );
    }

    private void validateAndAssert(
            TextItem data,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    ) {
        TextItemValidator validator = new TextItemValidator();

        // CHECKSTYLE:OFF
        Exception e = expectThrows(
                exceptionClass,
                () -> validator.validate(data, null, VALIDATION_PATH)
        );
        // CHECKSTYLE:ON
        assertEquals(e.getMessage().substring(0, errorMessage.length()), errorMessage);
    }

}