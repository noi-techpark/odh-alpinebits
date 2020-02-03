/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.freerooms;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.NotEqualValidationException;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelAvailNotifRQ.UniqueID;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link UniqueIDValidator}.
 */
public class UniqueIDValidatorTest {

    private static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.UNIQUE_ID);

    private static final String DEFAULT_TYPE = "16";
    private static final String DEFAULT_ID = "";
    private static final String DEFAULT_INSTANCE = "CompleteSet";

    @Test
    public void testValidate_ShouldThrow_WhenContextIsNull() {
        UniqueID uniqueID = this.buildValidUniqueID();

        this.validateAndAssert(uniqueID, null, NullValidationException.class, ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenDeltasNotSupportedAndUniqueIDIsNull() {
        this.validateAndAssert(null, false, NullValidationException.class, ErrorMessage.EXPECT_UNIQUE_ID_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenTypeIsNull() {
        UniqueID uniqueID = this.buildValidUniqueID();
        uniqueID.setType(null);

        this.validateAndAssert(uniqueID, true, NullValidationException.class, ErrorMessage.EXPECT_TYPE_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenTypeIsInvalid() {
        UniqueID uniqueID = this.buildValidUniqueID();
        uniqueID.setType("9999");

        this.validateAndAssert(uniqueID, true, ValidationException.class, ErrorMessage.EXPECT_TYPE_TO_BE_16_OR_35);
    }

    @Test
    public void testValidate_ShouldThrow_WhenIDIsNull() {
        UniqueID uniqueID = this.buildValidUniqueID();
        uniqueID.setID(null);

        this.validateAndAssert(uniqueID, true, NullValidationException.class, ErrorMessage.EXPECT_ID_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenInstanceIsNull() {
        UniqueID uniqueID = this.buildValidUniqueID();
        uniqueID.setInstance(null);

        this.validateAndAssert(uniqueID, true, NullValidationException.class, ErrorMessage.EXPECT_INSTANCE_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenInstanceIsInvalid() {
        UniqueID uniqueID = this.buildValidUniqueID();
        uniqueID.setInstance("invalid text");

        this.validateAndAssert(uniqueID, true, NotEqualValidationException.class, ErrorMessage.EXPECT_INSTANCE_TO_BE_COMPLETE_SET);
    }

    private void validateAndAssert(
            UniqueID data,
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

    private UniqueID buildValidUniqueID() {
        UniqueID uniqueID = new UniqueID();
        uniqueID.setType(DEFAULT_TYPE);
        uniqueID.setID(DEFAULT_ID);
        uniqueID.setInstance(DEFAULT_INSTANCE);
        return uniqueID;
    }
}