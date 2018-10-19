/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.idm.alpinebits.middleware;

/**
 * The middleware implements a certain aspect or functionality.
 */
public interface Middleware {

    /**
     * Handle the invocation, using the given {@link Context} if appropriate. Call
     * {@link MiddlewareChain#next()} to invoke the next middleware in the chain.
     *
     * @param ctx   The {@link Context} is shared between all middlewares of the chain
     * @param chain the chain is used to call the next middleware when appropriate
     */
    void handleContext(Context ctx, MiddlewareChain chain);

}
