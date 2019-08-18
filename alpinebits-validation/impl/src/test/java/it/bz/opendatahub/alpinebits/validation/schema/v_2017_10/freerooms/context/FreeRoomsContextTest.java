/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms.context;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for {@link FreeRoomsContext}.
 */
public class FreeRoomsContextTest {

    @Test
    public void testIsRoomCategoriesSupported_ShouldBeFalseByDefault() {
        FreeRoomsContext ctx = new FreeRoomsContext.Builder().build();
        assertFalse(ctx.isRoomCategoriesSupported());
    }

    @Test
    public void testIsRoomCategoriesSupported_ShouldBeTrue_WhenSetByBuilder() {
        FreeRoomsContext ctx = new FreeRoomsContext.Builder().withRoomCategoriesSupport().build();
        assertTrue(ctx.isRoomCategoriesSupported());
    }

    @Test
    public void testIsDistinctRoomsSupported_ShouldBeFalseByDefault() {
        FreeRoomsContext ctx = new FreeRoomsContext.Builder().build();
        assertFalse(ctx.isDistinctRoomsSupported());
    }

    @Test
    public void testIsDistinctRoomsSupported_ShouldBeTrue_WhenSetByBuilder() {
        FreeRoomsContext ctx = new FreeRoomsContext.Builder().withDistinctRoomsSupport().build();
        assertTrue(ctx.isDistinctRoomsSupported());
    }

    @Test
    public void testIsDeltaSupported_ShouldBeFalseByDefault() {
        FreeRoomsContext ctx = new FreeRoomsContext.Builder().build();
        assertFalse(ctx.isDeltaSupported());
    }

    @Test
    public void testIsDeltaSupported_ShouldBeTrue_WhenSetByBuilder() {
        FreeRoomsContext ctx = new FreeRoomsContext.Builder().withDeltaSupport().build();
        assertTrue(ctx.isDeltaSupported());
    }

    @Test
    public void testIsFreeButNotBookableSupported_ShouldBeFalseByDefault() {
        FreeRoomsContext ctx = new FreeRoomsContext.Builder().build();
        assertFalse(ctx.isFreeButNotBookableSupported());
    }

    @Test
    public void testIsFreeButNotBookableSupported_ShouldBeTrue_WhenSetByBuilder() {
        FreeRoomsContext ctx = new FreeRoomsContext.Builder().withFreeButNotBookableSupport().build();
        assertTrue(ctx.isFreeButNotBookableSupported());
    }
}