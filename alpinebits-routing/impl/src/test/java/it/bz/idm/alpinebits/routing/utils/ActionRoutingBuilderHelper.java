/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing.utils;

import it.bz.idm.alpinebits.routing.ActionRoutingBuilder;
import it.bz.idm.alpinebits.routing.VersionRoutingBuilder;

/**
 * Helper class with methods to generate {@link ActionRoutingBuilder}
 * instances, used for tests.
 */
public class ActionRoutingBuilderHelper {

    /**
     * Return an {@link ActionRoutingBuilder} for the
     * {@link VersionRoutingBuilderHelper#DEFAULT_VERSION}.
     *
     * @return an {@link ActionRoutingBuilder} for the
     * {@link VersionRoutingBuilderHelper#DEFAULT_VERSION}
     */
    public static ActionRoutingBuilder buildDefaultActionRoutingBuilder() {
        return buildActionRoutingBuilderForVersion(VersionRoutingBuilderHelper.DEFAULT_VERSION);
    }

    /**
     * Return an {@link ActionRoutingBuilder} for the given <code>version</code>.
     *
     * @param version the returned {@link ActionRoutingBuilder} is configured
     *                for this <code>version</code>
     * @return an {@link ActionRoutingBuilder} for the given <code>version</code>
     */
    public static ActionRoutingBuilder buildActionRoutingBuilderForVersion(String version) {
        VersionRoutingBuilder builder = VersionRoutingBuilderHelper.buildVersionRoutingBuilderForVersion(version);
        return ActionRoutingBuilder.newBuilder(builder, routes -> {
        });
    }
}
