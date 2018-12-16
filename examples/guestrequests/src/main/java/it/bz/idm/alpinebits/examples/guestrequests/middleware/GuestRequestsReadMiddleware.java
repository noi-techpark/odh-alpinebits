/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.guestrequests.middleware;

import it.bz.idm.alpinebits.common.context.RequestContextKey;
import it.bz.idm.alpinebits.db.PersistenceContextKey;
import it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers.GuestRequestsReadResponse;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Key;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;

import javax.persistence.EntityManager;

/**
 * A simple {@link Middleware} to handle GuestRequest read requests.
 */
public class GuestRequestsReadMiddleware implements Middleware {

    private final Key<GuestRequestsReadResponse> readResponseKey;

    public GuestRequestsReadMiddleware(Key<GuestRequestsReadResponse> readResponseKey) {
        this.readResponseKey = readResponseKey;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Get necessary objects from middleware context
        EntityManager em = ctx.getOrThrow(PersistenceContextKey.ENTITY_MANAGER);
        String username = ctx.getOrThrow(RequestContextKey.REQUEST_USERNAME);

        // Call service for persistence
        GuestRequestsReadService service = new GuestRequestsReadService(em);
        GuestRequestsReadResponse response = service.readGuestRequests(username);

        // Put result back into middleware context
        ctx.put(this.readResponseKey, response);
    }
}
