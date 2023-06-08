// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.context.inventory;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.RequiredContextKeyMissingException;
import it.bz.opendatahub.alpinebits.middleware.impl.SimpleContext;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Tests for {@link InventoryContextProvider}.
 */
public class InventoryContextProviderTest {

    private static final String ALPINEBITS_ACTION = AlpineBitsAction.INVENTORY_BASIC_PUSH;

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBuildContext_ShouldThrow_WhenContextIsNull() {
        new InventoryContextProvider().buildContext(null);
    }

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testBuildContext_ShouldThrow_WhenActionIsUndefined() {
        Context ctx = new SimpleContext();
        new InventoryContextProvider().buildContext(ctx);
    }

    @Test
    public void testBuildContext_ShouldHaveActionFromContext() {
        Context ctx = new SimpleContext();
        ctx.put(RequestContextKey.REQUEST_ACTION, ALPINEBITS_ACTION);
        InventoryContext inventoryContext = new InventoryContextProvider().buildContext(ctx);
        assertEquals(inventoryContext.getAction(), ALPINEBITS_ACTION);
    }

}