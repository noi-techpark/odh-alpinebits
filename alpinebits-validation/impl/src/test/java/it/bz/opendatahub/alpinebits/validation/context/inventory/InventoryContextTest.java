// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.context.inventory;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Tests for {@link InventoryContext}.
 */
public class InventoryContextTest {

    private static final String ALPINEBITS_ACTION = AlpineBitsAction.INVENTORY_BASIC_PUSH;

    @Test
    public void testGetAction() {
        InventoryContext inventoryContext = new InventoryContext(ALPINEBITS_ACTION);
        assertEquals(inventoryContext.getAction(), ALPINEBITS_ACTION);
    }

}