/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.servlet.impl;

import it.bz.idm.alpinebits.servlet.impl.utils.ResponseStatusSettingMiddleware;
import it.bz.idm.alpinebits.servlet.impl.utils.ServletOutputStreamBuilder;
import it.bz.idm.alpinebits.servlet.impl.utils.ThrowingMiddleware;
import it.bz.idm.alpinebits.servlet.impl.utils.ThrowingRequestExceptionHandler;
import org.testng.annotations.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link AlpineBitsServletTest} class.
 */
public class AlpineBitsServletTest {

    @Test(expectedExceptions = ServletException.class)
    public void testInit_NoMiddleware() throws Exception {
        ServletConfig config = mock(ServletConfig.class);
        when(config.getInitParameter(AlpineBitsServlet.MIDDLEWARE_CLASSNAME))
                .thenReturn(null);

        AlpineBitsServlet servlet = new AlpineBitsServlet();
        servlet.init(config);
    }

    @Test
    public void testDoPost_MiddlewareThrowing() throws Exception {
        ServletConfig config = mock(ServletConfig.class);
        when(config.getInitParameter(AlpineBitsServlet.MIDDLEWARE_CLASSNAME))
                .thenReturn(ThrowingMiddleware.class.getName());

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        when(response.getOutputStream()).thenReturn(ServletOutputStreamBuilder.getServletOutputStream(stringWriter));

        AlpineBitsServlet servlet = new AlpineBitsServlet();
        servlet.init(config);

        servlet.doPost(request, response);

        // This test assumes, that the servlets RequestExceptionHandler catches the middleware
        // exception and sets the response status
        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        assertEquals(stringWriter.toString(), ThrowingMiddleware.EXPECTED_EXCEPTION_MESSAGE);
    }

    @Test
    public void testDoPost_MiddlewareAndExceptionHandlerThrowing() throws Exception {
        ServletConfig config = mock(ServletConfig.class);
        when(config.getInitParameter(AlpineBitsServlet.MIDDLEWARE_CLASSNAME))
                .thenReturn(ThrowingMiddleware.class.getName());
        when(config.getInitParameter(AlpineBitsServlet.REQUEST_EXCEPTION_HANDLER_CLASSNAME))
                .thenReturn(ThrowingRequestExceptionHandler.class.getName());

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        when(response.getOutputStream()).thenReturn(ServletOutputStreamBuilder.getServletOutputStream(stringWriter));

        AlpineBitsServlet servlet = new AlpineBitsServlet();
        servlet.init(config);

        servlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testDoPost_MiddlewareOk() throws Exception {
        ServletConfig config = mock(ServletConfig.class);
        when(config.getInitParameter(AlpineBitsServlet.MIDDLEWARE_CLASSNAME))
                .thenReturn(ResponseStatusSettingMiddleware.class.getName());

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(response.getOutputStream()).thenReturn(ServletOutputStreamBuilder.getServletOutputStream(null));

        AlpineBitsServlet servlet = new AlpineBitsServlet();
        servlet.init(config);

        servlet.doPost(request, response);

        // This test assumes, that the servlets RequestExceptionHandler catches the middleware
        // exception and sets the response using the HttpServletResponse#sendError(int, String) method.
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}