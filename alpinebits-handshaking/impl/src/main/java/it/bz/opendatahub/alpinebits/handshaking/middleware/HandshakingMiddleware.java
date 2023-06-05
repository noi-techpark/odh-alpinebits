// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking.middleware;

import it.bz.opendatahub.alpinebits.handshaking.ContextSerializer;
import it.bz.opendatahub.alpinebits.handshaking.HandshakingDataExtractor;
import it.bz.opendatahub.alpinebits.handshaking.dto.HandshakingData;
import it.bz.opendatahub.alpinebits.handshaking.IntersectionComputer;
import it.bz.opendatahub.alpinebits.handshaking.OTAPingRSBuilder;
import it.bz.opendatahub.alpinebits.handshaking.JsonSerializer;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.routing.Router;
import it.bz.opendatahub.alpinebits.routing.RouterContextKey;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAPingRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAPingRS;

/**
 * This {@link Middleware} handles the AlpineBits handshake action, based
 * on the router configuration found in the {@link Context}.
 * <p>
 * It merges the data provided by the client with the current router configuration
 * and returns that data.
 * <p>
 * This middleware doesn't invoke {@link MiddlewareChain#next()}, i.e.
 * no other middlewares will be invoked after this one.
 */
public final class HandshakingMiddleware implements Middleware {

    private final ContextSerializer contextSerializer;
    private final JsonSerializer jsonSerializer = new JsonSerializer();

    public HandshakingMiddleware(ContextSerializer contextSerializer) {
        if (contextSerializer == null) {
            throw new IllegalArgumentException("The ContextSerializer must not be null");
        }
        this.contextSerializer = contextSerializer;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Convert router information to HandshakingData
        Router router = ctx.getOrThrow(RouterContextKey.ALPINEBITS_ROUTER);
        HandshakingData routerHandshakingData = HandshakingDataExtractor.fromRouter(router);

        // Get HandshakingData from request
        OTAPingRQ otaPingRQ = this.contextSerializer.fromContext(ctx);
        String requestHandshakingJson = otaPingRQ.getEchoData();
        HandshakingData requestHandshakingData = jsonSerializer.fromJson(requestHandshakingJson);

        // Merge the HandshakingData
        HandshakingData mergedHandshakingData = IntersectionComputer.intersectHandshakingData(
                routerHandshakingData,
                requestHandshakingData
        );

        // Convert merged Handshaking data to a JSON string
        String mergedHandshakingJson = jsonSerializer.toJson(mergedHandshakingData);

        // Generate merge result
        OTAPingRS otaPingRS = OTAPingRSBuilder.build(requestHandshakingJson, mergedHandshakingJson);

        this.contextSerializer.toContext(ctx, otaPingRS);
    }

}
