/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.servlet.middleware;

import it.bz.idm.alpinebits.common.constants.HousekeepingActionEnum;
import it.bz.idm.alpinebits.common.context.RequestContextKey;
import it.bz.idm.alpinebits.common.context.ResponseContextKeys;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.impl.SimpleContext;
import it.bz.idm.alpinebits.servlet.ServletContextKey;
import it.bz.idm.alpinebits.servlet.impl.utils.ServletOutputStreamBuilder;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test cases for {@link HousekeepingWriterMiddleware} class.
 */
public class HousekeepingWriterMiddlewareTest {

    private static final String DEFAULT_VERSION = "2017-10";
    private static final Collection<String> DEFAULT_CAPABILITIES = Arrays.asList(
            "getVersion",
            "getCapabilities",
            "otherAction"
    );

    @Test
    public void testHandleContext_undefinedAction() throws Exception {
        Context ctx = new SimpleContext();
        HttpServletResponse response = mock(HttpServletResponse.class);
        ctx.put(ServletContextKey.SERVLET_RESPONSE, response);

        this.invokeHousekeepingWriterMiddleware(ctx);

        verify(response, never()).getWriter();
    }

    @Test
    public void testHandleContext_notHousekeepingAction() throws Exception {
        Context ctx = new SimpleContext();
        HttpServletResponse response = mock(HttpServletResponse.class);
        ctx.put(ServletContextKey.SERVLET_RESPONSE, response);
        ctx.put(RequestContextKey.REQUEST_ACTION, "some action");

        this.invokeHousekeepingWriterMiddleware(ctx);

        verify(response, never()).getWriter();
    }

    @Test
    public void testHandleContext_getVersionHousekeepingAction() {
        Context ctx = new SimpleContext();
        ctx.put(RequestContextKey.REQUEST_ACTION, HousekeepingActionEnum.GET_VERSION.getAction());
        ctx.put(ResponseContextKeys.RESPONSE_VERSION, DEFAULT_VERSION);

        StringWriter stringWriter = new StringWriter();
        ctx.put(ResponseContextKeys.RESPONSE_CONTENT_STREAM, ServletOutputStreamBuilder.getServletOutputStream(stringWriter));

        this.invokeHousekeepingWriterMiddleware(ctx);

        assertEquals(stringWriter.toString(), "OK:" + DEFAULT_VERSION);
    }

    @Test
    public void testHandleContext_getCapabilitiesHousekeepingAction() {
        Context ctx = new SimpleContext();
        ctx.put(RequestContextKey.REQUEST_ACTION, HousekeepingActionEnum.GET_CAPABLILITIES.getAction());
        ctx.put(ResponseContextKeys.RESPONSE_CAPABILITIES, DEFAULT_CAPABILITIES);

        StringWriter stringWriter = new StringWriter();
        ctx.put(ResponseContextKeys.RESPONSE_CONTENT_STREAM, ServletOutputStreamBuilder.getServletOutputStream(stringWriter));

        this.invokeHousekeepingWriterMiddleware(ctx);

        String result = stringWriter.toString();
        // Check format, starting with "OK:" and capabilities afterwards,
        // separated by the ',' character
        assertTrue(result.matches("OK:([\\w]+)(,[\\w]+)*"));
        DEFAULT_CAPABILITIES.stream()
                .forEach(capability -> assertTrue(result.contains(capability)));
    }

    private void invokeHousekeepingWriterMiddleware(Context ctx) {
        Middleware middleware = new HousekeepingWriterMiddleware();
        middleware.handleContext(ctx, () -> {});
    }
}