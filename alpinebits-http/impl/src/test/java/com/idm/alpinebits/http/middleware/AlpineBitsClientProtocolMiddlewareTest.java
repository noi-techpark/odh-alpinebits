/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.idm.alpinebits.http.middleware;

import com.idm.alpinebits.http.HttpContextKey;
import com.idm.alpinebits.http.AlpineBitsClientProtocolMissingException;
import com.idm.alpinebits.middleware.Context;
import com.idm.alpinebits.middleware.Middleware;
import com.idm.alpinebits.middleware.RequiredContextKeyMissingException;
import com.idm.alpinebits.middleware.impl.SimpleContext;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Tests cases for {@link AlpineBitsClientProtocolMiddleware} class.
 */
public class AlpineBitsClientProtocolMiddlewareTest {

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testHandleContext_RequestIsNull() {
        Middleware middleware = new AlpineBitsClientProtocolMiddleware();

        Context ctx = new SimpleContext();
        middleware.handleContext(ctx, null);
    }

    @Test(expectedExceptions = AlpineBitsClientProtocolMissingException.class)
    public void testHandleContext_AlpineBitsClientProtocolHeaderMissing() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(AlpineBitsClientProtocolMiddleware.CLIENT_PROTOCOL_VERSION_HEADER))
                .thenReturn(null);

        Context ctx = new SimpleContext();
        ctx.set(HttpContextKey.HTTP_REQUEST, request);

        Middleware middleware = new AlpineBitsClientProtocolMiddleware();
        middleware.handleContext(ctx, null);
    }

    @Test
    public void testHandleContext_AlpineBitsClientProtocolHeaderDefined() {
        String clientProtocolVersion = "2017-10";

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(AlpineBitsClientProtocolMiddleware.CLIENT_PROTOCOL_VERSION_HEADER))
                .thenReturn(clientProtocolVersion);

        Context ctx = new SimpleContext();
        ctx.set(HttpContextKey.HTTP_REQUEST, request);

        Middleware middleware = new AlpineBitsClientProtocolMiddleware();
        middleware.handleContext(ctx, () -> {});

        String clientProtocolVersionValue = ctx.getOrThrow(AlpineBitsClientProtocolMiddleware.AB_CLIENT_PROTOCOL_VERSION, String.class);
        assertEquals(clientProtocolVersionValue, clientProtocolVersion);
    }
}