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

/**
 * This class is used to create mappings of <code>action</code>
 * to {@link Middleware}.
 */
public final class ActionMappingBuilder {

    private final Map<String, Middleware> configuredActions = new ConcurrentHashMap<>();

    private final VersionRoutingBuilder parentBuilder;
    private final Consumer<Map<String, Middleware>> callback;

    private ActionMappingBuilder(
            VersionRoutingBuilder parentBuilder,
            Consumer<Map<String, Middleware>> callback
    ) {
        this.parentBuilder = parentBuilder;
        this.callback = callback;
    }

    public static ActionMappingBuilder newBuilder(
            VersionRoutingBuilder parentBuilder,
            Consumer<Map<String, Middleware>> callback
    ) {
        return new ActionMappingBuilder(parentBuilder, callback);
    }

    /**
     * Configure a <code>action</code> to {@link Middleware} assignment for the
     * current {@link ActionMappingBuilder} instance.
     *
     * @param action     the <code>action</code> that links to the <code>middleware</code>
     * @param middleware this <code>middleware</code> is linked to the given
     *                   <code>action</code>
     * @return the current {@link ActionMappingBuilder} instance
     * @throws IllegalArgumentException if the <code>action</code> or
     *                                  <code>middleware</code> is null
     */
    public ActionMappingBuilder addMiddleware(String action, Middleware middleware) {
        if (action == null) {
            throw new IllegalArgumentException("The action must not be null");
        }
        if (middleware == null) {
            throw new IllegalArgumentException("The middleware must not be null");
        }

//        Middleware composedMiddleware = ComposingMiddlewareBuilder.compose(Collections.singletonList(middleware));
        this.configuredActions.put(action, middleware);
        return this;
    }

    /**
     * Return the parent {@link VersionRoutingBuilder} to continue route building.
     *
     * @return the parent {@link VersionRoutingBuilder}
     */
    public VersionRoutingBuilder done() {
        this.callback.accept(this.configuredActions);
        return parentBuilder;
    }

//    /**
//     * Use this builder to create action-to-middleware assignments.
//     * Call the {@link Builder#done()} method when the configuration
//     * is finished. This returns the parent {@link DefaultRouter.Builder}
//     * that can be used to further build routes.
//     * <p>
//     * See {@link DefaultRouter.Builder} for further information on the
//     * usage.
//     */
//    public static class Builder {
//        private final ActionMappingBuilder managedInstance = new ActionMappingBuilder();
//        private final VersionRoutingBuilder.Builder parentBuilder;
//        private final Consumer<Map<String, Middleware>> callback;
//
//        public Builder(VersionRoutingBuilder.Builder parentBuilder, Consumer<Map<String, Middleware>> callback) {
//            this.parentBuilder = parentBuilder;
//            this.callback = callback;
//        }
//
//        /**
//         * Configure a <code>action</code> to {@link Middleware} assignment
//         * that can later on be used for routing.
//         *
//         * @param action     the <code>action</code> that links to the <code>middleware</code>
//         * @param middleware this <code>middleware</code> is linked to the given
//         *                   <code>action</code>
//         * @return the current {@link ActionMappingBuilder.Builder} instance
//         * @throws IllegalArgumentException if the <code>action</code> or
//         *                                  <code>middleware</code> is null
//         */
//        public ActionMappingBuilder.Builder addMiddleware(String action, Middleware middleware) {
//            this.managedInstance.addMiddleware(action, middleware);
//            return this;
//        }
//
//        /**
//         * Return the parent {@link VersionRoutingBuilder.Builder} to continue route building.
//         *
//         * @return the parent {@link VersionRoutingBuilder.Builder}
//         */
//        public VersionRoutingBuilder.Builder done() {
//            this.callback.accept(this.managedInstance.configuredActions);
//            return parentBuilder;
//        }
//
//    }

}
