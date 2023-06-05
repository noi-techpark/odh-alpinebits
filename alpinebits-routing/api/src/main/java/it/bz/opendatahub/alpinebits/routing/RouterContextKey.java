// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.routing;

import it.bz.opendatahub.alpinebits.middleware.Key;

/**
 * This class contains key definitions, that may be used e.g. in a middleware context.
 */
public final class RouterContextKey {

    /**
     * Context key for AlpineBits {@link Router}.
     */
    public static final Key<Router> ALPINEBITS_ROUTER = Key.key(
            "alpinebits.router", Router.class
    );

    private RouterContextKey() {
        // Empty
    }

}
