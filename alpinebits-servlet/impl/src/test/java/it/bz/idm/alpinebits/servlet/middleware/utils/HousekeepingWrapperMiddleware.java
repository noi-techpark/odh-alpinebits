/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.servlet.middleware.utils;

import it.bz.idm.alpinebits.common.context.ResponseContextKeys;
import it.bz.idm.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;
import it.bz.idm.alpinebits.servlet.middleware.AlpineBitsClientProtocolMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.HousekeepingWriterMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.MultipartFormDataParserMiddleware;

import java.util.Arrays;
import java.util.Collection;

/**
 * This {@link Middleware} configures the necessary minimum
 * middlewares to run integration tests for
 * {@link HousekeepingWriterMiddleware}.
 */
public class HousekeepingWrapperMiddleware implements Middleware {

    public static final Collection<String> DEFAULT_CAPABILITIES = Arrays.asList(
            "getVersion",
            "getCapabilities",
            "otherAction"
    );
    public static final String DEFAULT_VERSION = "2017-10";

    private final Middleware middleware;

    public HousekeepingWrapperMiddleware() {
        this.middleware = ComposingMiddlewareBuilder.compose(Arrays.asList(
                new AlpineBitsClientProtocolMiddleware(),
                new MultipartFormDataParserMiddleware(),
                new HousekeepingWriterMiddleware(),
                buildHousekeepingValuesSettingMiddldeware()
        ));
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        this.middleware.handleContext(ctx, chain);
    }

    private Middleware buildHousekeepingValuesSettingMiddldeware() {
        return (ctx, chain) -> {
            ctx.put(ResponseContextKeys.RESPONSE_CAPABILITIES, DEFAULT_CAPABILITIES);
            ctx.put(ResponseContextKeys.RESPONSE_VERSION, DEFAULT_VERSION);
        };
    }
}
