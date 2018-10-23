/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link ActionConfigurer.Builder} class.
 */
public class ActionConfigurerBuilderTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddMiddleware_throwsOnNullAction() {
        ActionConfigurer.Builder builder = this.getBuilder();
        builder.addMiddleware(null, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddMiddleware_throwsOnNullMiddleware() {
        ActionConfigurer.Builder builder = this.getBuilder();
        builder.addMiddleware("some_action", null);
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDone_ReturnsParentBuilder() {
        DefaultRouter.Builder routerBuilder = new DefaultRouter.Builder();
        ActionConfigurer.Builder builder = new ActionConfigurer.Builder(routerBuilder, o -> {});

        DefaultRouter.Builder resultBuilder = builder.addMiddleware("some_action", null).done();
        assertEquals(resultBuilder, routerBuilder);
    }

    private ActionConfigurer.Builder getBuilder() {
        return new ActionConfigurer.Builder(null, map -> {});
    }
}