/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking.utils;

import it.bz.opendatahub.alpinebits.handshaking.ContextSerializer;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAPingRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAPingRS;

/**
 * This {@link ContextSerializer} implementation is configurable by its constructor
 * parameters and is used for tests only.
 */
public class ConfigurableContextSerializer implements ContextSerializer {

    public static final Key<OTAPingRS> OTA_PING_RS_KEY = Key.key("ping response key", OTAPingRS.class);

    private final OTAPingRQ otaPingRQ;

    public ConfigurableContextSerializer(OTAPingRQ otaPingRQ) {
        this.otaPingRQ = otaPingRQ;
    }

    @Override
    public OTAPingRQ fromContext(Context ctx) {
        return otaPingRQ;
    }

    @Override
    public void toContext(Context ctx, OTAPingRS otaPingRS) {
        ctx.put(OTA_PING_RS_KEY, otaPingRS);
    }
}
