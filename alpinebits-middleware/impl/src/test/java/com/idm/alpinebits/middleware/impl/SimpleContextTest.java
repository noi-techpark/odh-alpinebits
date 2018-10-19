/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.idm.alpinebits.middleware.impl;

import com.idm.alpinebits.middleware.Context;
import com.idm.alpinebits.middleware.RequiredContextKeyMissingException;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link SimpleContext} class.
 */
public class SimpleContextTest {

    @Test
    public void testGetState() {
        Context ctx = ContextBuilder.buildSimpleContext();

        String intKey = "intKey";
        int intValue = 1;
        ctx.set(intKey, intValue);

        Optional<Integer> ctxIntValue = ctx.get(intKey, Integer.class);
        assertTrue(ctxIntValue.isPresent());
        assertEquals(intValue, ctxIntValue.get().intValue());

        String longKey = "longKey";
        Long longValue = 2L;
        ctx.set(longKey, longValue);

        Optional<Long> ctxLongValue = ctx.get(longKey, Long.class);
        assertTrue(ctxLongValue.isPresent());
        assertEquals(longValue, ctxLongValue.get());

        String stringKey = "stringKey";
        String stringValue = "one";
        ctx.set(stringKey, stringValue);

        Optional<String> ctxStringValue = ctx.get(stringKey, String.class);
        assertTrue(ctxStringValue.isPresent());
        assertEquals(stringValue, ctxStringValue.get());

        String objectKey = "objectKey";
        Object objectValue = new Object();
        ctx.set(objectKey, objectValue);

        Optional<Object> ctxObjectValue = ctx.get(objectKey, Object.class);
        assertTrue(ctxObjectValue.isPresent());
        assertEquals(objectValue, ctxObjectValue.get());
    }

    @Test
    public void testGetOrThrow_ValuePresent() {
        Context ctx = ContextBuilder.buildSimpleContext();

        String intKey = "intKey";
        int intValue = 1;
        ctx.set(intKey, intValue);

        Integer ctxIntValue = ctx.getOrThrow(intKey, Integer.class);
        assertEquals(intValue, ctxIntValue.intValue());

        String longKey = "longKey";
        Long longValue = 2L;
        ctx.set(longKey, longValue);

        Long ctxLongValue = ctx.getOrThrow(longKey, Long.class);
        assertEquals(longValue, ctxLongValue);

        String stringKey = "stringKey";
        String stringValue = "one";
        ctx.set(stringKey, stringValue);

        String ctxStringValue = ctx.getOrThrow(stringKey, String.class);
        assertEquals(stringValue, ctxStringValue);

        String objectKey = "objectKey";
        Object objectValue = new Object();
        ctx.set(objectKey, objectValue);

        Object ctxObjectValue = ctx.getOrThrow(objectKey, Object.class);
        assertEquals(objectValue, ctxObjectValue);
    }

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testGetOrThrow_ValueNotPresent() {
        Context ctx = ContextBuilder.buildSimpleContext();

        String undefinedKey = "undefined key";

        ctx.getOrThrow(undefinedKey, Integer.class);
    }

    @Test
    public void testSetState() {
        Context ctx = ContextBuilder.buildSimpleContext();

        String intKey = "intKey";
        int intValue1 = 1;
        ctx.set(intKey, intValue1);

        Optional<Integer> ctxValue = ctx.get(intKey, Integer.class);
        assertTrue(ctxValue.isPresent());
        assertEquals(intValue1, ctxValue.get().intValue());

        int intValue2 = 2;
        ctx.set(intKey, intValue2);

        ctxValue = ctx.get(intKey, Integer.class);
        assertTrue(ctxValue.isPresent());
        assertEquals(intValue2, ctxValue.get().intValue());
    }

    @Test
    public void testRemoveState() {
        Context ctx = ContextBuilder.buildSimpleContext();

        String intKey = "intKey";
        int intValue = 1;
        ctx.set(intKey, intValue);

        Optional<Integer> ctxValue = ctx.get(intKey, Integer.class);
        assertTrue(ctxValue.isPresent());
        assertEquals(intValue, ctxValue.get().intValue());

        int removedValue = (int) ctx.remove(intKey);

        assertEquals(intValue, removedValue);
        assertFalse(ctx.get(intKey, Integer.class).isPresent());
    }

    @Test
    public void testStateContainsKey() {
        Context ctx = ContextBuilder.buildSimpleContext();

        String intKey = "intKey";
        int intValue = 1;
        ctx.set(intKey, intValue);

        assertTrue(ctx.contains(intKey));
        assertFalse(ctx.contains("undefined key"));
    }

    @Test
    public void testStateDoesntContainKey() {
        Context ctx = ContextBuilder.buildSimpleContext();

        String undefinedKey = "undefined key";

        assertFalse(ctx.contains(undefinedKey));
        assertEquals(ctx.get(undefinedKey, Object.class), Optional.empty());
    }

    @Test(expectedExceptions = ClassCastException.class)
    public void testGetterThrowsOnIncompatibleTypes() {
        Context ctx = ContextBuilder.buildSimpleContext();

        String intKey = "intKey";
        Integer intValue = 1;
        ctx.set(intKey, intValue);

        ctx.get(intKey, String.class);
    }
}