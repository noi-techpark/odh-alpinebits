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
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveContentNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveContentNotifRS;

/**
 * A simple {@link Middleware} to handle Inventory push
 * requests, persisting them into a DB.
 */
public class InventoryPushMiddleware implements Middleware {

    public static final Key<OTAHotelDescriptiveContentNotifRQ> OTA_INVENTORY_PUSH_REQUEST
            = Key.key("inventory push request", OTAHotelDescriptiveContentNotifRQ.class);
    public static final Key<OTAHotelDescriptiveContentNotifRS> OTA_INVENTORY_PUSH_RESPONSE
            = Key.key("inventory push response", OTAHotelDescriptiveContentNotifRS.class);

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Call service for persistence
        OTAHotelDescriptiveContentNotifRS response = this.invokeService(ctx);

        // Put result back into middleware context
        ctx.put(OTA_INVENTORY_PUSH_RESPONSE, response);
    }

    private OTAHotelDescriptiveContentNotifRS invokeService(Context ctx) {
        // Get necessary objects from middleware context
        String action = ctx.getOrThrow(RequestContextKey.REQUEST_ACTION);
        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ = ctx.getOrThrow(OTA_INVENTORY_PUSH_REQUEST);

        // Call service for persistence
        InventoryPushService service = new InventoryPushService();
        if (AlpineBitsAction.INVENTORY_BASIC_PUSH.equals(action)) {
            return service.logInventoryBasic(otaHotelDescriptiveContentNotifRQ);
        } else if (AlpineBitsAction.INVENTORY_HOTEL_INFO_PUSH.equals(action)) {
            return service.logInventoryHotelInfo(otaHotelDescriptiveContentNotifRQ);
        }

        throw new AlpineBitsException("No implementation for action found", 500);
    }
}
