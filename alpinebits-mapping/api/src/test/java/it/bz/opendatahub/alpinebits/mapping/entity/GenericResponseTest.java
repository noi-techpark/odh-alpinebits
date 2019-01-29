/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test cases for {@link GenericResponse} class.
 */
public class GenericResponseTest {

    private static final String SUCCESS_OK = "";
    private static final String MESSAGE = "message";
    private static final Integer WARNING_TYPE = 1;
    private static final Integer ERROR_CODE = 1;

    @Test
    public void testSuccess() {
        GenericResponse genericResponse = GenericResponse.success();
        assertEquals(genericResponse.getSuccess(), SUCCESS_OK);
        assertNull(genericResponse.getWarnings());
        assertNull(genericResponse.getErrors());
    }

    @Test
    public void testWarning() {
        GenericResponse genericResponse = GenericResponse.warning(
                Warning.withoutRecordId(WARNING_TYPE, MESSAGE)
        );
        assertEquals(genericResponse.getSuccess(), SUCCESS_OK);
        assertNull(genericResponse.getErrors());

        assertEquals(genericResponse.getWarnings().size(), 1);

        Warning warning = genericResponse.getWarnings().get(0);
        assertNull(warning.getRecordId());
        assertEquals(warning.getType(), WARNING_TYPE);
        assertEquals(warning.getContent(), MESSAGE);
    }

    @Test
    public void testError() {
        GenericResponse genericResponse = GenericResponse.error(
                Error.withDefaultType(ERROR_CODE, MESSAGE)
        );
        assertNull(genericResponse.getSuccess());
        assertNull(genericResponse.getWarnings());

        assertEquals(genericResponse.getErrors().size(), 1);

        Error error = genericResponse.getErrors().get(0);
        assertEquals(error.getType(), Error.TYPE_DEFAULT);
        assertEquals(error.getCode(), ERROR_CODE);
        assertEquals(error.getContent(), MESSAGE);
    }
}