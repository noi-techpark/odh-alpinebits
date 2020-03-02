/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.inventory.common;

import it.bz.opendatahub.alpinebits.common.constants.Iso6391;
import it.bz.opendatahub.alpinebits.validation.EmptyCollectionValidationException;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link DescriptionsValidator}.
 */
public class DescriptionsValidatorTest {

    private static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.DESCRIPTION_LIST);

    @Test
    public void testValidate_ShouldThrow_WhenDescriptionsIsNull() {
        this.validateAndAssert(null, NullValidationException.class, ErrorMessage.EXPECT_DESCRIPTIONS_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenDescriptionsIsEmpty() {
        List<Description> descriptions = new ArrayList<>();

        this.validateAndAssert(descriptions, EmptyCollectionValidationException.class, ErrorMessage.EXPECT_DESCRIPTION_TO_EXIST);
    }

    @Test
    public void testValidate_ShouldThrow_WhenDuplicateLanguageAndTextFormat() {
        String textFormat = Names.PLAIN_TEXT;
        String language = Iso6391.ABKHAZIAN.getCode();

        List<Description> descriptions = new ArrayList<>();
        descriptions.add(new Description(null, textFormat, language));
        descriptions.add(new Description(null, textFormat, language));

        String errorMessage = String.format(
                ErrorMessage.EXPECT_NO_DUPLICATE_LANGUAGE_AND_TEXT_FORMAT,
                language,
                textFormat
        );
        this.validateAndAssert(descriptions, ValidationException.class, errorMessage);
    }

    @Test
    public void testValidate_ShouldThrow_WhenHTMLTextFormatOnly() {
        String language = Iso6391.ABKHAZIAN.getCode();
        List<Description> descriptions = new ArrayList<>();
        descriptions.add(new Description(null, Names.HTML, language));

        String errorMessage = String.format(ErrorMessage.EXPECT_PLAIN_TEXT_TO_EXIST, language);
        this.validateAndAssert(descriptions, ValidationException.class, errorMessage);
    }

    private void validateAndAssert(
            List<Description> data,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    ) {
        DescriptionsValidator validator = new DescriptionsValidator();

        // CHECKSTYLE:OFF
        Exception e = expectThrows(
                exceptionClass,
                () -> validator.validate(data, null, VALIDATION_PATH)
        );
        // CHECKSTYLE:ON
        assertEquals(e.getMessage().substring(0, errorMessage.length()), errorMessage);
    }
}