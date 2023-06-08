// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.routing;

import it.bz.opendatahub.alpinebits.routing.constants.Action;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Test cases for {@link DefaultRouter.Builder} class.
 */
public class DefaultRouterBuilderTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testForVersion_throwsOnNullVersion() {
        new DefaultRouter.Builder().version(null);
    }

    @Test
    public void testForVersion_returnsActionConfigurerBuilder() {
        RoutingBuilder builder = new DefaultRouter.Builder().version("");
        assertEquals(builder.getClass(), RoutingBuilder.class);
    }

    @Test
    public void testBuild() {
        Router router = new DefaultRouter.Builder()
                .version("")
                .supportsAction(Action.of("some", "value"))
                .withCapabilities()
                .using((ctx, chain) -> {
                })
                .versionComplete()
                .buildRouter();
        assertNotNull(router);
    }

}