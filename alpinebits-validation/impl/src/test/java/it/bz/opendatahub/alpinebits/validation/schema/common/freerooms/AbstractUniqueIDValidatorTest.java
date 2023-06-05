// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.common.freerooms;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.NotEqualValidationException;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms.UniqueIDValidator;
import it.bz.opendatahub.alpinebits.xml.schema.ota.UniqueIDType;
import org.testng.annotations.Test;

/**
 * Abstract tests for {@link UniqueIDValidator}.
 */
public abstract class AbstractUniqueIDValidatorTest {

    protected static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.UNIQUE_ID);

    protected static final String DEFAULT_TYPE = "16";
    protected static final String DEFAULT_ID = "";
    protected static final String DEFAULT_INSTANCE = "CompleteSet";

    @Test
    public void testValidate_ShouldThrow_WhenContextIsNull() {
        UniqueIDType uniqueID = this.buildValidUniqueID();

        this.validateAndAssert(uniqueID, null, NullValidationException.class, ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenTypeIsNull() {
        UniqueIDType uniqueID = this.buildValidUniqueID();
        uniqueID.setType(null);

        this.validateAndAssert(uniqueID, true, NullValidationException.class, ErrorMessage.EXPECT_TYPE_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenTypeIsInvalid() {
        UniqueIDType uniqueID = this.buildValidUniqueID();
        uniqueID.setType("9999");

        this.validateAndAssert(uniqueID, true, ValidationException.class, ErrorMessage.EXPECT_TYPE_TO_BE_16_OR_35);
    }

    @Test
    public void testValidate_ShouldThrow_WhenIDIsNull() {
        UniqueIDType uniqueID = this.buildValidUniqueID();
        uniqueID.setID(null);

        this.validateAndAssert(uniqueID, true, NullValidationException.class, ErrorMessage.EXPECT_ID_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenInstanceIsNull() {
        UniqueIDType uniqueID = this.buildValidUniqueID();
        uniqueID.setInstance(null);

        this.validateAndAssert(uniqueID, true, NullValidationException.class, ErrorMessage.EXPECT_INSTANCE_TO_BE_NOT_NULL);
    }

    @Test
    public void testValidate_ShouldThrow_WhenInstanceIsInvalid() {
        UniqueIDType uniqueID = this.buildValidUniqueID();
        uniqueID.setInstance("invalid text");

        this.validateAndAssert(uniqueID, true, NotEqualValidationException.class, ErrorMessage.EXPECT_INSTANCE_TO_BE_COMPLETE_SET);
    }

    protected abstract void validateAndAssert(
            UniqueIDType data,
            Boolean supportsDeltas,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    );

    protected abstract UniqueIDType buildValidUniqueID();

}