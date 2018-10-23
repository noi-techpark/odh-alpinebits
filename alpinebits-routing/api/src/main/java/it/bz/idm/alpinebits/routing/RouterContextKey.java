/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing;

import it.bz.idm.alpinebits.middleware.Key;

/**
 * This class contains key definitions, that may be used e.g. in a middleware context.
 */
public final class RouterContextKey {

    /**
     * Context key for HTTP request.
     */
    public static final Key<Router> ALPINEBITS_ROUTER = Key.key(
            "alpinebits.router", Router.class
    );

    private RouterContextKey() {
        // Empty
    }

}
