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
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveContentNotifRQ;
import org.testng.annotations.Test;

/**
 * Abstract tests for {@link OTAHotelDescriptiveContentNotifRQ} validator.
 */
public abstract class AbstractOTAHotelDescriptiveContentNotifRQValidatorTest {

    protected static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.OTA_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_RQ);

    @Test
    public void testValidate_ShouldThrow_WhenOTAHotelDescriptiveContentNotifRQIsNull() {
        this.validateAndAssert(
                null,
                NullValidationException.class,
                ErrorMessage.EXPECT_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_RQ_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenContextIsNull() {
        OTAHotelDescriptiveContentNotifRQ rq = new OTAHotelDescriptiveContentNotifRQ();

        this.validateAndAssert(
                rq,
                NullValidationException.class,
                ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL
        );
    }

    protected abstract void validateAndAssert(
            OTAHotelDescriptiveContentNotifRQ data,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    );

}