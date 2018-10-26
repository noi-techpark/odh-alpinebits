/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.common.context;

import it.bz.idm.alpinebits.middleware.Key;

import java.util.Collection;

/**
 * This class contains key definitions, that may be used e.g. in a middleware context
 * to define response values.
 */
public final class ResponseContextKeys {

    /**
     * Context key for capabilities response from AlpineBits housekeeping actions.
     */
    public static final Key<Collection> RESPONSE_CAPABILITIES = Key.key(
            "response.capabilities", Collection.class
    );

    /**
     * Context key for version response from AlpineBits housekeeping actions.
     */
    public static final Key<String> RESPONSE_VERSION = Key.key(
            "response.version", String.class
    );

    private ResponseContextKeys() {
        // Empty
    }

}
