/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing;

import it.bz.idm.alpinebits.routing.utils.VersionRoutingBuilderHelper;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * Test cases for {@link VersionRoutingBuilder} class.
 */
public class VersionRoutingBuilderTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddMiddleware_newBuilderThrowsOnNullCallback() {
        DefaultRouter.Builder parentBuilder = new DefaultRouter.Builder();
        VersionRoutingBuilder.newBuilder(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddMiddleware_forVersionThrowsOnNullVersion() {
        VersionRoutingBuilder builder = VersionRoutingBuilderHelper.buildDefaultVersionRoutingBuilder();
        builder.forVersion(null);
    }

    @Test
    public void testDone_returnsRouter() {
        VersionRoutingBuilder builder = VersionRoutingBuilderHelper.buildDefaultVersionRoutingBuilder();

        Router router = builder.buildRouter();
        assertNotNull(router);
    }

}