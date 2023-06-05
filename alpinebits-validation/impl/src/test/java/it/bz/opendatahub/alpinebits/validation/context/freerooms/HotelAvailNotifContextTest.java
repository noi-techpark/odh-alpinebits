// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.context.freerooms;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Tests for {@link HotelAvailNotifContext}.
 */
public class HotelAvailNotifContextTest {

    @Test
    public void testIsRoomCategoriesSupported_ShouldBeFalseByDefault() {
        HotelAvailNotifContext ctx = new HotelAvailNotifContext.Builder().build();
        assertFalse(ctx.isRoomCategoriesSupported());
    }

    @Test
    public void testIsRoomCategoriesSupported_ShouldBeTrue_WhenSetByBuilder() {
        HotelAvailNotifContext ctx = new HotelAvailNotifContext.Builder().withRoomCategoriesSupport().build();
        assertTrue(ctx.isRoomCategoriesSupported());
    }

    @Test
    public void testIsDistinctRoomsSupported_ShouldBeFalseByDefault() {
        HotelAvailNotifContext ctx = new HotelAvailNotifContext.Builder().build();
        assertFalse(ctx.isDistinctRoomsSupported());
    }

    @Test
    public void testIsDistinctRoomsSupported_ShouldBeTrue_WhenSetByBuilder() {
        HotelAvailNotifContext ctx = new HotelAvailNotifContext.Builder().withDistinctRoomsSupport().build();
        assertTrue(ctx.isDistinctRoomsSupported());
    }

    @Test
    public void testIsDeltaSupported_ShouldBeFalseByDefault() {
        HotelAvailNotifContext ctx = new HotelAvailNotifContext.Builder().build();
        assertFalse(ctx.isDeltaSupported());
    }

    @Test
    public void testIsDeltaSupported_ShouldBeTrue_WhenSetByBuilder() {
        HotelAvailNotifContext ctx = new HotelAvailNotifContext.Builder().withDeltaSupport().build();
        assertTrue(ctx.isDeltaSupported());
    }

    @Test
    public void testIsFreeButNotBookableSupported_ShouldBeFalseByDefault() {
        HotelAvailNotifContext ctx = new HotelAvailNotifContext.Builder().build();
        assertFalse(ctx.isFreeButNotBookableSupported());
    }

    @Test
    public void testIsFreeButNotBookableSupported_ShouldBeTrue_WhenSetByBuilder() {
        HotelAvailNotifContext ctx = new HotelAvailNotifContext.Builder().withFreeButNotBookableSupport().build();
        assertTrue(ctx.isFreeButNotBookableSupported());
    }
}