/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.middleware;

import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.impl.SimpleContext;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link RequestMappingMiddleware} class.
 */
public class RequestMappingMiddlewareTest {

    private static final Key<Integer> SOURCE_KEY = Key.key("source", Integer.class);
    private static final Key<String> TARGET_KEY = Key.key("target", String.class);

    @Test
    public void testHandleContext() {
        int source = 23;

        Context ctx = new SimpleContext();
        ctx.put(SOURCE_KEY, source);

        Middleware middleware = new RequestMappingMiddleware<>(SOURCE_KEY, TARGET_KEY, (integer, context) -> integer.toString());

        middleware.handleContext(ctx, () -> {
            String target = ctx.getOrThrow(TARGET_KEY);

            // Assert mapping is performed before next
            // downstream middleware is invoked
            assertEquals(target, Integer.toString(source));
        });
    }
}