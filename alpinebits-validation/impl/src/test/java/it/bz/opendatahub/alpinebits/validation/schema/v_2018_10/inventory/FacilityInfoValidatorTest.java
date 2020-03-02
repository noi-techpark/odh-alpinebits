/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.inventory;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Names;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link FacilityInfoValidator}.
 */
public class FacilityInfoValidatorTest {

    private static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.FACILITY_INFO);

    @Test
    public void testValidate_ShouldThrow_WhenFacilityInfoIsNull() {
        FacilityInfoValidator validator = new FacilityInfoValidator();

        // CHECKSTYLE:OFF
        Exception e = expectThrows(
                NullValidationException.class,
                () -> validator.validate(null, null, VALIDATION_PATH)
        );
        // CHECKSTYLE:ON
        String message = ErrorMessage.EXPECT_FACILITY_INFO_TO_BE_NOT_NULL;
        assertEquals(e.getMessage().substring(0, message.length()), message);
    }

}