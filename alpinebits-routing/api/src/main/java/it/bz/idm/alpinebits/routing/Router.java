/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing;

import it.bz.idm.alpinebits.middleware.Middleware;

import java.util.Collection;
import java.util.Optional;

/**
 * A router provides functionality to return a {@link Middleware} based
 * on AlpineBits <code>version</code> and <code>action</code>. It provides
 * also functionality to inspect the configured routes.
 */
public interface Router {

    /**
     * Find a {@link Middleware} based on AlpineBits <code>version</code> and
     * <code>action</code>.
     * <p>
     * This method returns an {@link Optional} that is not empty, if the router
     * contains a middleware that was configured for the given <code>version</code>
     * and <code>action</code>. If no match was found, an empty optional is returned.
     * <p>
     * If the <code>version</code> or <code>action</code> parameters are null,
     * an {@link IllegalArgumentException} is thrown.
     *
     * @param version the AlpineBits version, for which a middleware is searched
     * @param action  the AlpineBits action, for which a middleware is searched
     * @return an {@link Optional} that wraps the search result
     * @throws IllegalArgumentException if <code>version</code> or
     *                                  <code>action</code> is null
     */
    Optional<Middleware> findMiddleware(String version, String action);

    /**
     * Return a list of configured AlpineBits versions.
     * <p>
     * The result returns a list of every configured version, no matter if
     * actions are defined for that version. That means that a version
     * with no action is also part of the result.
     *
     * @return a {@link Collection} of configured versions
     */
    Collection<String> getVersions();

    /**
     * Return an {@link Optional} wrapping a {@link Collection} of configured
     * AlpineBits actions for the given <code>version</code>.
     * <p>
     * If the version is not configured, the optional is empty. If there are no
     * actions defined for the given <code>version</code>, an empty collection
     * is returned.
     * <p>
     * If the <code>version</code> is null, an {@link IllegalArgumentException}
     * is thrown.
     *
     * @param version the <code>version</code> for which to return the actions
     * @return an {@link Optional} wrapping a {@link Collection} of configured
     * AlpineBits actions for the given <code>version</code>. The optional is
     * empty, if there exists no configuration for the given <code>version</code>.
     * The optional returns an empty collection, if the <code>version</code>
     * is configured, but no actions are defined for it. If there are actions
     * defined, the collection contains them.
     * @throws IllegalArgumentException if <code>version</code> is null
     */
    Optional<Collection<String>> getActionsForVersion(String version);

    /**
     * Check if there is a {@link Middleware} for the given <code>version</code>
     * and <code>action</code>.
     *
     * @param version the AlpineBits version to check
     * @param action  the AlpineBits action to check
     * @return <code>true</code> if there exists a middleware for the given
     * parameters, <code>false</code> otherwise
     * @throws IllegalArgumentException if <code>version</code> or
     *                                  <code>action</code> is null
     */
    boolean isActionDefined(String version, String action);

    /**
     * Check if there is a {@link Middleware} configured for the given
     * <code>version</code> and <code>action</code>.
     *
     * @param version the AlpineBits version, for which a middleware is searched
     * @param action  the AlpineBits action, for which a middleware is searched
     * @return <code>true</code> if a middleware was found, <code>false</code>
     * otherwise
     * @throws IllegalArgumentException if <code>version</code> or
     *                                  <code>action</code> is null
     */
    boolean isRouteDefined(String version, String action);

    /**
     * Check if there is a configuration for the given <code>version</code>.
     *
     * @param version the AlpineBits version to check
     * @return <code>true</code> if the <code>version</code> was found,
     * <code>false</code> otherwise
     */
    boolean isVersionDefined(String version);

}
