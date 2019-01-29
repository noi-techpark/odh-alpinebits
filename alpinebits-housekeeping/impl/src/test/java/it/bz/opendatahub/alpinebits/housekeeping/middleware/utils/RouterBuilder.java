/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.housekeeping.middleware.utils;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.routing.DefaultRouter;
import it.bz.opendatahub.alpinebits.routing.Router;

/**
 * This class provides methods to build a {@link Router}.
 */
public class RouterBuilder {

    public static final String DEFAULT_VERSION = AlpineBitsVersion.V_2017_10;
    public static final String DEFAULT_ACTION = "some action";

    public static Router buildDefaultRouter() {
        return buildRouterForVersion(DEFAULT_VERSION);
    }

    public static Router buildRouterForVersion(String version) {
        return new DefaultRouter.Builder()
                .version(version)
                .supportsAction(DEFAULT_ACTION)
                .withCapabilities()
                .using((ctx, chain) -> {})
                .versionComplete()
                .buildRouter();
    }
}
