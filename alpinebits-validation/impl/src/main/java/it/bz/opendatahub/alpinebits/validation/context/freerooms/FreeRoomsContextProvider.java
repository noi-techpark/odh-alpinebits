/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.context.freerooms;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsCapability;
import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.routing.Router;
import it.bz.opendatahub.alpinebits.routing.RouterContextKey;
import it.bz.opendatahub.alpinebits.validation.context.ValidationContextProvider;

/**
 * Implementation of {@link ValidationContextProvider} that
 * produces a {@link FreeRoomsContext}.
 */
public class FreeRoomsContextProvider implements ValidationContextProvider<FreeRoomsContext> {

    @Override
    public FreeRoomsContext buildContext(Context ctx) {
        if (ctx == null) {
            throw new IllegalArgumentException("Context is required");
        }

        String version = ctx.getOrThrow(RequestContextKey.REQUEST_VERSION);
        Router router = ctx.getOrThrow(RouterContextKey.ALPINEBITS_ROUTER);

        FreeRoomsContext.Builder builder = new FreeRoomsContext.Builder();
        if (router.isCapabilityDefined(version, AlpineBitsCapability.FREE_ROOMS_HOTEL_AVAIL_NOTIF_ACCEPT_DELTAS)) {
            builder.withDeltaSupport();
        }
        if (router.isCapabilityDefined(version, AlpineBitsCapability.FREE_ROOMS_HOTEL_AVAIL_NOTIF_ACCEPT_BOOKING_THRESHOLD)) {
            builder.withFreeButNotBookableSupport();
        }
        if (router.isCapabilityDefined(version, AlpineBitsCapability.FREE_ROOMS_HOTEL_AVAIL_NOTIF_ACCEPT_CATEGORIES)) {
            builder.withRoomCategoriesSupport();
        }
        if (router.isCapabilityDefined(version, AlpineBitsCapability.FREE_ROOMS_HOTEL_AVAIL_NOTIF_ACCEPT_ROOMS)) {
            builder.withDistinctRoomsSupport();
        }
        return builder.build();
    }

}
