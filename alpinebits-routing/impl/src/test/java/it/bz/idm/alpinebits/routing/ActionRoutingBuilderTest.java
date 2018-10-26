/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing;

import it.bz.idm.alpinebits.routing.utils.ActionRoutingBuilderHelper;
import it.bz.idm.alpinebits.routing.utils.VersionRoutingBuilderHelper;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link ActionRoutingBuilder} class.
 */
public class ActionRoutingBuilderBuilderTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddMiddleware_newBuildThrowsOnNullParentBuilder() {
        ActionRoutingBuilder.newBuilder(null, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddMiddleware_addMiddlewareThrowsOnNullCallback() {
        VersionRoutingBuilder parentBuilder = VersionRoutingBuilderHelper.buildDefaultVersionRoutingBuilder();
        ActionRoutingBuilder.newBuilder(parentBuilder, null);
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddMiddleware_addMiddlewareThrowsOnNullAction() {
        ActionRoutingBuilder builder = ActionRoutingBuilderHelper.buildDefaultActionRoutingBuilder();
        builder.addMiddleware(null, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddMiddleware_addMiddlewareThrowsOnNullMiddleware() {
        ActionRoutingBuilder builder = ActionRoutingBuilderHelper.buildDefaultActionRoutingBuilder();
        builder.addMiddleware("some_action", null);
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDone_returnsParentBuilder() {
        VersionRoutingBuilder versionRoutingBuilder = VersionRoutingBuilderHelper.buildDefaultVersionRoutingBuilder();
        ActionRoutingBuilder actionRoutingBuilder = ActionRoutingBuilder.newBuilder(versionRoutingBuilder, null);

        VersionRoutingBuilder resultVersionRoutingBuilder = actionRoutingBuilder.done();
        assertEquals(versionRoutingBuilder, resultVersionRoutingBuilder);
    }
}