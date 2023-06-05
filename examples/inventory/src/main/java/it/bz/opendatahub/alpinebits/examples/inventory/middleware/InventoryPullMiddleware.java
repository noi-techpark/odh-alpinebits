// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.inventory.middleware;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;

/**
 * A simple {@link Middleware} to handle Inventory pull
 * requests, returning data from DB.
 */
public class InventoryPullMiddleware implements Middleware {

    public static final Key<OTAHotelDescriptiveInfoRQ> OTA_INVENTORY_PULL_REQUEST
            = Key.key("inventory pull request", OTAHotelDescriptiveInfoRQ.class);
    public static final Key<OTAHotelDescriptiveInfoRS> OTA_INVENTORY_PULL_RESPONSE
            = Key.key("inventory pull response", OTAHotelDescriptiveInfoRS.class);

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Call service for persistence
        OTAHotelDescriptiveInfoRS response = this.invokeService(ctx);

        // Put result back into middleware context
        ctx.put(OTA_INVENTORY_PULL_RESPONSE, response);
    }

    private OTAHotelDescriptiveInfoRS invokeService(Context ctx) {
        // Get necessary objects from middleware context
        String action = ctx.getOrThrow(RequestContextKey.REQUEST_ACTION);
        OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ = ctx.getOrThrow(OTA_INVENTORY_PULL_REQUEST);

        // Call service for persistence
        InventoryPullService service = new InventoryPullService();
        if (AlpineBitsAction.INVENTORY_BASIC_PULL.equals(action)) {
            return service.readInventoryBasic(otaHotelDescriptiveInfoRQ);
        } else if (AlpineBitsAction.INVENTORY_HOTEL_INFO_PULL.equals(action)) {
            return service.readInventoryHotelInfo(otaHotelDescriptiveInfoRQ);
        }

        throw new AlpineBitsException("No implementation for action found", 500);
    }
}
