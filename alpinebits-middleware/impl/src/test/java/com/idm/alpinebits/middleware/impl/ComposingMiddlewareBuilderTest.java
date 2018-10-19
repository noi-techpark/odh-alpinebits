/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.idm.alpinebits.middleware.impl;

import com.idm.alpinebits.middleware.Context;
import com.idm.alpinebits.middleware.Middleware;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link ComposingMiddlewareBuilder} class.
 */
public class ComposingMiddlewareBuilderTest {

    private static final String KEY_1 = "key1";
    private static final String KEY_2 = "key2";
    private static final String KEY_3 = "key3";
    private static final String KEY_4 = "key4";
    private static final String VALUE_1 = "value1";
    private static final String VALUE_2 = "value2";
    private static final String VALUE_3 = "value3";
    private static final String VALUE_4 = "value4";

    @DataProvider(name = "testComposeDataProvider")
    public static Object[][] testComposeDataProvider() {
        return new Object[][]{
                {false, false, Optional.empty()},
                {false, true, Optional.empty()},
                {true, false, Optional.of(VALUE_2)},
                {true, true, Optional.of(VALUE_2)},
        };
    }

    @DataProvider(name = "testNestedComposeDataProvider")
    public static Object[][] testNestedComposeDataProvider() {
        return new Object[][]{
                {false, false, false, Optional.empty()},
                {false, false, true, Optional.empty()},
                {false, true, false, Optional.empty()},
                {false, true, true, Optional.empty()},
                {true, false, false, Optional.empty()},
                {true, false, true, Optional.empty()},
                {true, true, false, Optional.of(VALUE_3)},
                {true, true, true, Optional.of(VALUE_3)},
        };
    }


    @DataProvider(name = "testNestedComposeDataProvider_WithTwoComposedMiddlewares")
    public static Object[][] testNestedComposeDataProvider_WithTwoComposedMiddlewares() {
        return new Object[][]{
                {false, false, false, false, Optional.empty()},
                {false, false, false, true, Optional.empty()},
                {false, false, true, false, Optional.empty()},
                {false, false, true, true, Optional.empty()},
                {false, true, false, false, Optional.empty()},
                {false, true, false, true, Optional.empty()},
                {false, true, true, false, Optional.empty()},
                {false, true, true, true, Optional.empty()},
                {true, false, false, false, Optional.empty()},
                {true, false, false, true, Optional.empty()},
                {true, false, true, false, Optional.empty()},
                {true, false, true, true, Optional.empty()},
                {true, true, false, false, Optional.empty()},
                {true, true, false, true, Optional.empty()},
                {true, true, true, false, Optional.of(VALUE_4)},
                {true, true, true, true, Optional.of(VALUE_4)},
        };
    }

    @Test(dataProvider = "testComposeDataProvider")
    public void testCompose(
            boolean middleware1CallsNext,
            boolean middleware2CallsNext,
            Optional<String> expectedValue
    ) throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        Middleware middleware1 = MiddlewareBuilder.buildMiddleware(KEY_1, VALUE_1, middleware1CallsNext);
        Middleware middleware2 = MiddlewareBuilder.buildMiddleware(KEY_2, VALUE_2, middleware2CallsNext);

        Middleware composedMiddleware = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware1, middleware2));
        composedMiddleware.handleContext(ctx, null);

        assertEquals(ctx.get(KEY_1, String.class), Optional.of(VALUE_1));
        assertEquals(ctx.get(KEY_2, String.class), expectedValue);

        // Assert the value of the toString() method for 100% test coverage
        assertEquals(ComposingMiddlewareBuilder.COMPOSING_MIDDLEWARE_NAME, composedMiddleware.toString());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testComposeMiddlewareListNull() throws Exception {
        Middleware composedMiddleware = ComposingMiddlewareBuilder.compose(null);
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testComposeOneMiddlewareNull() throws Exception {
        List<Middleware> middlewares = new ArrayList<>();
        middlewares.add(null);
        Middleware composedMiddleware = ComposingMiddlewareBuilder.compose(middlewares);
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = MiddlewareBuilder.EXCEPTION_MESSAGE)
    public void testComposeWithFirstMiddlewareThrowing() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        Middleware middleware1 = MiddlewareBuilder.buildThrowingMiddleware();
        Middleware composedMiddleware = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware1));
        composedMiddleware.handleContext(ctx, null);
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = MiddlewareBuilder.EXCEPTION_MESSAGE)
    public void testComposeWithSecondMiddlewareThrowing() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        Middleware middleware1 = MiddlewareBuilder.buildMiddleware(KEY_1, VALUE_1, true);
        Middleware middleware2 = MiddlewareBuilder.buildThrowingMiddleware();
        Middleware composedMiddleware = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware1, middleware2));
        composedMiddleware.handleContext(ctx, null);
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = MiddlewareBuilder.EXCEPTION_MESSAGE)
    public void testComposeWithFirstComposingMiddlewareThrowing() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        Middleware middleware1 = MiddlewareBuilder.buildMiddleware(KEY_1, VALUE_1, true);
        Middleware middleware2 = MiddlewareBuilder.buildThrowingMiddleware();
        Middleware middleware3 = MiddlewareBuilder.buildMiddleware(KEY_2, VALUE_2, true);

        Middleware tmpComposedMiddleware = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware1, middleware2));
        Middleware finalComposedMiddleware = ComposingMiddlewareBuilder.compose(Arrays.asList(tmpComposedMiddleware, middleware3));

        finalComposedMiddleware.handleContext(ctx, null);
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = MiddlewareBuilder.EXCEPTION_MESSAGE)
    public void testComposeWithSecondComposingMiddlewareThrowing() throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        Middleware middleware1 = MiddlewareBuilder.buildMiddleware(KEY_1, VALUE_1, true);
        Middleware middleware2 = MiddlewareBuilder.buildThrowingMiddleware();
        Middleware middleware3 = MiddlewareBuilder.buildMiddleware(KEY_2, VALUE_2, true);

        Middleware tmpComposedMiddleware = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware2, middleware3));
        Middleware finalComposedMiddleware = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware1, tmpComposedMiddleware));

        finalComposedMiddleware.handleContext(ctx, null);
    }

    @Test(dataProvider = "testNestedComposeDataProvider")
    public void testNestedCompose_WhereComposedMiddlewareIsFirst(
            boolean middleware1CallsNext,
            boolean middleware2CallsNext,
            boolean middleware3CallsNext,
            Optional<String> expectedValue
    ) throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        Middleware middleware1 = MiddlewareBuilder.buildMiddleware(KEY_1, VALUE_1, middleware1CallsNext);
        Middleware middleware2 = MiddlewareBuilder.buildMiddleware(KEY_2, VALUE_2, middleware2CallsNext);
        Middleware middleware3 = MiddlewareBuilder.buildMiddleware(KEY_3, VALUE_3, middleware3CallsNext);

        // Compose middlewares 1 and 2
        Middleware tmpComposedMiddleware = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware1, middleware2));

        // Compose tmpComposedMiddleware and middleware 3
        Middleware finalComposedMiddlewar = ComposingMiddlewareBuilder.compose(Arrays.asList(tmpComposedMiddleware, middleware3));

        finalComposedMiddlewar.handleContext(ctx, null);

        assertEquals(ctx.get(KEY_1, String.class), Optional.of(VALUE_1));
        assertEquals(ctx.get(KEY_3, String.class), expectedValue);
    }

    @Test(dataProvider = "testNestedComposeDataProvider")
    public void testNestedCompose_WhereComposedMiddlewareIsLast(
            boolean middleware1CallsNext,
            boolean middleware2CallsNext,
            boolean middleware3CallsNext,
            Optional<String> expectedValue
    ) throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        Middleware middleware1 = MiddlewareBuilder.buildMiddleware(KEY_1, VALUE_1, middleware1CallsNext);
        Middleware middleware2 = MiddlewareBuilder.buildMiddleware(KEY_2, VALUE_2, middleware2CallsNext);
        Middleware middleware3 = MiddlewareBuilder.buildMiddleware(KEY_3, VALUE_3, middleware3CallsNext);

        // Compose middlewares 2 and 3
        Middleware tmpComposedMiddleware = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware2, middleware3));

        // Compose middleware1 and tmpComposedMiddleware and middleware 3
        Middleware finalComposedMiddlewar = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware1, tmpComposedMiddleware));

        finalComposedMiddlewar.handleContext(ctx, null);

        assertEquals(ctx.get(KEY_1, String.class), Optional.of(VALUE_1));
        assertEquals(ctx.get(KEY_3, String.class), expectedValue);
    }

    @Test(dataProvider = "testNestedComposeDataProvider_WithTwoComposedMiddlewares")
    public void testNestedCompose_WithTwoComposedMiddlewares(
            boolean middleware1CallsNext,
            boolean middleware2CallsNext,
            boolean middleware3CallsNext,
            boolean middleware4CallsNext,
            Optional<String> expectedValue
    ) throws Exception {
        Context ctx = ContextBuilder.buildSimpleContext();

        Middleware middleware1 = MiddlewareBuilder.buildMiddleware(KEY_1, VALUE_1, middleware1CallsNext);
        Middleware middleware2 = MiddlewareBuilder.buildMiddleware(KEY_2, VALUE_2, middleware2CallsNext);
        Middleware middleware3 = MiddlewareBuilder.buildMiddleware(KEY_3, VALUE_3, middleware3CallsNext);
        Middleware middleware4 = MiddlewareBuilder.buildMiddleware(KEY_4, VALUE_4, middleware4CallsNext);

        // Compose middlewares 1 and 2
        Middleware tmpComposedMiddleware1 = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware1, middleware2));

        // Compose middlewares 3 and 4
        Middleware tmpComposedMiddleware2 = ComposingMiddlewareBuilder.compose(Arrays.asList(middleware3, middleware4));

        // Compose middleware1 and tmpComposedMiddleware and middleware 3
        Middleware finalComposedMiddlewar = ComposingMiddlewareBuilder.compose(Arrays.asList(tmpComposedMiddleware1, tmpComposedMiddleware2));

        finalComposedMiddlewar.handleContext(ctx, null);

        assertEquals(ctx.get(KEY_1, String.class), Optional.of(VALUE_1));
        assertEquals(ctx.get(KEY_4, String.class), expectedValue);
    }
}