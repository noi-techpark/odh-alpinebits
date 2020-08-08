/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2020_10.inventory;

import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.schema.common.inventory.AbstractImageItemValidatorTest;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ImageItemsType.ImageItem;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link ImageItemValidator}.
 */
public class ImageItemValidatorTest extends AbstractImageItemValidatorTest {

    protected void validateAndAssert(
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