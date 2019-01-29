/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.routing;

import it.bz.opendatahub.alpinebits.middleware.Middleware;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This class is used by {@link DefaultRouter.Builder}
 * for route building.
 *
 * It's usefulness is limited for other cases.
 */
public final class RoutingBuilder {

    private final RoutingBuilder currentRoutingBuilder;
    private final DefaultRouter.Builder parentRouterBuilder;
    private final VersionConfiguration versionConfiguration = new VersionConfiguration();
    private final Consumer<VersionConfiguration> versionConfigurationConsumer;
    private final Supplier<Router> routerBuilder;

    public RoutingBuilder(
            DefaultRouter.Builder parentRouterBuilder,
            Consumer<VersionConfiguration> versionConfigurationConsumer,
            Supplier<Router> routerBuilder
    ) {
        this.currentRoutingBuilder = this;
        this.parentRouterBuilder = parentRouterBuilder;
        this.versionConfigurationConsumer = versionConfigurationConsumer;
        this.routerBuilder = routerBuilder;
    }

    /**
     * Add an <code>action</code> to {@link Middleware} assignment for the
     * current {@link RoutingBuilder} instance.
     *
     * @param action the <code>action</code> that links to the <code>middleware</code>
     * @return a {@link CapabilityBuilder} instance to further configure the route
     * @throws IllegalArgumentException if the <code>action</code> or
     *                                  <code>middleware</code> is null
     */
    public CapabilityBuilder supportsAction(String action) {
        if (action == null) {
            throw new IllegalArgumentException("The action must not be null");
        }

        return new CapabilityBuilder(action);
    }

    /**
     * Builder for AlpineBits capabilities for a given route.
     */
    public class CapabilityBuilder {
        private final String action;

        public CapabilityBuilder(String action) {
            this.action = action;
        }

        public MiddlewareBuilder withCapabilities(String... capabilities) {
            return withCapabilities(Arrays.asList(capabilities));
        }

        public MiddlewareBuilder withCapabilities(Collection<String> capabilities) {
            return withCapabilities(new HashSet<>(capabilities));
        }

        public MiddlewareBuilder withCapabilities(Set<String> capabilities) {
            return new MiddlewareBuilder(this.action, capabilities);
        }
    }

    /**
     * Builder for AlpineBits middleware for a given route.
     */
    public class MiddlewareBuilder {
        private final String action;
        private final Set<String> capabilities;

        public MiddlewareBuilder(String action, Set<String> capabilities) {
            this.action = action;
            this.capabilities = capabilities;
        }

        public AppenderBuilder using(Middleware middleware) {
            ActionConfiguration actionConfiguration = new ActionConfiguration(this.capabilities, middleware);
            versionConfiguration.addActionConfiguration(this.action, actionConfiguration);
            return new AppenderBuilder();
        }
    }

    /**
     * Builder to add more actions or complete the
     * current version configuration.
     */
    public class AppenderBuilder {
        public RoutingBuilder and() {
            return currentRoutingBuilder;
        }

        public FinalBuilder versionComplete() {
            versionConfigurationConsumer.accept(versionConfiguration);
            return new FinalBuilder();
        }
    }

    /**
     * Builder to build next version configuration or
     * to build the final {@link Router}.
     */
    public class FinalBuilder {
        public DefaultRouter.Builder and() {
            return parentRouterBuilder;
        }

        public Router buildRouter() {
            versionConfigurationConsumer.accept(versionConfiguration);
            return routerBuilder.get();
        }
    }
}
