/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.servlet.middleware.utils;

import it.bz.opendatahub.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.servlet.ServletContextKey;
import it.bz.opendatahub.alpinebits.servlet.middleware.ContentTypeHintMiddleware;

/**
 * Test middleware to test {@link ContentTypeHintMiddleware} behaviour when the
 * Content-Type is already defined in the response.
 */
public class ContentTypeResponseDefinedMiddleware implements Middleware {

    public static final String CONTENT_TYPE_HEADER_VALUE = "application/x-aplinebits-test-content-defined";

    private final Middleware middleware;

    // Suppress CheckStyle warnings about wrong lambda indentation
    @SuppressWarnings("checkstyle:indentation")
    public ContentTypeResponseDefinedMiddleware() {
        this.middleware = ComposingMiddlewareBuilder.compose(
                new ContentTypeHintMiddleware(),
                (ctx, chain) -> ctx
                        .getOrThrow(ServletContextKey.SERVLET_RESPONSE)
                        .setHeader(ContentTypeHintMiddleware.RESPONSE_CONTENT_TYPE_HEADER, CONTENT_TYPE_HEADER_VALUE)
        );
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        this.middleware.handleContext(ctx, chain);
    }

}

