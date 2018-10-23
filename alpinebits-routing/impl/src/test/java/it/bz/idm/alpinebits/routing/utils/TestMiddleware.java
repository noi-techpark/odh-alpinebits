/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing.utils;

import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Key;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;

/**
 * This {@link Middleware} is used for tests only.
 */
public class TestMiddleware implements Middleware {

    public static final Key<String> DEFAULT_KEY = Key.key("KEY", String.class);
    public static final String DEFAULT_VALUE = "VALUE";

    private final Key<String> key;
    private final String value;

    public TestMiddleware() {
        this.key = DEFAULT_KEY;
        this.value = DEFAULT_VALUE;
    }

    public TestMiddleware(Key<String> key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        ctx.put(this.key, this.value);
    }

}
