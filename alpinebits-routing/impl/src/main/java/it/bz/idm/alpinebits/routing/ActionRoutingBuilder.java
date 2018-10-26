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
 * to {@link Middleware}. It's mainly used together with
 * {@link DefaultRouter.Builder} and {@link VersionRoutingBuilder}.
 * <p>
 * See {@link DefaultRouter.Builder} for further information on
 * how to build a {@link DefaultRouter}.
 */
public final class ActionRoutingBuilder {

    private final Map<String, Middleware> configuredActions = new ConcurrentHashMap<>();

    private final VersionRoutingBuilder parentBuilder;
    private final Consumer<Map<String, Middleware>> callback;

    private ActionRoutingBuilder(
            VersionRoutingBuilder parentBuilder,
            Consumer<Map<String, Middleware>> callback
    ) {
        this.parentBuilder = parentBuilder;
        this.callback = callback;
    }

    /**
     * Return a new {@link ActionRoutingBuilder} instance.
     *
     * @param parentBuilder the parent builder is returned, when the
     *                      {@link ActionRoutingBuilder#done()} is called,
     *                      providing a nested builder chain
     * @param callback      the callback is invoked when the
     *                      {@link ActionRoutingBuilder#done()} is called.
     *                      The current configured actions are used as
     *                      parameters
     * @return a new {@link ActionRoutingBuilder} instance
     */
    public static ActionRoutingBuilder newBuilder(
            VersionRoutingBuilder parentBuilder,
            Consumer<Map<String, Middleware>> callback
    ) {
        if (parentBuilder == null) {
            throw new IllegalArgumentException("The parentBuilder must not be null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("The callback must not be null");
        }
        return new ActionRoutingBuilder(parentBuilder, callback);
    }

    /**
     * Configure a <code>action</code> to {@link Middleware} assignment for the
     * current {@link ActionRoutingBuilder} instance.
     *
     * @param action     the <code>action</code> that links to the <code>middleware</code>
     * @param middleware this <code>middleware</code> is linked to the given
     *                   <code>action</code>
     * @return the current {@link ActionRoutingBuilder} instance
     * @throws IllegalArgumentException if the <code>action</code> or
     *                                  <code>middleware</code> is null
     */
    public ActionRoutingBuilder addMiddleware(String action, Middleware middleware) {
        if (action == null) {
            throw new IllegalArgumentException("The action must not be null");
        }
        if (middleware == null) {
            throw new IllegalArgumentException("The middleware must not be null");
        }

        this.configuredActions.put(action, middleware);
        return this;
    }

    /**
     * This method invokes the callback, provided in
     * {@link ActionRoutingBuilder#newBuilder(VersionRoutingBuilder, Consumer)}.
     * The it returns the parent {@link VersionRoutingBuilder} to continue
     * route building.
     *
     * @return the parent {@link VersionRoutingBuilder}
     */
    public VersionRoutingBuilder done() {
        this.callback.accept(this.configuredActions);
        return parentBuilder;
    }

}
