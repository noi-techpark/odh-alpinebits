/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.routing;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link RouterContextKey} class.
 */
public class RouterContextKeyTest {

    @Test
    public void testContextKey_AlpineBitsRouter() {
        assertEquals(RouterContextKey.ALPINEBITS_ROUTER.getType(), Router.class);
    }

}