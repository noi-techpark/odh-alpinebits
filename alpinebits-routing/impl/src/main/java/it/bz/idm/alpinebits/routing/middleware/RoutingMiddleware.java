/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing.middleware;

import it.bz.idm.alpinebits.http.HttpContextKey;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;
import it.bz.idm.alpinebits.routing.Router;
import it.bz.idm.alpinebits.routing.RouterContextKey;
import it.bz.idm.alpinebits.routing.UndefinedRouteException;

import java.util.Optional;

/**
 * This {@link Middleware} provides routing capabilities for AlpineBits requests.
 * <p>
 * It tries to match the version and action of the current AlpineBits request,
 * taken from the {@link Context}, to a list of configurable middlewares.
 * <p>
 * If a match was found, the corresponding middleware is invoked. A
 * {@link UndefinedRouteException} is thrown otherwise.
 */
public class RoutingMiddleware implements Middleware {

    private final Router router;

    public RoutingMiddleware(Router router) {
        if (router == null) {
            throw new IllegalArgumentException("The router must not be null");
        }
        this.router = router;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        String version = ctx.getOrThrow(HttpContextKey.ALPINE_BITS_CLIENT_PROTOCOL_VERSION);
        String action = ctx.getOrThrow(HttpContextKey.ALPINE_BITS_ACTION);

        // Try to find a middleware for the given AlpineBits version and action
        Middleware middleware = this.findMiddleware(version, action);

        // Add router to context, such that other middlewares may use its information
        ctx.put(RouterContextKey.ALPINEBITS_ROUTER, this.router);

        // Invoke middleware
        middleware.handleContext(ctx, () -> {
        });
    }

    private Middleware findMiddleware(String version, String action) {
        Optional<Middleware> optional = this.router.findMiddleware(version, action);

        if (!optional.isPresent()) {
            throw new UndefinedRouteException("No route found for version " + version + " and action " + action);
        }

        return optional.get();
    }

}
