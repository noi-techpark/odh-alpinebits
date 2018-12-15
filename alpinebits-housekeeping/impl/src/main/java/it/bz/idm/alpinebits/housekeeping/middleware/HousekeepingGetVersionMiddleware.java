/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.housekeeping.middleware;

import it.bz.idm.alpinebits.common.context.RequestContextKey;
import it.bz.idm.alpinebits.common.context.ResponseContextKeys;
import it.bz.idm.alpinebits.housekeeping.HousekeepingWriteException;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;
import it.bz.idm.alpinebits.routing.Router;
import it.bz.idm.alpinebits.routing.RouterContextKey;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * This {@link Middleware} handles the AlpineBits
 * <code>getVersion</code> housekeeping request.
 * <p>
 * It returns a collection of versions configured for the AlpioneBits server
 * instance. This collection is taken from a {@link Router} inside the
 * {@link Context}, mapped by the {@link RouterContextKey#ALPINEBITS_ROUTER}
 * key.
 * <p>
 * This middleware doesn't invoke {@link MiddlewareChain#next()}, i.e.
 * no other middlewares will be invoked after this one.
 */
public class HousekeepingGetVersionMiddleware implements Middleware {

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        Router router = ctx.getOrThrow(RouterContextKey.ALPINEBITS_ROUTER);
        String requestedVersion = ctx.getOrThrow(RequestContextKey.REQUEST_VERSION);
        OutputStream os = ctx.getOrThrow(ResponseContextKeys.RESPONSE_CONTENT_STREAM);

        String version = router.getVersion(requestedVersion);

        String responseMessage = "OK:" + version;
        try {
            os.write(responseMessage.getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            throw new HousekeepingWriteException(
                    "Error while writing Housekeeping response. Response message" +
                            "would have been: \"" + responseMessage + "\"", e);
        }
    }

}
