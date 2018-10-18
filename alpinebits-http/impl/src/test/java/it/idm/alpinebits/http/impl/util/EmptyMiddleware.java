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
 * An empty {@link Middleware} used for testing.
 */
public class EmptyMiddleware implements Middleware {

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Do nothing, since this Middleware is just used for testing
    }

}
