/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking.utils;

import it.bz.opendatahub.alpinebits.handshaking.ContextSerializer;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAPingRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAPingRS;

/**
 * This {@link ContextSerializer} implementation throws on each method invocation.
 */
public class ThrowingContextSerializer implements ContextSerializer {

    private final RuntimeException exception;

    public ThrowingContextSerializer(RuntimeException exception) {
        this.exception = exception;
    }

    @Override
    public OTAPingRQ fromContext(Context ctx) {
        throw exception;
    }

    @Override
    public void toContext(Context ctx, OTAPingRS otaPingRS) {
        throw exception;
    }
}
