/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.freerooms.middleware;

import it.bz.opendatahub.alpinebits.db.PersistenceContextKey;
import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebits.mapping.entity.freerooms.FreeRoomsRequest;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;

import javax.persistence.EntityManager;

/**
 * A simple {@link Middleware} to handle FreeRooms requests,
 * persisting the data from DB.
 */
public class FreeRoomsMiddleware implements Middleware {

    private final Key<FreeRoomsRequest> requestKey;
    private final Key<GenericResponse> responseKey;

    public FreeRoomsMiddleware(
            Key<FreeRoomsRequest> requestKey,
            Key<GenericResponse> responseKey
    ) {
        this.requestKey = requestKey;
        this.responseKey = responseKey;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Get necessary objects from middleware context
        FreeRoomsRequest freeRoomsRequest = ctx.getOrThrow(this.requestKey);
        EntityManager em = ctx.getOrThrow(PersistenceContextKey.ENTITY_MANAGER);

        FreeRoomsService freeRoomsService = new FreeRoomsService(em);
        GenericResponse freeRoomsResponse = freeRoomsService.persistFreeRooms(freeRoomsRequest);

        // Put result back into middleware context
        ctx.put(this.responseKey, freeRoomsResponse);
    }

}
