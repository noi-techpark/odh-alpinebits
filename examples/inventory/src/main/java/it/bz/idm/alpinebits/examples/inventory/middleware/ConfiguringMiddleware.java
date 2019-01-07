/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.inventory.middleware;

import it.bz.idm.alpinebits.common.constants.AlpineBitsAction;
import it.bz.idm.alpinebits.common.constants.AlpineBitsCapability;
import it.bz.idm.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.idm.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.idm.alpinebits.examples.inventory.middleware.configuration.InventoryPullMiddlewareBuilder;
import it.bz.idm.alpinebits.examples.inventory.middleware.configuration.InventoryPushMiddlewareBuilder;
import it.bz.idm.alpinebits.housekeeping.middleware.HousekeepingGetCapabilitiesMiddleware;
import it.bz.idm.alpinebits.housekeeping.middleware.HousekeepingGetVersionMiddleware;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;
import it.bz.idm.alpinebits.routing.DefaultRouter;
import it.bz.idm.alpinebits.routing.Router;
import it.bz.idm.alpinebits.routing.middleware.RoutingMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.AlpineBitsClientProtocolMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.BasicAuthenticationMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.GzipUnsupportedMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.MultipartFormDataParserMiddleware;

import javax.xml.bind.JAXBException;
import java.util.Arrays;

/**
 * This {@link Middleware} configures a set of middlewares, such that
 * the resulting server is able to respond to AlpineBits Inventory requests.
 * <p>
 * The resulting server supports the {@link AlpineBitsVersion#V_2017_10}
 * version only.
 * <p>
 * A basic authentication check is enforced, although any username and
 * password combination is valid. In other words: a request MUST contain
 * basic authentication information, but that information is not checked
 * any further.
 */
public class ConfiguringMiddleware implements Middleware {

    private final Middleware middleware;

    public ConfiguringMiddleware() throws JAXBException {
        this.middleware = ComposingMiddlewareBuilder.compose(Arrays.asList(
                new AlpineBitsClientProtocolMiddleware(),
                new BasicAuthenticationMiddleware(),
                new GzipUnsupportedMiddleware(),
                new MultipartFormDataParserMiddleware(),
                this.buildRoutingMiddleware()
        ));
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        this.middleware.handleContext(ctx, chain);
    }

    private Middleware buildRoutingMiddleware() throws JAXBException {
        Router router = new DefaultRouter.Builder()
                .version(AlpineBitsVersion.V_2017_10)
                .supportsAction(AlpineBitsAction.GET_VERSION)
                .withCapabilities(AlpineBitsCapability.GET_VERSION)
                .using(new HousekeepingGetVersionMiddleware())
                .and()
                .supportsAction(AlpineBitsAction.GET_CAPABILITIES)
                .withCapabilities(AlpineBitsCapability.GET_CAPABILITIES)
                .using(new HousekeepingGetCapabilitiesMiddleware())
                .and()
                .supportsAction(AlpineBitsAction.INVENTORY_BASIC_PUSH)
                .withCapabilities(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_INVENTORY)
                .using(InventoryPushMiddlewareBuilder.buildInventoryPushMiddleware())
                .and()
                .supportsAction(AlpineBitsAction.INVENTORY_BASIC_PULL)
                .withCapabilities(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_INFO)
                .using(InventoryPullMiddlewareBuilder.buildInventoryPullMiddleware())
                .and()
                .supportsAction(AlpineBitsAction.INVENTORY_HOTEL_INFO_PUSH)
                .withCapabilities(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_INFO_INVENTORY)
                .using(InventoryPushMiddlewareBuilder.buildInventoryPushMiddleware())
                .and()
                .supportsAction(AlpineBitsAction.INVENTORY_HOTEL_INFO_PULL)
                .withCapabilities(AlpineBitsCapability.INVENTORY_HOTEL_DESCRIPTIVE_INFO_INFO)
                .using(InventoryPullMiddlewareBuilder.buildInventoryPullMiddleware())
                .versionComplete()
                .buildRouter();
        return new RoutingMiddleware(router);
    }

}