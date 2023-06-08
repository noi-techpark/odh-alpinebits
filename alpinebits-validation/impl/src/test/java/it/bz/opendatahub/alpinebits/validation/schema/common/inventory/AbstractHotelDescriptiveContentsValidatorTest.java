// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

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
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents;
import org.testng.annotations.Test;

/**
 * Abstract tests for {@link HotelDescriptiveContents} validator.
 */
public abstract class AbstractHotelDescriptiveContentsValidatorTest {

    protected static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.HOTEL_DESCRIPTIVE_CONTENTS);

    @Test
    public void testValidate_ShouldThrow_WhenHotelDescriptiveContentsIsNull() {
        this.validateAndAssert(
                null,
                NullValidationException.class,
                ErrorMessage.EXPECT_HOTEL_DESCRIPTIVE_CONTENTS_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenContextIsNull() {
        HotelDescriptiveContents contents = new HotelDescriptiveContents();

        this.validateAndAssert(
                contents,
                NullValidationException.class,
                ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL
        );
    }

    protected abstract void validateAndAssert(
            HotelDescriptiveContents data,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    );
}