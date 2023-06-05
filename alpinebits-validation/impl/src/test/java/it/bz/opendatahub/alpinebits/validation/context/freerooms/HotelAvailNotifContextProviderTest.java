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
import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.RequiredContextKeyMissingException;
import it.bz.opendatahub.alpinebits.middleware.impl.SimpleContext;
import it.bz.opendatahub.alpinebits.routing.DefaultRouter;
import it.bz.opendatahub.alpinebits.routing.Router;
import it.bz.opendatahub.alpinebits.routing.RouterContextKey;
import it.bz.opendatahub.alpinebits.routing.constants.Action;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests for {@link HotelAvailNotifContextProvider}.
 */
public class HotelAvailNotifContextProviderTest {

    private static final String ALPINEBITS_VERSION = AlpineBitsVersion.V_2017_10;

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBuildContext_ShouldThrow_WhenContextIsNull() {
        new HotelAvailNotifContextProvider().buildContext(null);
    }

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testBuildContext_ShouldThrow_WhenVersionIsUndefined() {
        Context ctx = new SimpleContext();
        new HotelAvailNotifContextProvider().buildContext(ctx);
    }

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testBuildContext_ShouldThrow_WhenRouterIsUndefined() {
        Context ctx = new SimpleContext();
        ctx.put(RequestContextKey.REQUEST_VERSION, AlpineBitsVersion.V_2017_10);
        new HotelAvailNotifContextProvider().buildContext(ctx);
    }

    @Test
    public void testBuildContext_ShouldHaveDeltaSupport_WhenCapabilityIsSet() {
        Context ctx = new SimpleContext();
        ctx.put(RequestContextKey.REQUEST_VERSION, AlpineBitsVersion.V_2017_10);
        ctx.put(
                RouterContextKey.ALPINEBITS_ROUTER,
                this.buildRouter(AlpineBitsCapability.FREE_ROOMS_HOTEL_AVAIL_NOTIF_ACCEPT_DELTAS)
        );
        HotelAvailNotifContext hotelAvailNotifContext = new HotelAvailNotifContextProvider().buildContext(ctx);
        assertTrue(hotelAvailNotifContext.isDeltaSupported());
    }

    @Test
    public void testBuildContext_ShouldHaveFreeButNotBookableSupport_WhenCapabilityIsSet() {
        Context ctx = new SimpleContext();
        ctx.put(RequestContextKey.REQUEST_VERSION, AlpineBitsVersion.V_2017_10);
        ctx.put(
                RouterContextKey.ALPINEBITS_ROUTER,
                this.buildRouter(AlpineBitsCapability.FREE_ROOMS_HOTEL_AVAIL_NOTIF_ACCEPT_BOOKING_THRESHOLD)
        );
        HotelAvailNotifContext hotelAvailNotifContext = new HotelAvailNotifContextProvider().buildContext(ctx);
        assertTrue(hotelAvailNotifContext.isFreeButNotBookableSupported());
    }

    @Test
    public void testBuildContext_ShouldHaveRoomCategoriesSupport_WhenCapabilityIsSet() {
        Context ctx = new SimpleContext();
        ctx.put(RequestContextKey.REQUEST_VERSION, AlpineBitsVersion.V_2017_10);
        ctx.put(
                RouterContextKey.ALPINEBITS_ROUTER,
                this.buildRouter(AlpineBitsCapability.FREE_ROOMS_HOTEL_AVAIL_NOTIF_ACCEPT_CATEGORIES)
        );
        HotelAvailNotifContext hotelAvailNotifContext = new HotelAvailNotifContextProvider().buildContext(ctx);
        assertTrue(hotelAvailNotifContext.isRoomCategoriesSupported());
    }

    @Test
    public void testBuildContext_ShouldHaveDistinctRoomsSupport_WhenCapabilityIsSet() {
        Context ctx = new SimpleContext();
        ctx.put(RequestContextKey.REQUEST_VERSION, AlpineBitsVersion.V_2017_10);
        ctx.put(
                RouterContextKey.ALPINEBITS_ROUTER,
                this.buildRouter(AlpineBitsCapability.FREE_ROOMS_HOTEL_AVAIL_NOTIF_ACCEPT_ROOMS)
        );
        HotelAvailNotifContext hotelAvailNotifContext = new HotelAvailNotifContextProvider().buildContext(ctx);
        assertTrue(hotelAvailNotifContext.isDistinctRoomsSupported());
    }

    private Router buildRouter(String... capabilities) {
        return new DefaultRouter.Builder()
                .version(ALPINEBITS_VERSION)
                .supportsAction(Action.FREE_ROOMS_HOTEL_AVAIL_NOTIF_FREE_ROOMS)
                .withCapabilities(capabilities)
                .using(null)
                .versionComplete()
                .buildRouter();
    }
}