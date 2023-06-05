// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking.utils;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.routing.DefaultRouter;
import it.bz.opendatahub.alpinebits.routing.Router;
import it.bz.opendatahub.alpinebits.routing.constants.Action;

/**
 * This class provides methods to build a {@link Router}, it is used for tests only.
 */
public class RouterBuilder {

    public static final String DEFAULT_VERSION = AlpineBitsVersion.V_2018_10;
    public static final Action DEFAULT_ACTION = Action.of("action request param", "some action");
    public static final String DEFAULT_CAPABILITY = "some capability";
    public static final Router DEFAULT_ROUTER = buildDefaultRouter();

    public static Router buildDefaultRouter() {
        return buildRouter(DEFAULT_VERSION);
    }

    public static Router buildRouter(String version) {
        return buildRouter(version, DEFAULT_ACTION);
    }

    public static Router buildRouter(String version, Action action) {
        return buildRouter(version, action, DEFAULT_CAPABILITY);
    }

    public static Router buildRouter(String version, Action action, String capability) {
        return new DefaultRouter.Builder()
                .version(version)
                .supportsAction(action)
                .withCapabilities(capability)
                .using((ctx, chain) -> {
                })
                .versionComplete()
                .buildRouter();
    }
}
