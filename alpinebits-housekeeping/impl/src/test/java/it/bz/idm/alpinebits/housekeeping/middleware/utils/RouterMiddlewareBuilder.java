/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.housekeeping.middleware.utils;

import it.bz.idm.alpinebits.common.constants.AlpineBitsAction;
import it.bz.idm.alpinebits.common.constants.AlpineBitsCapability;
import it.bz.idm.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.idm.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.idm.alpinebits.housekeeping.middleware.HousekeepingGetCapabilitiesMiddleware;
import it.bz.idm.alpinebits.housekeeping.middleware.HousekeepingGetVersionMiddleware;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.routing.DefaultRouter;
import it.bz.idm.alpinebits.routing.Router;
import it.bz.idm.alpinebits.routing.RoutingBuilder;
import it.bz.idm.alpinebits.routing.middleware.RoutingMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.AlpineBitsClientProtocolMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.MultipartFormDataParserMiddleware;

import java.util.Arrays;
import java.util.Collection;

/**
 * Helper class to build pre-defined routing {@link Middleware}.
 */
public class RouterMiddlewareBuilder {

    public static final String DEFAULT_VERSION = AlpineBitsVersion.V_2017_10;
    public static final String DEFAULT_ACTION = "some action";

    private static final Collection<String> VERSION_LIST = Arrays.asList(
            AlpineBitsVersion.V_2010_08,
            AlpineBitsVersion.V_2010_10,
            AlpineBitsVersion.V_2011_09,
            AlpineBitsVersion.V_2011_10,
            AlpineBitsVersion.V_2011_11,
            AlpineBitsVersion.V_2012_05,
            AlpineBitsVersion.V_2012_05B,
            AlpineBitsVersion.V_2013_04,
            AlpineBitsVersion.V_2014_04,
            AlpineBitsVersion.V_2015_07,
            AlpineBitsVersion.V_2015_07B
    );

    /**
     * This method builds a routing {@link Middleware} with a single
     * version and no assigned action.
     *
     * @return {@link Middleware} able to route AlpineBits
     * <code>getVersion</code> actions
     */
    public static Middleware buildRoutingMiddlewareWithSingleVersion() {
        Router router = new DefaultRouter.Builder()
                .version(DEFAULT_VERSION)
                .supportsAction(AlpineBitsAction.GET_VERSION)
                .withCapabilities(AlpineBitsCapability.GET_VERSION)
                .using(new HousekeepingGetVersionMiddleware())
                .versionComplete()
                .buildRouter();
        return new RoutingMiddleware(router);
    }

    /**
     * This method builds a routing {@link Middleware} with a list
     * of versions and no assigned action, except the default version,
     * that provides the routing to the {@link HousekeepingGetVersionMiddleware}.
     *
     * @return {@link Middleware} able to route AlpineBits
     * <code>getVersion</code> actions
     */
    public static Middleware buildRoutingMiddlewareWithManyVersions() {
        RoutingBuilder.FinalBuilder builder = new DefaultRouter.Builder()
                .version(DEFAULT_VERSION)
                .supportsAction(AlpineBitsAction.GET_VERSION)
                .withCapabilities(AlpineBitsCapability.GET_VERSION)
                .using(new HousekeepingGetVersionMiddleware())
                .versionComplete();

        VERSION_LIST.forEach(version -> builder
                .and()
                .version(version)
                .supportsAction(AlpineBitsAction.GET_VERSION)
                .withCapabilities(AlpineBitsCapability.GET_VERSION)
                .using(new HousekeepingGetVersionMiddleware())
                .versionComplete()
        );

        Router router = builder.buildRouter();
        return new RoutingMiddleware(router);
    }

    /**
     * This method builds a routing {@link Middleware} able to route
     * AlpineBits <code>getCapabilities</code> actions.
     *
     * @return {@link Middleware} able to route AlpineBits
     * <code>getCapabilities</code> actions
     */
    public static Middleware buildRoutingMiddlewareWithCapabilititesAction() {
        Router router = new DefaultRouter.Builder()
                .version(DEFAULT_VERSION)
                .supportsAction(AlpineBitsAction.GET_CAPABILITIES)
                .withCapabilities(AlpineBitsCapability.GET_CAPABILITIES)
                .using(new HousekeepingGetCapabilitiesMiddleware())
                .versionComplete()
                .buildRouter();
        return new RoutingMiddleware(router);
    }

    /**
     * This method builds a routing {@link Middleware} able to route
     * AlpineBits <code>getCapabilities</code> and
     * {@link RouterMiddlewareBuilder#DEFAULT_ACTION} actions.
     *
     * @return {@link Middleware} able to route AlpineBits
     * <code>getCapabilities</code> and
     * {@link RouterMiddlewareBuilder#DEFAULT_ACTION} actions
     */
    public static Middleware buildRoutingMiddlewareWithCapabilititesAndCustomAction(String customCapability) {
        Router router = new DefaultRouter.Builder()
                .version(DEFAULT_VERSION)
                .supportsAction(AlpineBitsAction.GET_CAPABILITIES)
                .withCapabilities(AlpineBitsCapability.GET_CAPABILITIES)
                .using(new HousekeepingGetCapabilitiesMiddleware())
                .and()
                .supportsAction(DEFAULT_ACTION)
                .withCapabilities(customCapability)
                .using((ctx, chain) -> {
                })
                .versionComplete()
                .buildRouter();
        return new RoutingMiddleware(router);
    }

    public static Middleware buildRoutingMiddlewareForIntegrationTest() {
        RoutingBuilder.FinalBuilder builder = new DefaultRouter.Builder()
                .version(DEFAULT_VERSION)
                .supportsAction(AlpineBitsAction.GET_VERSION)
                .withCapabilities(AlpineBitsCapability.GET_VERSION)
                .using(new HousekeepingGetVersionMiddleware())
                .and()
                .supportsAction(AlpineBitsAction.GET_CAPABILITIES)
                .withCapabilities(AlpineBitsCapability.GET_CAPABILITIES)
                .using(new HousekeepingGetCapabilitiesMiddleware())
                .versionComplete();

        VERSION_LIST.forEach(version -> builder
                .and()
                .version(version)
                .supportsAction(AlpineBitsAction.GET_VERSION)
                .withCapabilities(AlpineBitsCapability.GET_VERSION)
                .using(new HousekeepingGetVersionMiddleware())
                .versionComplete()
        );

        Middleware routingMiddleware = new RoutingMiddleware(builder.buildRouter());

        return ComposingMiddlewareBuilder.compose(Arrays.asList(
                new AlpineBitsClientProtocolMiddleware(),
                new MultipartFormDataParserMiddleware(),
                routingMiddleware
        ));
    }
}
