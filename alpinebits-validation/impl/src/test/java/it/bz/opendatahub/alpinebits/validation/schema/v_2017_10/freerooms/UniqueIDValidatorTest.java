/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms;

import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.schema.common.freerooms.AbstractUniqueIDValidatorTest;
import it.bz.opendatahub.alpinebits.xml.schema.ota.UniqueIDType;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link UniqueIDValidator}.
 */
public class UniqueIDValidatorTest extends AbstractUniqueIDValidatorTest {

    protected void validateAndAssert(
            UniqueIDType data,
            Boolean supportsDeltas,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    ) {
        UniqueIDValidator validator = new UniqueIDValidator();

        // CHECKSTYLE:OFF
        Exception e = expectThrows(
                exceptionClass,
                () -> validator.validate(data, supportsDeltas, VALIDATION_PATH)
        );
        // CHECKSTYLE:ON
        assertEquals(e.getMessage().substring(0, errorMessage.length()), errorMessage);
    }

    protected UniqueIDType buildValidUniqueID() {
        UniqueIDType uniqueID = new UniqueIDType();
        uniqueID.setType(DEFAULT_TYPE);
        uniqueID.setID(DEFAULT_ID);
        uniqueID.setInstance(DEFAULT_INSTANCE);
        return uniqueID;
    }
}