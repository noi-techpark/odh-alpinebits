/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.housekeeping.middleware.utils;

import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test {@link Middleware} that delegates its work to a pre-configured
 * routing middleware for integration tests.
 */
public class IntegrationTestingMiddleware implements Middleware {

    private static final Logger LOG = LoggerFactory.getLogger(IntegrationTestingMiddleware.class);

    private final Middleware routingMiddleware;

    public IntegrationTestingMiddleware() {
        this.routingMiddleware = RouterMiddlewareBuilder.buildRoutingMiddlewareForIntegrationTest();
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        this.routingMiddleware.handleContext(ctx, chain);
    }

}
