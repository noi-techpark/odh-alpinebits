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
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage.StatusApplicationControl;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link StatusApplicationControlValidator}.
 */
public class StatusApplicationControlValidatorTest {

    private static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.STATUS_APPLICATION_CONTROL);

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
        StatusApplicationControl statusApplicationControl = this.buildValidStatusApplicationControl();
        statusApplicationControl.setStart(null);

        this.validateAndAssert(
                statusApplicationControl,
                NullValidationException.class,
                ErrorMessage.EXPECT_START_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenEndIsNull() {
        StatusApplicationControl statusApplicationControl = this.buildValidStatusApplicationControl();
        statusApplicationControl.setEnd(null);

        this.validateAndAssert(
                statusApplicationControl,
                NullValidationException.class,
                ErrorMessage.EXPECT_END_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenInvTypeCodeIsNull() {
        StatusApplicationControl statusApplicationControl = this.buildValidStatusApplicationControl();
        statusApplicationControl.setInvTypeCode(null);

        this.validateAndAssert(
                statusApplicationControl,
                NullValidationException.class,
                ErrorMessage.EXPECT_INV_TYPE_CODE_TO_BE_NOT_NULL
        );
    }

    private void validateAndAssert(
            StatusApplicationControl data,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    ) {
        StatusApplicationControlValidator validator = new StatusApplicationControlValidator();

        // CHECKSTYLE:OFF
        Exception e = expectThrows(
                exceptionClass,
                () -> validator.validate(data, null, VALIDATION_PATH)
        );
        // CHECKSTYLE:ON
        assertEquals(e.getMessage().substring(0, errorMessage.length()), errorMessage);
    }

    private StatusApplicationControl buildValidStatusApplicationControl() {
        StatusApplicationControl statusApplicationControl = new StatusApplicationControl();
        statusApplicationControl.setStart(LocalDate.of(2017, 10, 1));
        statusApplicationControl.setEnd(LocalDate.of(2017, 10, 31));
        statusApplicationControl.setInvTypeCode("DEFAULT_INV_TYPE_CODE");
        return statusApplicationControl;
    }
}