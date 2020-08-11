/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.context.freerooms;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for {@link AvailStatusMessagesContext}.
 */
public class AvailStatusMessagesContextTest {

    private static final String INSTANCE = "some instance";

    @Test
    public void testFromFreeRoomsContext() {
        HotelAvailNotifContext hotelAvailNotifContext = new HotelAvailNotifContext.Builder()
                .withFreeButNotBookableSupport()
                .withRoomCategoriesSupport()
                .withDistinctRoomsSupport()
                .build();
        AvailStatusMessagesContext ctx = AvailStatusMessagesContext.fromHotelAvailNotifContext(INSTANCE, hotelAvailNotifContext);
        assertEquals(ctx.getInstance(), INSTANCE);
        assertTrue(ctx.isFreeButNotBookableSupported());
        assertTrue(ctx.isRoomCategoriesSupported());
        assertTrue(ctx.isDistinctRoomsSupported());
    }

    @Test
    public void testGetInstance_ShouldBeNullByDefault() {
        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().build();
        assertNull(ctx.getInstance());
    }

    @Test
    public void testGetInstance_ShouldBeDefined_WhenSetByBuilder() {
        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().withInstance(INSTANCE).build();
        assertEquals(ctx.getInstance(), INSTANCE);
    }

    @Test
    public void testIsRoomCategoriesSupported_ShouldBeFalseByDefault() {
        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().build();
        assertFalse(ctx.isRoomCategoriesSupported());
    }

    @Test
    public void testIsRoomCategoriesSupported_ShouldBeTrue_WhenSetByBuilder() {
        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().withRoomsCategoriesSupport().build();
        assertTrue(ctx.isRoomCategoriesSupported());
    }

    @Test
    public void testIsDistinctRoomsSupported_ShouldBeFalseByDefault() {
        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().build();
        assertFalse(ctx.isDistinctRoomsSupported());
    }

    @Test
    public void testIsDistinctRoomsSupported_ShouldBeTrue_WhenSetByBuilder() {
        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().withDistinctRoomsSupport().build();
        assertTrue(ctx.isDistinctRoomsSupported());
    }

    @Test
    public void testIsFreeButNotBookableSupported_ShouldBeFalseByDefault() {
        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().build();
        assertFalse(ctx.isFreeButNotBookableSupported());
    }

    @Test
    public void testIsFreeButNotBookableSupported_ShouldBeTrue_WhenSetByBuilder() {
        AvailStatusMessagesContext ctx = new AvailStatusMessagesContext.Builder().withFreeButNotBookableSupport().build();
        assertTrue(ctx.isFreeButNotBookableSupported());
    }

}