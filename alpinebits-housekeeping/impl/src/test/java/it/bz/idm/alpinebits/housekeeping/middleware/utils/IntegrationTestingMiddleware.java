/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.housekeeping.middleware.utils;

import it.bz.idm.alpinebits.common.context.ResponseContextKeys;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Optional;

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

        OutputStream os = ctx.getOrThrow(ResponseContextKeys.RESPONSE_CONTENT_STREAM);

        this.tryToAddVersionToResponse(ctx, os);
        this.tryToAddCapabilitiesToResponse(ctx, os);
    }

    private void tryToAddVersionToResponse(Context ctx, OutputStream os) {
        Optional<String> versionOptional = ctx.get(ResponseContextKeys.RESPONSE_VERSION);
        versionOptional.ifPresent(version -> {
            try {
                os.write(version.getBytes(Charset.forName("UTF-8")));
            } catch (IOException e) {
                LOG.error("Could not write response", e);
            }
        });
    }


    private void tryToAddCapabilitiesToResponse(Context ctx, OutputStream os) {
        Optional<Collection> capabilitiesOptional = ctx.get(ResponseContextKeys.RESPONSE_CAPABILITIES);
        capabilitiesOptional.ifPresent(capabilities -> {
            try {
                os.write(String.join(",", capabilities).getBytes(Charset.forName("UTF-8")));
            } catch (IOException e) {
                LOG.error("Could not write response", e);
            }
        });
    }
}
