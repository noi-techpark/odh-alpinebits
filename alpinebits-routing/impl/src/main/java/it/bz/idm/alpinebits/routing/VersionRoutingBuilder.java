/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing;

import it.bz.idm.alpinebits.middleware.Middleware;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This class is used to create mappings of <code>version</code>
 * to a list of {@link Middleware} actions. It's mainly used together with
 * {@link DefaultRouter.Builder} and {@link ActionRoutingBuilder}.
 * <p>
 * See {@link DefaultRouter.Builder} for further information on
 * how to build a {@link DefaultRouter}.
 */
public final class VersionRoutingBuilder {

    private final Map<String, Map<String, Middleware>> configuredRoutes = new ConcurrentHashMap<>();
    private final Function<Map<String, Map<String, Middleware>>, Router> callback;

    private VersionRoutingBuilder(
            Function<Map<String, Map<String, Middleware>>, Router> callback
    ) {
        this.callback = callback;
    }

    /**
     * Return a new {@link VersionRoutingBuilder} instance.
     *
     * @param callback the callback is invoked when the
     *                 {@link VersionRoutingBuilder#buildRouter()} is called.
     *                 The current configured routes are used as
     *                 parameters
     * @return a new {@link ActionRoutingBuilder} instance
     */
    public static VersionRoutingBuilder newBuilder(
            Function<Map<String, Map<String, Middleware>>, Router> callback
    ) {
        if (callback == null) {
            throw new IllegalArgumentException("The callback must not be null");
        }
        return new VersionRoutingBuilder(callback);
    }

    /**
     * Configure a <code>version</code> to a list of{@link Middleware} assignment for the
     * current {@link VersionRoutingBuilder} instance.
     *
     * @param version the <code>version</code> that links to the list
     *                of <code>middlewares</code>
     * @return the current {@link VersionRoutingBuilder} instance
     * @throws IllegalArgumentException if the <code>version</code> or
     *                                  <code>middleware</code> is null
     */
    public ActionRoutingBuilder forVersion(String version) {
        if (version == null) {
            throw new IllegalArgumentException("The version must not be null");
        }

        Consumer<Map<String, Middleware>> actionMerger = this.getActionMerger(version);
        return ActionRoutingBuilder.newBuilder(this, actionMerger);
    }

    public Router buildRouter() {
        return this.callback.apply(this.configuredRoutes);
    }

    /**
     * Return a consumer that, when invoked, creates a merged map of AlpineBits
     * actions for the given AlpineBits version.
     *
     * @param version merge actions for this AlpineBits version
     * @return {@link Consumer} returning a merged map of AlpineBits actions for
     * the given AlpineBits version
     */
    private Consumer<Map<String, Middleware>> getActionMerger(String version) {
        return (Map<String, Middleware> configuredActions) -> {
            Map<String, Middleware> configuredVersion = this.configuredRoutes.get(version);
            if (configuredVersion == null) {
                // Put the action list If no actions are defined for this version
                this.configuredRoutes.put(version, configuredActions);
            } else {
                configuredVersion.putAll(configuredActions);
            }
        };
    }

}
