/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link OTAHotelAvailNotifRQValidator}.
 */
public class OTAHotelAvailNotifRQValidatorTest {

    private static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.OTA_HOTEL_AVAIL_NOTIF_RQ);

    @Test
    public void testValidate_ShouldThrow_WhenOTAHotelAvailNotifRQIsNull() {
        this.validateAndAssert(null, NullValidationException.class, ErrorMessage.EXPECT_HOTEL_AVAIL_NOTIF_RQ_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenContextIsNull() {
        OTAHotelAvailNotifRQ rq = new OTAHotelAvailNotifRQ();
        this.validateAndAssert(rq, NullValidationException.class, ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL);
    }

    private void validateAndAssert(
            OTAHotelAvailNotifRQ data,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    ) {
        OTAHotelAvailNotifRQValidator validator = new OTAHotelAvailNotifRQValidator();

        // CHECKSTYLE:OFF
        Exception e = expectThrows(
                exceptionClass,
                () -> validator.validate(data, null, VALIDATION_PATH)
        );
        // CHECKSTYLE:ON
        assertEquals(e.getMessage().substring(0, errorMessage.length()), errorMessage);
    }
}