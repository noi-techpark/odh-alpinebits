/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.housekeeping.middleware;

import it.bz.idm.alpinebits.common.context.RequestContextKey;
import it.bz.idm.alpinebits.common.context.ResponseContextKeys;
import it.bz.idm.alpinebits.housekeeping.middleware.utils.RouterBuilder;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.RequiredContextKeyMissingException;
import it.bz.idm.alpinebits.middleware.impl.SimpleContext;
import it.bz.idm.alpinebits.routing.RouterContextKey;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link HousekeepingGetVersionMiddleware} class.
 */
public class HousekeepingGetVersionMiddlewareTest {

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testHandleContext_AlpineBitsRouterIsNull() {
        Context ctx = new SimpleContext();

        Middleware middleware = new HousekeepingGetVersionMiddleware();
        middleware.handleContext(ctx, null);
    }

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testHandleContext_RequestVersionIsNull() {
        Context ctx = new SimpleContext();
        ctx.put(RouterContextKey.ALPINEBITS_ROUTER, RouterBuilder.buildDefaultRouter());

        Middleware middleware = new HousekeepingGetVersionMiddleware();
        middleware.handleContext(ctx, null);
    }

    @Test
    public void testHandleContext_ReturnSameVersionIfMatch() {
        String version = RouterBuilder.DEFAULT_VERSION;

        Context ctx = new SimpleContext();
        ctx.put(RouterContextKey.ALPINEBITS_ROUTER, RouterBuilder.buildRouterForVersion(version));
        ctx.put(RequestContextKey.REQUEST_VERSION, version);

        Middleware routingMiddleware = new HousekeepingGetVersionMiddleware();
        routingMiddleware.handleContext(ctx, null);

        String resultVersion = ctx.getOrThrow(ResponseContextKeys.RESPONSE_VERSION);

        assertEquals(resultVersion, version);
    }

    @Test
    public void testHandleContext_ReturnHighestVersionIfNoMatch() {
        String version = RouterBuilder.DEFAULT_VERSION;
        String otherVersion = version + "1";

        Context ctx = new SimpleContext();
        ctx.put(RouterContextKey.ALPINEBITS_ROUTER, RouterBuilder.buildRouterForVersion(version));
        ctx.put(RequestContextKey.REQUEST_VERSION, otherVersion);

        Middleware routingMiddleware = new HousekeepingGetVersionMiddleware();
        routingMiddleware.handleContext(ctx, null);

        String resultVersion = ctx.getOrThrow(ResponseContextKeys.RESPONSE_VERSION);

        assertEquals(resultVersion, version);
    }
}