/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.idm.alpinebits.http.impl.util;

import it.idm.alpinebits.middleware.Context;
import it.idm.alpinebits.middleware.Middleware;
import it.idm.alpinebits.middleware.MiddlewareChain;

/**
 * A {@link Middleware} that throws on every invocation, used for testing.
 */
public class ThrowingMiddleware implements Middleware {

    public static final String EXCEPTION_MESSAGE = "exception message";
    public static final String EXPECTED_EXCEPTION_MESSAGE = "ERROR:" + EXCEPTION_MESSAGE;

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        throw new RuntimeException(EXCEPTION_MESSAGE);
    }

}
