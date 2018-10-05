/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Context;
import it.idm.alpinebits.middleware.Middleware;
import it.idm.alpinebits.middleware.MiddlewareChain;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertEquals;

/**
 * Tests cases for {@link SequentialMiddlewareChain} class.
 */
public class SequentialMiddlewareChainTest {

    private static final String KEY_1 = "key1";
    private static final String VALUE_1 = "value1";
    private static final String KEY_2 = "key2";
    private static final String VALUE_2 = "value2";

    @DataProvider(name = "testNextDataProvider")
    public static Object[][] middlewareCallsNext() {
        return new Object[][] {
                {true, true, Optional.of(VALUE_2)},
                {true, false, Optional.of(VALUE_2)},
                {false, true, Optional.empty()},
                {false, false, Optional.empty()},
        };
    }

    @Test(dataProvider = "testNextDataProvider")
    public void testNext(
            boolean middleware1CallsNext,
            boolean middleware2CallsNext,
            Optional<String> expectedValue
    ) throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        Middleware middleware1 = MiddlewareBuilder.buildMiddleware(KEY_1, VALUE_1, middleware1CallsNext);
        Middleware middleware2 = MiddlewareBuilder.buildMiddleware(KEY_2, VALUE_2, middleware2CallsNext);

        MiddlewareChain chain = new SequentialMiddlewareChain(ctx, Arrays.asList(middleware1, middleware2));
        middleware1.handleContext(ctx, chain);

        assertEquals(ctx.get(KEY_1, String.class), Optional.of(VALUE_1));
        assertEquals(ctx.get(KEY_2, String.class), expectedValue);
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = MiddlewareBuilder.FULL_EXCEPTION_MESSAGE)
    public void testThrowingMiddleware() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        Middleware throwingMiddleware = MiddlewareBuilder.buildThrowingMiddleware();

        MiddlewareChain chain = new SequentialMiddlewareChain(ctx, Arrays.asList(throwingMiddleware));
        chain.next();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testContextNull() throws Exception {
        new SequentialMiddlewareChain(null, new ArrayList<>());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMiddlewareListNull() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();
        new SequentialMiddlewareChain(ctx, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testOneMiddlewareNull() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();
        List<Middleware> middlewares = new ArrayList<>();
        middlewares.add(null);
        new SequentialMiddlewareChain(ctx, middlewares);
    }
}