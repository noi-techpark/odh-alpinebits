/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.dbserver.middleware.guestrequests;

import it.bz.idm.alpinebits.common.context.RequestContextKey;
import it.bz.idm.alpinebits.db.PersistenceContextKey;
import it.bz.idm.alpinebits.mapping.entity.guestrequests.notifreportrq.GuestRequestsConfirmationRequest;
import it.bz.idm.alpinebits.mapping.entity.guestrequests.notifreportrs.GuestRequestsConfirmationResponse;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Key;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;

import javax.persistence.EntityManager;

/**
 * A simple {@link Middleware} to handle GuestRequest confirmations
 * (acknowledgements and refusals), persisting them into a DB.
 */
public class GuestRequestsConfirmationMiddleware implements Middleware {

    private final Key<GuestRequestsConfirmationRequest> requestKey;
    private final Key<GuestRequestsConfirmationResponse> responseKey;

    public GuestRequestsConfirmationMiddleware(
            Key<GuestRequestsConfirmationRequest> requestKey,
            Key<GuestRequestsConfirmationResponse> responseKey
    ) {
        this.requestKey = requestKey;
        this.responseKey = responseKey;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Get necessary objects from middleware context
        GuestRequestsConfirmationRequest guestRequestsConfirmationRequest = ctx.getOrThrow(this.requestKey);
        EntityManager em = ctx.getOrThrow(PersistenceContextKey.ENTITY_MANAGER);
        String username = ctx.getOrThrow(RequestContextKey.REQUEST_USERNAME);

        // Call service for persistence
        GuestRequestsConfirmationService service = new GuestRequestsConfirmationService(em);
        GuestRequestsConfirmationResponse response = service.persistGuestRequestConfirmations(username, guestRequestsConfirmationRequest);

        // Put result back into middleware context
        ctx.put(this.responseKey, response);
    }
}
