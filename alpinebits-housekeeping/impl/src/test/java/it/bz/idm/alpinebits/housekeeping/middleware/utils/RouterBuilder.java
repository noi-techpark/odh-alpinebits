/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.housekeeping.middleware.utils;

import it.bz.idm.alpinebits.routing.DefaultRouter;
import it.bz.idm.alpinebits.routing.Router;

/**
 * This class provides methods to build a {@link Router}.
 */
public class RouterBuilder {

    public static final String DEFAULT_VERSION = "2017-10";

    public static Router buildDefaultRouter() {
        return buildRouterForVersion(DEFAULT_VERSION);
    }

    public static Router buildRouterForVersion(String version) {
        return new DefaultRouter.Builder().forVersion(version).done().buildRouter();
    }
}
