// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms;

import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.schema.common.freerooms.AbstractOTAHotelAvailNotifRQValidatorTest;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRQ;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link OTAHotelAvailNotifRQValidator}.
 */
public class OTAHotelAvailNotifRQValidatorTest extends AbstractOTAHotelAvailNotifRQValidatorTest {

    protected void validateAndAssert(
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