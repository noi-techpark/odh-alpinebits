/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing.middleware;

import it.bz.idm.alpinebits.http.HttpContextKey;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Key;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.RequiredContextKeyMissingException;
import it.bz.idm.alpinebits.middleware.impl.SimpleContext;
import it.bz.idm.alpinebits.routing.DefaultRouter;
import it.bz.idm.alpinebits.routing.Router;
import it.bz.idm.alpinebits.routing.UndefinedRouteException;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * Test cases for {@link RoutingMiddleware} class.
 */
public class RoutingMiddlewareTest {

    private static final String DEFAULT_VERSION = "2017-10";
    private static final String DEFAULT_ACTION = "some action";
    private static final String DEFAULT_KEY_IDENTIFIER = "test.key";

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testHandleContext_RouterIsNull() {
        new RoutingMiddleware(null);
    }


    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testHandleContext_AlpineBitsClientProtocolVersionIsNull() {
        Middleware middleware = this.getValidRoutingMiddleware();
        Context ctx = new SimpleContext();
        middleware.handleContext(ctx, null);
    }

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testHandleContext_AlpineBitsActionIsNull() {
        Middleware middleware = this.getValidRoutingMiddleware();
        Context ctx = new SimpleContext();
        ctx.put(HttpContextKey.ALPINE_BITS_CLIENT_PROTOCOL_VERSION, DEFAULT_VERSION);
        middleware.handleContext(ctx, null);
    }

    @Test(expectedExceptions = UndefinedRouteException.class)
    public void testHandleContext_InvalidRouteUnknownVersion() {
        Key<String> testKey = this.getTestCtxKey(DEFAULT_KEY_IDENTIFIER);
        String testValue = "some value";

        Router router = new DefaultRouter.Builder()
                .forVersion("some version")
                .addMiddleware(DEFAULT_ACTION, (ctx, chain) -> ctx.put(testKey, testValue))
                .done()
                .build();
        Middleware middleware = new RoutingMiddleware(router);
        Context ctx = this.getValidContext();
        middleware.handleContext(ctx, null);
    }

    @Test(expectedExceptions = UndefinedRouteException.class)
    public void testHandleContext_InvalidRouteUnknownAction() {
        Key<String> testKey = this.getTestCtxKey(DEFAULT_KEY_IDENTIFIER);
        String testValue = "some value";

        Router router = new DefaultRouter.Builder()
                .forVersion(DEFAULT_VERSION)
                .addMiddleware("some other action", (ctx, chain) -> ctx.put(testKey, testValue))
                .done()
                .build();
        Middleware middleware = new RoutingMiddleware(router);
        Context ctx = this.getValidContext();
        middleware.handleContext(ctx, null);
    }

    @Test
    public void testHandleContext_InvokeRouteWithFoundMiddleware() {
        Key<String> testKey = this.getTestCtxKey(DEFAULT_KEY_IDENTIFIER);
        String testValue = "some value";

        Router router = new DefaultRouter.Builder()
                .forVersion(DEFAULT_VERSION)
                .addMiddleware(DEFAULT_ACTION, (ctx, chain) -> ctx.put(testKey, testValue))
                .done()
                .build();
        Middleware middleware = new RoutingMiddleware(router);
        Context ctx = this.getValidContext();
        middleware.handleContext(ctx, null);

        String resultValue = ctx.getOrThrow(testKey);
        assertEquals(resultValue, testValue);
    }

    @Test
    public void testHandleContext_AddActionToExistingVersion() {
        Key<String> testKey1 = this.getTestCtxKey(DEFAULT_KEY_IDENTIFIER + 1);
        Key<String> testKey2 = this.getTestCtxKey(DEFAULT_KEY_IDENTIFIER + 2);
        String testValue1 = "some value1";
        String testValue2 = "some value2";

        // Configure router with two actions for the same version, where the
        // version specification is repeated
        Router router = new DefaultRouter.Builder()
                .forVersion(DEFAULT_VERSION)
                .addMiddleware(DEFAULT_ACTION, (ctx, chain) -> {
                    ctx.put(testKey1, testValue1);
                    chain.next();
                })
                .done()
                .forVersion(DEFAULT_VERSION)
                .addMiddleware(DEFAULT_ACTION + 2, (ctx, chain) -> {
                    ctx.put(testKey2, testValue2);
                    chain.next();
                })
                .done()
                .build();

        Middleware middleware = new RoutingMiddleware(router);
        Context ctx = new SimpleContext();
        ctx.put(HttpContextKey.ALPINE_BITS_CLIENT_PROTOCOL_VERSION, DEFAULT_VERSION);
        ctx.put(HttpContextKey.ALPINE_BITS_ACTION, DEFAULT_ACTION);

        // The router calls the middleware,
        // specified with HttpContextKey.ALPINE_BITS_CLIENT_PROTOCOL_VERSION
        // and HttpContextKey.ALPINE_BITS_ACTION
        middleware.handleContext(ctx, null);

        String resultValue1 = ctx.getOrThrow(testKey1);
        assertEquals(resultValue1, testValue1);

        // Since the routing matches only one of the two routes, the second middleware
        // is not invoked and the result is empty
        Optional<String> resultValue2 = ctx.get(testKey2);
        assertFalse(resultValue2.isPresent());
    }

    @Test
    public void testHandleContext_OverwriteExistingAction() {
        Key<String> testKey1 = this.getTestCtxKey(DEFAULT_KEY_IDENTIFIER + 1);
        Key<String> testKey2 = this.getTestCtxKey(DEFAULT_KEY_IDENTIFIER + 2);
        String testValue1 = "some value1";
        String testValue2 = "some value2";

        // Configure router with two actions for the same version, where the
        // version and action specification is repeated. Therefor, the first
        // route is overwritten by the second one
        Router router = new DefaultRouter.Builder()
                .forVersion(DEFAULT_VERSION)
                .addMiddleware(DEFAULT_ACTION, (ctx, chain) -> {
                    ctx.put(testKey1, testValue1);
                    chain.next();
                })
                .done()
                .forVersion(DEFAULT_VERSION)
                .addMiddleware(DEFAULT_ACTION, (ctx, chain) -> {
                    ctx.put(testKey2, testValue2);
                    chain.next();
                })
                .done()
                .build();

        Middleware middleware = new RoutingMiddleware(router);
        Context ctx = new SimpleContext();
        ctx.put(HttpContextKey.ALPINE_BITS_CLIENT_PROTOCOL_VERSION, DEFAULT_VERSION);
        ctx.put(HttpContextKey.ALPINE_BITS_ACTION, DEFAULT_ACTION);

        // The router calls the middleware, specified with
        // HttpContextKey.ALPINE_BITS_CLIENT_PROTOCOL_VERSION
        // and HttpContextKey.ALPINE_BITS_ACTION.
        middleware.handleContext(ctx, null);

        Optional<String> resultValue1 = ctx.get(testKey1);
        assertFalse(resultValue1.isPresent());

        String resultValue2 = ctx.getOrThrow(testKey2);
        assertEquals(testValue2, resultValue2);
    }

    private Middleware getValidRoutingMiddleware() {
        Router router = this.getValidRouter();
        return new RoutingMiddleware(router);
    }

    private Router getValidRouter() {
        return new DefaultRouter.Builder().build();
    }

    /**
     * Returns a context with <code>version</code> set to DEFAULT_VERSION
     * and <code>action</code> set to DEFAULT_ACTION.
     * @return
     */
    private Context getValidContext() {
        Context ctx = new SimpleContext();
        ctx.put(HttpContextKey.ALPINE_BITS_CLIENT_PROTOCOL_VERSION, DEFAULT_VERSION);
        ctx.put(HttpContextKey.ALPINE_BITS_ACTION, DEFAULT_ACTION);
        return ctx;
    }

    private Key<String> getTestCtxKey(String keyIdentifier) {
        return Key.key(keyIdentifier, String.class);
    }

}