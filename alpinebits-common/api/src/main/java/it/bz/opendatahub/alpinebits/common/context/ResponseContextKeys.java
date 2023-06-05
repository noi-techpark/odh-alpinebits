// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.context;

import it.bz.opendatahub.alpinebits.middleware.Key;

import java.io.OutputStream;

/**
 * This class contains key definitions, that may be used e.g. in a middleware context
 * to define response values.
 */
public final class ResponseContextKeys {

    /**
     * Context key for AlpineBits request content in its plain form, i.e. the XML.
     */
    public static final Key<OutputStream> RESPONSE_CONTENT_STREAM = Key.key(
            "response.content.stream", OutputStream.class
    );

    /**
     * Context key for response content type hint.
     * <p>
     * The hint can be used e.g. by a HTTP middleware to set the correct content-type
     * header value.
     */
    public static final Key<String> RESPONSE_CONTENT_TYPE_HINT = Key.key(
            "response.content.type.hint", String.class
    );

    private ResponseContextKeys() {
        // Empty
    }

}
