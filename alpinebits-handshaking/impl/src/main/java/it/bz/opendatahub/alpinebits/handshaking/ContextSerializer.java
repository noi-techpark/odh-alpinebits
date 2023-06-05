// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking;

import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAPingRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAPingRS;

/**
 * Serialize {@link OTAPingRQ} and {@link OTAPingRS} objects
 * from and to the context.
 */
public interface ContextSerializer {
    /**
     * Use data from the context to build a {@link OTAPingRQ} instance.
     *
     * @param ctx This context provides the data to such that a
     *            {@link OTAPingRQ} instance can be inferred and returned.
     * @return The {@link OTAPingRQ} instance.
     */
    OTAPingRQ fromContext(Context ctx);

    /**
     * Serialize a {@link OTAPingRS} instance into the context.
     *
     * @param ctx       The {@link OTAPingRS} instance is put into this context.
     * @param otaPingRS The {@link OTAPingRS} instance.
     */
    void toContext(Context ctx, OTAPingRS otaPingRS);
}
