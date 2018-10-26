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
     * Return the server AlpineBits version, according to AlpineBits
     * best practices (see AlpineBits specification, Housekeeping
     * actions -> Implementation tips and best practices):
     * <ul>
     *     <li>
     *         Client: the​ client​ queries​ the​ server​ version, sending​
     *         a header​ with​ the​ highest​ version​ it supports.
     *     </li>
     *     <li>
     *         Server: if the server supports this same version, it
     *         answers with this version and the negotiation​ terminates
     *         successfully; otherwise the server answers with the highest
     *         version it supports.
     *     </li>
     *     <li>
     *         Client: if the client recognizes the server version, it
     *         starts using the server version and the negotiation terminates
     *         successfully; otherwise no communication is possible, since
     *         the two parties don’t share a common version.
     *     </li>
     * </ul>
     * The consequence is, that the server may respond with a version, that
     * is different from the version requested by the client.
     * <p>
     * A version is returned, no matter if actions are defined for that
     * version, as long as the version itself is configured.
     *
     * @param requestedVersion the version requested by the client
     * @return the server version, according to AlpineBits best practices
     */
    String getVersion(String requestedVersion);

    /**
     * Return a list of configured AlpineBits versions.
     * <p>
     * The result returns the configured version, no matter if actions
     * are defined for those versions. That means that a version with
     * no action is also part of the result.
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
