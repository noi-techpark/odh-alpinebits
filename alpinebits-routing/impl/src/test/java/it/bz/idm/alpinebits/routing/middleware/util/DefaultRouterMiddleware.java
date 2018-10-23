/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing.middleware.util;

import it.bz.idm.alpinebits.http.middleware.AlpineBitsClientProtocolMiddleware;
import it.bz.idm.alpinebits.http.middleware.MultipartFormDataParserMiddleware;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Key;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;
import it.bz.idm.alpinebits.middleware.impl.ComposingMiddlewareBuilder;
import it.bz.idm.alpinebits.routing.DefaultRouter;
import it.bz.idm.alpinebits.routing.Router;
import it.bz.idm.alpinebits.routing.middleware.RoutingMiddleware;

import java.util.Arrays;

/**
 * A router middleware configuring {@link Middleware} used for testing.
 */
public class DefaultRouterMiddleware implements Middleware {

    public static final String DEFAULT_VERSION = "2017-10";
    public static final String DEFAULT_ACTION = "some action";

    public static final String DEFAULT_KEY_IDENTIFIER = "default key identifier";
    public static final String DEFAULT_KEY_VALUE = "default key value";
    public static final Key<String> DEFAULT_KEY = Key.key(DEFAULT_KEY_IDENTIFIER, String.class);

    private final Middleware middleware;

    public DefaultRouterMiddleware() {
        this((ctx, chain) -> {
            ctx.put(DEFAULT_KEY, DEFAULT_KEY_VALUE);
        });
    }

    public DefaultRouterMiddleware(Middleware middleware) {
        Router router = new DefaultRouter.Builder()
                .forVersion(DEFAULT_VERSION)
                .addMiddleware(DEFAULT_ACTION, middleware)
                .done()
                .build();

        this.middleware = ComposingMiddlewareBuilder.compose(Arrays.asList(
                new AlpineBitsClientProtocolMiddleware(),
                new MultipartFormDataParserMiddleware(),
                new RoutingMiddleware(router)
        ));
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        this.middleware.handleContext(ctx, chain);
    }
}
