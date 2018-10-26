/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing.utils;

import it.bz.idm.alpinebits.routing.DefaultRouter;
import it.bz.idm.alpinebits.routing.VersionRoutingBuilder;

/**
 * Helper class with methods to generate {@link VersionRoutingBuilder}
 * instances, used for tests.
 */
public class VersionRoutingBuilderHelper {

    public static final String DEFAULT_VERSION = "2017-10";

    /**
     * Return an {@link VersionRoutingBuilder} for the
     * {@link VersionRoutingBuilderHelper#DEFAULT_VERSION}.
     *
     * @return an {@link VersionRoutingBuilder} for the
     * {@link VersionRoutingBuilderHelper#DEFAULT_VERSION}
     */
    public static VersionRoutingBuilder buildDefaultVersionRoutingBuilder() {
        return buildVersionRoutingBuilderForVersion(DEFAULT_VERSION);
    }

    /**
     * Return an {@link VersionRoutingBuilder} for the given <code>version</code>.
     *
     * @param version the returned {@link VersionRoutingBuilder} is configured
     *                for this <code>version</code>
     * @return an {@link VersionRoutingBuilder} for the given <code>version</code>
     */
    public static VersionRoutingBuilder buildVersionRoutingBuilderForVersion(String version) {
        DefaultRouter.Builder builder = new DefaultRouter.Builder();
        return VersionRoutingBuilder.newBuilder(routes -> builder.forVersion(version).done().buildRouter());
    }
}
