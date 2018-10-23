/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing;

import it.bz.idm.alpinebits.middleware.Middleware;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * This class implements the {@link Router} interface.
 *
 * In addition, it provides a {@link DefaultRouter.Builder} to build new Routes.
 */
public final class DefaultRouter implements Router {

    private static final String VERSION_NULL_ERROR_MESSAGE = "The version must not be null";

    private final Map<String, Map<String, Middleware>> routes;

    private DefaultRouter(Map<String, Map<String, Middleware>> routes) {
        this.routes = routes;
    }

    @Override
    public Optional<Middleware> findMiddleware(String version, String action) {
        if (version == null) {
            throw new IllegalArgumentException(VERSION_NULL_ERROR_MESSAGE);
        }
        if (action == null) {
            throw new IllegalArgumentException("The action must not be null");
        }

        Map<String, Middleware> routesForVersion = this.routes.get(version);

        if (routesForVersion == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(routesForVersion.get(action));
    }

    @Override
    public Collection<String> getVersions() {
        return this.routes.keySet();
    }

    @Override
    public Optional<Collection<String>> getActionsForVersion(String version) {
        if (version == null) {
            throw new IllegalArgumentException(VERSION_NULL_ERROR_MESSAGE);
        }

        Map<String, Middleware> routesForVersion = this.routes.get(version);

        if (routesForVersion == null) {
            return Optional.empty();
        }

        return Optional.of(routesForVersion.keySet());
    }

    @Override
    public boolean isActionDefined(String version, String action) {
        return this.findMiddleware(version, action).isPresent();
    }

    @Override
    public boolean isRouteDefined(String version, String action) {
        return this.findMiddleware(version, action).isPresent();
    }

    @Override
    public boolean isVersionDefined(String version) {
        return this.routes.get(version) != null;
    }

    /**
     * Use this builder to create a {@link DefaultRouter}.
     * <p>
     * Example usage:
     * <pre>
     *     DefaultRouter router = new DefaultRouter.Builder()
     *          .forVersion("2017-10")
     *              .addMiddleware("action1", middleware1)
     *              .addMiddleware("action2", middleware2)
     *              .done()
     *          .forVersion("2018-10")
     *              .addMiddleware("action3", middleware3)
     *              .addMiddleware("action4", middleware4)
     *              .done()
     *          .build();
     * </pre>
     */
    public static class Builder {
        private final Map<String, Map<String, Middleware>> buildingRoutes = new ConcurrentHashMap<>();

        /**
         * Begin the configuration of middlewares for the given AlpineBits
         * <code>version</code>.
         * <p>
         * This method returns a {@link ActionConfigurer.Builder} that can
         * be used to configure action-to-middleware assignments. The router
         * will later on base its routing decisions on this assignments.
         * <p>
         * Note, that all middlewares build using the returned
         * {@link ActionConfigurer.Builder} are defined only for the given
         * <code>version</code>.
         *
         * @param version the AlpineBits version for which to return the
         *                {@link ActionConfigurer.Builder}
         * @return an {@link ActionConfigurer.Builder} used to assign actions
         * to middlewares for the given <code>version</code>
         */
        public ActionConfigurer.Builder forVersion(String version) {
            if (version == null) {
                throw new IllegalArgumentException(VERSION_NULL_ERROR_MESSAGE);
            }

            Consumer<Map<String, Middleware>> actionMerger = this.getActionMerger(version);
            return new ActionConfigurer.Builder(this, actionMerger);
        }

        /**
         * Return a {@link Router} that matches the configuration provided by
         * this builder.
         *
         * @return a {@link Router} that can be used to route AlpineBits request
         * based on their <code>version</code> and <code>action</code>
         */
        public Router build() {
            return new DefaultRouter(this.buildingRoutes);
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
                Map<String, Middleware> configuredRoutesForVersion = this.buildingRoutes.get(version);
                if (configuredRoutesForVersion == null) {
                    // Put the action list If no actions are defined for this version
                    this.buildingRoutes.put(version, configuredActions);
                } else {
                    configuredRoutesForVersion.putAll(configuredActions);
                }
            };
        }
    }
}
