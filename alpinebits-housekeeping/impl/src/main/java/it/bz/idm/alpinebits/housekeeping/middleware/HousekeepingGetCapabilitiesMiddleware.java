/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.housekeeping.middleware;

import it.bz.idm.alpinebits.common.context.RequestContextKey;
import it.bz.idm.alpinebits.common.context.ResponseContextKeys;
import it.bz.idm.alpinebits.housekeeping.VersionMismatchException;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;
import it.bz.idm.alpinebits.routing.Router;
import it.bz.idm.alpinebits.routing.RouterContextKey;

import java.util.Collection;
import java.util.Optional;

/**
 * This {@link Middleware} handles the AlpineBits
 * <code>getCapabilities</code> housekeeping request.
 * <p>
 * It returns a collection of actions configured for the version defined
 * by the request. This collection is taken from a {@link Router} inside the
 * {@link Context}, mapped by the {@link RouterContextKey#ALPINEBITS_ROUTER}
 * key. If the collection of actions is empty, an empty collection is returned.
 * <p>
 * If no actions were configured for the current version, a
 * {@link VersionMismatchException} is thrown. The reason is, that the
 * router is supposed to return at least the <code>getCapabilities</code>,
 * since it routed to this middleware. If this is not the case, something
 * is wrong, e.g. in the router implementation.
 * <p>
 * This middleware doesn't invoke {@link MiddlewareChain#next()}, i.e.
 * no other middlewares will be invoked after this one.
 */
public class HousekeepingGetCapabilitiesMiddleware implements Middleware {

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        Router router = ctx.getOrThrow(RouterContextKey.ALPINEBITS_ROUTER);
        String version = ctx.getOrThrow(RequestContextKey.REQUEST_VERSION);

        Optional<Collection<String>> capabilities = router.getActionsForVersion(version);

        if (capabilities.isPresent()) {
            ctx.put(ResponseContextKeys.RESPONSE_CAPABILITIES, capabilities.get());
        } else {
            throw new VersionMismatchException(
                    "Expected action configurations for AlpineBits version " + version + ", but none were found"
            );
        }
    }

}
