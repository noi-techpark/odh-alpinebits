/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing;

import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Key;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.impl.SimpleContext;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.testng.Assert.*;

/**
 * Test cases for {@link DefaultRouter} class.
 */
public class DefaultRouterTest {

    private static final String DEFAULT_VERSION = "2017-10";
    private static final String DEFAULT_ACTION = "DEFAULT_ACTION";
    private static final String UNKNOWN_VERSION = "some_version";
    private static final String UNKNOWN_ACTION = "some_action";

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindMiddleware_throwsOnNullVersion() {
        this.getRouter(null, DEFAULT_ACTION);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindMiddleware_throwsOnNullAction() {
        this.getRouter(DEFAULT_VERSION, null);
    }

    @Test
    public void testFindMiddleware_emptyOptionalOnUnknownVersion() {
        Router router = this.getRouter(DEFAULT_VERSION, DEFAULT_ACTION);
        Optional<Middleware> middleware = router.findMiddleware(UNKNOWN_VERSION, DEFAULT_ACTION);
        assertFalse(middleware.isPresent());
    }

    @Test
    public void testFindMiddleware_emptyOptionalOnUnknownAction() {
        Router router = this.getRouter(DEFAULT_VERSION, DEFAULT_ACTION);
        Optional<Middleware> middleware = router.findMiddleware(DEFAULT_VERSION, UNKNOWN_ACTION);
        assertFalse(middleware.isPresent());
    }

    @Test
    public void testFindMiddleware_notEmptyIfFound() {
        Router router = this.getDefaultRouter();
        Optional<Middleware> middleware = router.findMiddleware(DEFAULT_VERSION, DEFAULT_ACTION);
        assertTrue(middleware.isPresent());
    }

    @Test
    public void testFindMiddleware_expectedMiddlewareExecutionWhenInvoked() {
        Key<String> ctxKey = Key.key("ctxKey", String.class);
        String ctxValue = "ctxValue";

        Middleware middleware = (ctx, chain) -> ctx.put(ctxKey, ctxValue);
        Router router = this.getRouter(DEFAULT_VERSION, DEFAULT_ACTION, middleware);
        Optional<Middleware> optionalMiddleware = router.findMiddleware(DEFAULT_VERSION, DEFAULT_ACTION);

        // Use optional value without any further test (e.g. Optional#isPresent()), because if
        // the optional is not present, an Exception is thrown
        Middleware resultMiddleware = optionalMiddleware.get();

        Context ctx = new SimpleContext();
        resultMiddleware.handleContext(ctx, null);

        String value = ctx.getOrThrow(ctxKey);

        assertEquals(value, ctxValue);
    }

    @Test
    public void testGetVersions_listOfDefinedVersions() {
        String version1 = "2017-10";
        String version2 = "2018-10";

        Router router = new DefaultRouter.Builder()
                .forVersion(version1)
                .done()
                .forVersion(version2)
                .done()
                .buildRouter();
        Collection<String> versions = router.getVersions();
        assertEquals(versions, Arrays.asList(version1, version2));
    }

    @Test
    public void testGetVersion_returnVersionOnMatch() {
        Router router = this.getDefaultRouter();
        assertEquals(router.getVersion(DEFAULT_VERSION), DEFAULT_VERSION);
    }

    @Test
    public void testGetVersion_returnHighestVersionIfNoMatch() {
        String version1 = "2017-10";
        String version2 = "2018-10";

        Router router = new DefaultRouter.Builder()
                .forVersion(version1)
                .done()
                .forVersion(version2)
                .done()
                .buildRouter();
        assertEquals(router.getVersion("xxx"), version2);
    }

    @Test
    public void testGetActionsForVersion_emptyForUnknownVersion() {
        Router router = this.getDefaultRouter();
        Optional<Collection<String>> optional = router.getActionsForVersion(UNKNOWN_VERSION);
        assertFalse(optional.isPresent());
    }

    @Test
    public void testGetActionsForVersion_emptyForKnownVersionAndEmptyActions() {
        Router router = new DefaultRouter.Builder()
                .forVersion(DEFAULT_VERSION)
                .done()
                .buildRouter();
        Optional<Collection<String>> optional = router.getActionsForVersion(DEFAULT_VERSION);

        // Use optional value without any further test (e.g. Optional#isPresent()), because if
        // the optional is not present, an Exception is thrown
        Collection<String> actions = optional.get();
        assertTrue(actions.isEmpty());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetActionsForVersion_throwsOnNullVersion() {
        this.getDefaultRouter().getActionsForVersion(null);
    }

    @Test
    public void testGetActionsForVersion_listOfActions() {
        String action1 = "action1";
        String action2 = "action2";
        Middleware middleware = (ctx, chain) -> {
        };

        Router router = new DefaultRouter.Builder()
                .forVersion(DEFAULT_VERSION)
                .addMiddleware(action1, middleware)
                .addMiddleware(action2, middleware)
                .done()
                .buildRouter();

        Optional<Collection<String>> optional = router.getActionsForVersion(DEFAULT_VERSION);

        // Use optional value without any further test (e.g. Optional#isPresent()), because if
        // the optional is not present, an Exception is thrown
        Collection<String> actions = optional.get();

        assertEquals(actions.size(), 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIsActionDefined_throwsOnNullVersion() {
        Router router = this.getDefaultRouter();
        assertFalse(router.isActionDefined(null, DEFAULT_ACTION));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIsActionDefined_throwsOnNullAction() {
        Router router = this.getDefaultRouter();
        assertFalse(router.isActionDefined(DEFAULT_VERSION, null));
    }

    @Test
    public void testIsActionDefined_falseForUnknownVersion() {
        Router router = this.getDefaultRouter();
        assertFalse(router.isActionDefined(UNKNOWN_VERSION, DEFAULT_ACTION));
    }

    @Test
    public void testIsActionDefined_falseForUnknownAction() {
        Router router = this.getDefaultRouter();
        assertFalse(router.isActionDefined(DEFAULT_VERSION, UNKNOWN_ACTION));
    }

    @Test
    public void testIsActionDefined_trueForKnownVersionAndAction() {
        Router router = this.getDefaultRouter();
        assertTrue(router.isActionDefined(DEFAULT_VERSION, DEFAULT_ACTION));
    }

    @Test
    public void testIsRouteDefined_falseIfNotFound() {
        Router router = this.getDefaultRouter();
        boolean isRouteDefined = router.isRouteDefined(UNKNOWN_VERSION, DEFAULT_ACTION);
        assertFalse(isRouteDefined);
    }

    @Test
    public void testIsRouteDefined_falseIfFound() {
        Router router = this.getDefaultRouter();
        boolean isRouteDefined = router.isRouteDefined(DEFAULT_VERSION, DEFAULT_ACTION);
        assertTrue(isRouteDefined);
    }

    @Test
    public void testIsVersionDefined_falseForUnknownVersion() {
        Router router = this.getDefaultRouter();
        assertFalse(router.isVersionDefined(UNKNOWN_VERSION));
    }

    @Test
    public void testIsVersionDefined_trueForKnownVersion() {
        Router router = this.getDefaultRouter();
        assertTrue(router.isVersionDefined(DEFAULT_VERSION));
    }


    private Router getDefaultRouter() {
        return this.getRouter(DEFAULT_VERSION, DEFAULT_ACTION);
    }

    private Router getRouter(String version, String action) {
        return this.getRouter(version, action, (ctx, chain) -> {
        });
    }

    private Router getRouter(String version, String action, Middleware middleware) {
        return new DefaultRouter.Builder()
                .forVersion(version)
                .addMiddleware(action, middleware)
                .done()
                .buildRouter();
    }

}