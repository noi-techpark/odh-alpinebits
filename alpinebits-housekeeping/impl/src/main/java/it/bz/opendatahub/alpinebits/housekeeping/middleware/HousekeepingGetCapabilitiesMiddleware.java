/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.housekeeping.middleware;

import it.bz.opendatahub.alpinebits.common.constants.HttpContentTypeHeaderValues;
import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.common.context.ResponseContextKeys;
import it.bz.opendatahub.alpinebits.housekeeping.HousekeepingWriteException;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.routing.Router;
import it.bz.opendatahub.alpinebits.routing.RouterContextKey;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;

/**
 * This {@link Middleware} handles the AlpineBits
 * <code>getCapabilities</code> housekeeping request.
 * <p>
 * It returns a collection of capabilities configured for the version defined
 * by the request. This collection is taken from a {@link Router} inside the
 * {@link Context}, mapped by the {@link RouterContextKey#ALPINEBITS_ROUTER}
 * key. If the collection of actions is empty, an empty collection is returned.
 * <p>
 * This middleware doesn't invoke {@link MiddlewareChain#next()}, i.e.
 * no other middlewares will be invoked after this one.
 */
public class HousekeepingGetCapabilitiesMiddleware implements Middleware {

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        Router router = ctx.getOrThrow(RouterContextKey.ALPINEBITS_ROUTER);
        String version = ctx.getOrThrow(RequestContextKey.REQUEST_VERSION);
        OutputStream os = ctx.getOrThrow(ResponseContextKeys.RESPONSE_CONTENT_STREAM);

        Collection<String> capabilities = router.getCapabilitiesForVersion(version).orElse(Collections.emptySet());

        String responseMessage = "OK:" + String.join(",", capabilities);
        try {
            os.write(responseMessage.getBytes(StandardCharsets.UTF_8));
            ctx.put(ResponseContextKeys.RESPONSE_CONTENT_TYPE_HINT, HttpContentTypeHeaderValues.TEXT_PLAIN);
        } catch (IOException e) {
            throw new HousekeepingWriteException(
                    "Error while writing Housekeeping response. Response message" +
                            "would have been: \"" + responseMessage + "\"", e);
        }
    }

}
