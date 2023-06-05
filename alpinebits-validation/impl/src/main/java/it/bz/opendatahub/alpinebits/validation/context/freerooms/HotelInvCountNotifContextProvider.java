// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

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
 * produces a {@link HotelInvCountNotifContext}.
 */
public class HotelInvCountNotifContextProvider implements ValidationContextProvider<HotelInvCountNotifContext> {

    @Override
    public HotelInvCountNotifContext buildContext(Context ctx) {
        if (ctx == null) {
            throw new IllegalArgumentException("Context is required");
        }

        String version = ctx.getOrThrow(RequestContextKey.REQUEST_VERSION);
        Router router = ctx.getOrThrow(RouterContextKey.ALPINEBITS_ROUTER);

        HotelInvCountNotifContext.Builder builder = new HotelInvCountNotifContext.Builder();
        if (router.isCapabilityDefined(version, AlpineBitsCapability.FREE_ROOMS_HOTEL_INV_COUNT_NOTIF_ACCEPT_ROOMS)) {
            builder.withRoomsSupport();
        }
        if (router.isCapabilityDefined(version, AlpineBitsCapability.FREE_ROOMS_HOTEL_INV_COUNT_NOTIF_ACCEPT_CATEGORIES)) {
            builder.withCategoriesSupport();
        }
        if (router.isCapabilityDefined(version, AlpineBitsCapability.FREE_ROOMS_HOTEL_INV_COUNT_NOTIF_ACCEPT_DELTAS)) {
            builder.withDeltasSupport();
        }
        if (router.isCapabilityDefined(version, AlpineBitsCapability.FREE_ROOMS_HOTEL_INV_COUNT_NOTIF_ACCEPT_OUT_OF_ORDER)) {
            builder.withOutOfOrderSupport();
        }
        if (router.isCapabilityDefined(version, AlpineBitsCapability.FREE_ROOMS_HOTEL_INV_COUNT_NOTIF_ACCEPT_OUT_OF_MARKET)) {
            builder.withOutOfMarketSupport();
        }
        if (router.isCapabilityDefined(version, AlpineBitsCapability.FREE_ROOMS_HOTEL_INV_COUNT_NOTIF_ACCEPT_CLOSING_SEASONS)) {
            builder.withClosingSeasonsSupport();
        }
        return builder.build();
    }

}
