/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.inventory.middleware;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.db.PersistenceContextKey;
import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveContentNotifRequest;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;

import javax.persistence.EntityManager;

/**
 * A simple {@link Middleware} to handle Inventory push
 * requests, persisting them into a DB.
 */
public class InventoryPushMiddleware implements Middleware {

    private final Key<HotelDescriptiveContentNotifRequest> requestKey;
    private final Key<GenericResponse> responseKey;

    public InventoryPushMiddleware(
            Key<HotelDescriptiveContentNotifRequest> requestKey,
            Key<GenericResponse> responseKey
    ) {
        this.requestKey = requestKey;
        this.responseKey = responseKey;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Call service for persistence
        GenericResponse response = this.invokeService(ctx);

        // Put result back into middleware context
        ctx.put(this.responseKey, response);
    }

    private GenericResponse invokeService(Context ctx) {
        // Get necessary objects from middleware context
        String action = ctx.getOrThrow(RequestContextKey.REQUEST_ACTION);
        HotelDescriptiveContentNotifRequest hotelDescriptiveContentNotifRequest = ctx.getOrThrow(this.requestKey);
        EntityManager em = ctx.getOrThrow(PersistenceContextKey.ENTITY_MANAGER);

        // Call service for persistence
        InventoryPushService service = new InventoryPushService(em);
        if (AlpineBitsAction.INVENTORY_BASIC_PUSH.equals(action)) {
            return service.writeBasic(hotelDescriptiveContentNotifRequest);
        } else if (AlpineBitsAction.INVENTORY_HOTEL_INFO_PUSH.equals(action)) {
            return service.writeHotelInfo(hotelDescriptiveContentNotifRequest);
        }

        throw new AlpineBitsException("No implementation for action found", 500);
    }
}
