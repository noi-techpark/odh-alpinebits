/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking.utils;

import it.bz.opendatahub.alpinebits.handshaking.ContextSerializer;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAPingRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAPingRS;

/**
 * This {@link ContextSerializer} implementation does nothing and
 * is used for tests only.
 */
public class EmptyContextSerializer implements ContextSerializer {

    @Override
    public OTAPingRQ fromContext(Context ctx) {
        return null;
    }

    @Override
    public void toContext(Context ctx, OTAPingRS otaPingRS) {
        // Empty
    }
}
