/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.common.freerooms;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms.StatusApplicationControlValidator;
import it.bz.opendatahub.alpinebits.xml.schema.ota.StatusApplicationControlType;
import org.testng.annotations.Test;

/**
 * Abstract tests for {@link StatusApplicationControlValidator}.
 */
public abstract class AbstractStatusApplicationControlValidatorTest {

    protected static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.STATUS_APPLICATION_CONTROL);

    protected static final String STATUS_APPLICATION_CONTROL_START = "2017-10-01";
    protected static final String STATUS_APPLICATION_CONTROL_END = "2017-10-31";
    protected static final String STATUS_APPLICATION_CONTROL_INV_TYPE_CODE = "DEFAULT_INV_TYPE_CODE";

    @Test
    public void testValidate_ShouldThrow_WhenStatusApplicationCodeIsNull() {
        this.validateAndAssert(
                null,
                NullValidationException.class,
                ErrorMessage.EXPECT_STATUS_APPLICATION_CONTROL_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenStartIsNull() {
        StatusApplicationControlType statusApplicationControl = this.buildValidStatusApplicationControl();
        statusApplicationControl.setStart(null);

        this.validateAndAssert(
                statusApplicationControl,
                NullValidationException.class,
                ErrorMessage.EXPECT_START_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenEndIsNull() {
        StatusApplicationControlType statusApplicationControl = this.buildValidStatusApplicationControl();
        statusApplicationControl.setEnd(null);

        this.validateAndAssert(
                statusApplicationControl,
                NullValidationException.class,
                ErrorMessage.EXPECT_END_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenInvTypeCodeIsNull() {
        StatusApplicationControlType statusApplicationControl = this.buildValidStatusApplicationControl();
        statusApplicationControl.setInvTypeCode(null);

        this.validateAndAssert(
                statusApplicationControl,
                NullValidationException.class,
                ErrorMessage.EXPECT_INV_TYPE_CODE_TO_BE_NOT_NULL
        );
    }

    protected abstract void validateAndAssert(
            StatusApplicationControlType data,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    );

    protected abstract StatusApplicationControlType buildValidStatusApplicationControl();

}