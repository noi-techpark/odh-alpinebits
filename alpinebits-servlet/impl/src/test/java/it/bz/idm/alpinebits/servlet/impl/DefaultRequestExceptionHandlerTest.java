/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.servlet.impl;

import it.bz.idm.alpinebits.common.exception.AlpineBitsException;
import it.bz.idm.alpinebits.middleware.RequiredContextKeyMissingException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link DefaultRequestExceptionHandlerTest} class.
 */
public class DefaultRequestExceptionHandlerTest {

    private static final String EXCEPTION_MESSAGE = "exception message";
    private static final String EXPECTED_EXCEPTION_MESSAGE = "ERROR:" + EXCEPTION_MESSAGE;

    @DataProvider(name = "exceptions")
    public static Object[][] exceptions() {
        return new Object[][]{
                {new AlpineBitsException(EXCEPTION_MESSAGE, 99), HttpServletResponse.SC_INTERNAL_SERVER_ERROR},
                {new AlpineBitsException(EXCEPTION_MESSAGE, 100), 100},
                {new AlpineBitsException(EXCEPTION_MESSAGE, 599), 599},
                {new AlpineBitsException(EXCEPTION_MESSAGE, 600), HttpServletResponse.SC_INTERNAL_SERVER_ERROR},
                {new RequiredContextKeyMissingException(EXCEPTION_MESSAGE), HttpServletResponse.SC_INTERNAL_SERVER_ERROR},
                {new Exception(EXCEPTION_MESSAGE), HttpServletResponse.SC_INTERNAL_SERVER_ERROR},
        };
    }

    @Test(dataProvider = "exceptions")
    public void testHandleRequestException(Exception e, int expectedStatusCode) throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        DefaultRequestExceptionHandler handler = new DefaultRequestExceptionHandler();

        handler.handleRequestException(request, response, e);

        String expectedErrorMessage = handler.getErrorMessage(e);

        verify(response).setStatus(expectedStatusCode);
        assertEquals(stringWriter.toString(), expectedErrorMessage);
    }

    @Test
    public void testGetErrorMessage() {
        DefaultRequestExceptionHandler handler = new DefaultRequestExceptionHandler();
        String errorMessage = handler.getErrorMessage(new Exception(EXCEPTION_MESSAGE));
        assertEquals(errorMessage, EXPECTED_EXCEPTION_MESSAGE);
    }
}