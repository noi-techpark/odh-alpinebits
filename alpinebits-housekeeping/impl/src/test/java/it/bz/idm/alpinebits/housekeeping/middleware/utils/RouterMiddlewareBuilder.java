/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.housekeeping.middleware.utils;

import it.bz.idm.alpinebits.common.constants.HousekeepingActionEnum;
import it.bz.idm.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.idm.alpinebits.housekeeping.middleware.HousekeepingGetCapabilitiesMiddleware;
import it.bz.idm.alpinebits.housekeeping.middleware.HousekeepingGetVersionMiddleware;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.routing.DefaultRouter;
import it.bz.idm.alpinebits.routing.Router;
import it.bz.idm.alpinebits.routing.VersionRoutingBuilder;
import it.bz.idm.alpinebits.routing.middleware.RoutingMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.AlpineBitsClientProtocolMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.MultipartFormDataParserMiddleware;

import java.util.Arrays;
import java.util.Collection;

/**
 * Helper class to build pre-defined routing {@link Middleware}.
 */
public class RouterMiddlewareBuilder {

    public static final String DEFAULT_VERSION = "2017-10";
    public static final String DEFAULT_ACTION = "some action";

    private static final Collection<String> VERSION_LIST = Arrays.asList(
            "2010-08",
            "2010-10",
            "2011-09",
            "2011-10",
            "2011-11",
            "2012-05",
            "2012-05b",
            "2013-04",
            "2014-04",
            "2015-07",
            "2015-07b",
            "2017-10"
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
                .forVersion(DEFAULT_VERSION)
                .addMiddleware(HousekeepingActionEnum.GET_VERSION.getAction(), new HousekeepingGetVersionMiddleware())
                .done()
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
        VersionRoutingBuilder builder = new DefaultRouter.Builder()
                .forVersion(DEFAULT_VERSION)
                .addMiddleware(HousekeepingActionEnum.GET_VERSION.getAction(), new HousekeepingGetVersionMiddleware())
                .done();
        VERSION_LIST.forEach(version -> builder.forVersion(version).done());
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
                .forVersion(DEFAULT_VERSION)
                .addMiddleware(HousekeepingActionEnum.GET_CAPABLILITIES.getAction(), new HousekeepingGetCapabilitiesMiddleware())
                .done()
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
    public static Middleware buildRoutingMiddlewareWithCapabilititesAndCustomAction() {
        Router router = new DefaultRouter.Builder()
                .forVersion(DEFAULT_VERSION)
                .addMiddleware(HousekeepingActionEnum.GET_CAPABLILITIES.getAction(), new HousekeepingGetCapabilitiesMiddleware())
                .addMiddleware(DEFAULT_ACTION, (ctx, chain) -> {
                })
                .done()
                .buildRouter();
        return new RoutingMiddleware(router);
    }

    public static Middleware buildRoutingMiddlewareForIntegrationTest() {
        Middleware servletMiddleware = ComposingMiddlewareBuilder.compose(Arrays.asList(
                new AlpineBitsClientProtocolMiddleware(),
                new MultipartFormDataParserMiddleware()
        ));

        VersionRoutingBuilder builder = new DefaultRouter.Builder()
                .forVersion(DEFAULT_VERSION)
                .addMiddleware(HousekeepingActionEnum.GET_VERSION.getAction(), new HousekeepingGetVersionMiddleware())
                .addMiddleware(HousekeepingActionEnum.GET_CAPABLILITIES.getAction(), new HousekeepingGetCapabilitiesMiddleware())
                .done();
        VERSION_LIST.forEach(version -> builder.forVersion(version).done());

        Middleware routingMiddleware = new RoutingMiddleware(builder.buildRouter());

        return ComposingMiddlewareBuilder.compose(Arrays.asList(servletMiddleware, routingMiddleware));
    }
}
