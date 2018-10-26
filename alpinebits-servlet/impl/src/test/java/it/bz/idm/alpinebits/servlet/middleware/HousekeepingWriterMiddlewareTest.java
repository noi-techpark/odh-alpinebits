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
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

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
        Context ctx = this.buildDefaultContext();
        this.invokeHousekeepingWriterMiddleware(ctx);

        HttpServletResponse response = ctx.getOrThrow(ServletContextKey.SERVLET_RESPONSE);
        verify(response, never()).getWriter();
    }

    @Test
    public void testHandleContext_notHousekeepingAction() throws Exception {
        Context ctx = this.buildDefaultContext();
        ctx.put(RequestContextKey.REQUEST_ACTION, "some action");
        this.invokeHousekeepingWriterMiddleware(ctx);

        HttpServletResponse response = ctx.getOrThrow(ServletContextKey.SERVLET_RESPONSE);
        verify(response, never()).getWriter();
    }

    @Test
    public void testHandleContext_getVersionHousekeepingAction() throws Exception {
        Context ctx = this.buildDefaultContext();
        ctx.put(RequestContextKey.REQUEST_ACTION, HousekeepingActionEnum.GET_VERSION.getAction());
        ctx.put(ResponseContextKeys.RESPONSE_VERSION, DEFAULT_VERSION);

        HttpServletResponse response = ctx.getOrThrow(ServletContextKey.SERVLET_RESPONSE);
        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        this.invokeHousekeepingWriterMiddleware(ctx);

        assertEquals(stringWriter.toString(), "OK:" + DEFAULT_VERSION);
    }

    @Test
    public void testHandleContext_getCapabilitiesHousekeepingAction() throws Exception {
        Context ctx = this.buildDefaultContext();
        ctx.put(RequestContextKey.REQUEST_ACTION, HousekeepingActionEnum.GET_CAPABLILITIES.getAction());
        ctx.put(ResponseContextKeys.RESPONSE_CAPABILITIES, DEFAULT_CAPABILITIES);

        HttpServletResponse response = ctx.getOrThrow(ServletContextKey.SERVLET_RESPONSE);
        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        this.invokeHousekeepingWriterMiddleware(ctx);

        String result = stringWriter.toString();
        // Check format, starting with "OK:" and capabilities afterwards,
        // separated by the ',' character
        assertTrue(result.matches("OK:([\\w]+)(,[\\w]+)*"));
        DEFAULT_CAPABILITIES.stream()
                .forEach(capability -> assertTrue(result.contains(capability)));
    }

    private Context buildDefaultContext() {
        HttpServletResponse response = mock(HttpServletResponse.class);

        Context ctx = new SimpleContext();
        ctx.put(ServletContextKey.SERVLET_RESPONSE, response);
        return ctx;
    }

    private void invokeHousekeepingWriterMiddleware(Context ctx) {
        Middleware middleware = new HousekeepingWriterMiddleware();
        middleware.handleContext(ctx, () -> {});
    }
}