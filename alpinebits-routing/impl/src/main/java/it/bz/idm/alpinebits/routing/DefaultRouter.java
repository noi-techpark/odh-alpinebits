/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing;

import it.bz.idm.alpinebits.middleware.Middleware;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * This class implements the {@link Router} interface.
 * <p>
 * In addition, it provides a {@link DefaultRouter.Builder} to build new Routes.
 */
public final class DefaultRouter implements Router {

    private static final String VERSION_NULL_ERROR_MESSAGE = "The version must not be null";

    private final Map<String, Map<String, Middleware>> routes;

    private final String highestSupportedVersion;

    private DefaultRouter(Map<String, Map<String, Middleware>> routes, String highestSupportedVersion) {
        if (routes == null) {
            throw new IllegalArgumentException("The routes must not be null");
        }
        if (routes.isEmpty()) {
            throw new IllegalArgumentException("No routes defined");
        }
        if (highestSupportedVersion == null) {
            throw new IllegalArgumentException("The highestSupportedVersion must not be null");
        }
        this.routes = routes;
        this.highestSupportedVersion = highestSupportedVersion;
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
    public String getVersion(String requestedVersion) {
        Collection<String> versions = this.getVersions();
        if (versions.contains(requestedVersion)) {
            return requestedVersion;
        }
        return this.highestSupportedVersion;
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
     *          .buildRouter();
     * </pre>
     */
    public static final class Builder {

        /**
         * Start router building by providing a version.
         * <p>
         * See {@link VersionRoutingBuilder} and {@link ActionRoutingBuilder}
         * for further information
         *
         * @param version the (first) <code>version</code> to configure
         *                <code>actions</code> for. Other versions with
         *                assigned actions may be configured using the
         *                builder.
         * @return a {@link ActionRoutingBuilder} to configure actions
         * for the given <code>version</code>
         */
        public ActionRoutingBuilder forVersion(String version) {
            return VersionRoutingBuilder.newBuilder(this.routerBuilder()).forVersion(version);
        }

        /**
         * Return a function that builds a {@link DefaultRouter} on invocation.
         *
         * @return a function building a {@link DefaultRouter}
         */
        private Function<Map<String, Map<String, Middleware>>, Router> routerBuilder() {
            return routes -> {
                List<String> versions = new ArrayList<>(routes.keySet());
                Collections.sort(versions);
                String highestSupportedVersion = versions.get(versions.size() - 1);
                return new DefaultRouter(routes, highestSupportedVersion);
            };
        }
    }
}
