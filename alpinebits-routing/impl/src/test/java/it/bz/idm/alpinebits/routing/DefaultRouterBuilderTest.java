/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing;

import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.routing.utils.TestMiddleware;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Test cases for {@link DefaultRouter.Builder} class.
 */
public class DefaultRouterBuilderTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testForVersion_throwsOnNullVersion() {
        new DefaultRouter.Builder().forVersion(null);
    }

    @Test
    public void testForVersion_returnsActionConfigurerBuilder() {
        ActionConfigurer.Builder builder = new DefaultRouter.Builder().forVersion("");
        assertEquals(builder.getClass(), ActionConfigurer.Builder.class);
    }

    @Test
    public void testBuild() {
        Middleware middleware = new TestMiddleware();
        Router router = new DefaultRouter.Builder()
                .forVersion("")
                    .addMiddleware("some_action", middleware)
                    .done()
                .build();
        assertNotNull(router);
    }

}