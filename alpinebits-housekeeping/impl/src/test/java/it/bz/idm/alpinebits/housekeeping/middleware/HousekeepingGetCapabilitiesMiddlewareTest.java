/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.housekeeping.middleware;

import it.bz.idm.alpinebits.common.constants.HousekeepingActionEnum;
import it.bz.idm.alpinebits.common.context.RequestContextKey;
import it.bz.idm.alpinebits.common.context.ResponseContextKeys;
import it.bz.idm.alpinebits.housekeeping.VersionMismatchException;
import it.bz.idm.alpinebits.housekeeping.middleware.utils.RouterBuilder;
import it.bz.idm.alpinebits.housekeeping.middleware.utils.RouterMiddlewareBuilder;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.RequiredContextKeyMissingException;
import it.bz.idm.alpinebits.middleware.impl.SimpleContext;
import it.bz.idm.alpinebits.routing.RouterContextKey;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test cases for {@link HousekeepingGetCapabilitiesMiddleware} class.
 */
public class HousekeepingGetCapabilitiesMiddlewareTest {

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testHandleContext_AlpineBitsRouterIsNull() {
        Middleware middleware = new HousekeepingGetCapabilitiesMiddleware();
        Context ctx = new SimpleContext();
        middleware.handleContext(ctx, null);
    }

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testHandleContext_RequestVersionIsNull() {
        Middleware middleware = new HousekeepingGetCapabilitiesMiddleware();
        Context ctx = new SimpleContext();
        ctx.put(RouterContextKey.ALPINEBITS_ROUTER, RouterBuilder.buildDefaultRouter());
        middleware.handleContext(ctx, null);
    }

    @Test(expectedExceptions = VersionMismatchException.class)
    public void testHandleContext_NoCapabilitiesPresent() {
        Middleware middleware = new HousekeepingGetCapabilitiesMiddleware();
        Context ctx = new SimpleContext();
        ctx.put(RouterContextKey.ALPINEBITS_ROUTER, RouterBuilder.buildDefaultRouter());
        ctx.put(RequestContextKey.REQUEST_VERSION, "some version");
        middleware.handleContext(ctx, null);
    }

    @Test
    public void testHandleContext_ReturnSingleCapability() {
        Context ctx = new SimpleContext();
        ctx.put(RequestContextKey.REQUEST_VERSION, RouterMiddlewareBuilder.DEFAULT_VERSION);
        ctx.put(RequestContextKey.REQUEST_ACTION, HousekeepingActionEnum.GET_CAPABLILITIES.getAction());

        // This routing middleware is configured for a single action: getCapabilitites,
        // implemented by HousekeepingGetCapabilitiesMiddleware.class. Therefore, when
        // invoked with getCapabilities as action, it returns exactly this action
        Middleware routingMiddleware = RouterMiddlewareBuilder.buildRoutingMiddlewareWithCapabilititesAction();
        routingMiddleware.handleContext(ctx, null);

        // Read the capabilities response from the context
        Collection<String> actions = ctx.getOrThrow(ResponseContextKeys.RESPONSE_CAPABILITIES);

        assertEquals(actions, Collections.singletonList(HousekeepingActionEnum.GET_CAPABLILITIES.getAction()));
    }

    @Test
    public void testHandleContext_ReturnManyCapabilitites() {
        Context ctx = new SimpleContext();
        ctx.put(RequestContextKey.REQUEST_VERSION, RouterMiddlewareBuilder.DEFAULT_VERSION);
        ctx.put(RequestContextKey.REQUEST_ACTION, HousekeepingActionEnum.GET_CAPABLILITIES.getAction());

        // This routing middleware is configured for the getCapabilitites and
        // RouterMiddlewareBuilder#DEFAULT_ACTION. Therefore, when invoked with
        // getCapabilities as action, it returns two actions
        Middleware routingMiddleware = RouterMiddlewareBuilder.buildRoutingMiddlewareWithCapabilititesAndCustomAction();
        routingMiddleware.handleContext(ctx, null);

        // Read the capabilities response from the context
        Collection<String> actions = ctx.getOrThrow(ResponseContextKeys.RESPONSE_CAPABILITIES);

        Set<String> expectedActions = new HashSet<>(
                Arrays.asList(
                        HousekeepingActionEnum.GET_CAPABLILITIES.getAction(),
                        RouterMiddlewareBuilder.DEFAULT_ACTION
                )
        );
        assertTrue(actions.equals(expectedActions));
    }
}