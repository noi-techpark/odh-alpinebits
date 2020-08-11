/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.housekeeping.middleware.utils;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsCapability;
import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.housekeeping.middleware.HousekeepingGetCapabilitiesMiddleware;
import it.bz.opendatahub.alpinebits.housekeeping.middleware.HousekeepingGetVersionMiddleware;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.routing.DefaultRouter;
import it.bz.opendatahub.alpinebits.routing.Router;
import it.bz.opendatahub.alpinebits.routing.RoutingBuilder;
import it.bz.opendatahub.alpinebits.routing.constants.Action;
import it.bz.opendatahub.alpinebits.routing.middleware.RoutingMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.AlpineBitsClientProtocolMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.ContentTypeHintMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.MultipartFormDataParserMiddleware;

import java.util.Arrays;
import java.util.Collection;

/**
 * Helper class to build pre-defined routing {@link Middleware}.
 */
public class RouterMiddlewareBuilder {

    public static final String DEFAULT_VERSION = AlpineBitsVersion.V_2017_10;

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
                .supportsAction(Action.GET_VERSION)
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
                .supportsAction(Action.GET_VERSION)
                .withCapabilities(AlpineBitsCapability.GET_VERSION)
                .using(new HousekeepingGetVersionMiddleware())
                .versionComplete();

        VERSION_LIST.forEach(version -> builder
                .and()
                .version(version)
                .supportsAction(Action.GET_VERSION)
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
                .supportsAction(Action.GET_CAPABILITIES)
                .withCapabilities(AlpineBitsCapability.GET_CAPABILITIES)
                .using(new HousekeepingGetCapabilitiesMiddleware())
                .versionComplete()
                .buildRouter();
        return new RoutingMiddleware(router);
    }

    /**
     * This method builds a routing {@link Middleware} able to route
     * AlpineBits <code>getCapabilities</code>.
     *
     * @return {@link Middleware} able to route AlpineBits
     * <code>getCapabilities</code>.
     */
    public static Middleware buildRoutingMiddlewareWithCapabilitiesAndCustomAction(String customCapability) {
        Router router = new DefaultRouter.Builder()
                .version(DEFAULT_VERSION)
                .supportsAction(Action.GET_CAPABILITIES)
                .withCapabilities(AlpineBitsCapability.GET_CAPABILITIES)
                .using(new HousekeepingGetCapabilitiesMiddleware())
                .and()
                .supportsAction(Action.of("some", AlpineBitsCapability.GET_CAPABILITIES))
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
                .supportsAction(Action.GET_VERSION)
                .withCapabilities(AlpineBitsCapability.GET_VERSION)
                .using(new HousekeepingGetVersionMiddleware())
                .and()
                .supportsAction(Action.GET_CAPABILITIES)
                .withCapabilities(AlpineBitsCapability.GET_CAPABILITIES)
                .using(new HousekeepingGetCapabilitiesMiddleware())
                .versionComplete();

        VERSION_LIST.forEach(version -> builder
                .and()
                .version(version)
                .supportsAction(Action.GET_VERSION)
                .withCapabilities(AlpineBitsCapability.GET_VERSION)
                .using(new HousekeepingGetVersionMiddleware())
                .versionComplete()
        );

        Middleware routingMiddleware = new RoutingMiddleware(builder.buildRouter());

        return ComposingMiddlewareBuilder.compose(
                new ContentTypeHintMiddleware(),
                new AlpineBitsClientProtocolMiddleware(),
                new MultipartFormDataParserMiddleware(),
                routingMiddleware
        );
    }
}
