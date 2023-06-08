// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.middleware;

import java.util.Optional;

/**
 * Each {@link Middleware#handleContext(Context, MiddlewareChain)} method is
 * provided with a {@link Context} object that is shared by the middlewares.
 * <p>
 * The context provides methods to store and retrieve data and can
 * thus be used for data exchange between the middlewares.
 * <p>
 * The context implements the {@link ExceptionHandler} interface.
 * Context implementations should handle context exceptions that occur while
 * the middlewares are executed. This way, the exception handling
 * is part of the context and can be specialized if needed.
 */
public interface Context extends ExceptionHandler {

    /**
     * Returns the value wrapped inside an {@link Optional} to which the specified
     * {@link Key} is mapped, or an empty Optional if this {@link Context}
     * contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return {@link Optional} containing the value for the given {@link Key},
     * or an empty Optional if no value was found
     */
    <T> Optional<T> get(Key<T> key);

    /**
     * Returns the value to which the specified {@link Key} is mapped, or
     * throws a {@link RequiredContextKeyMissingException} if this {@link Context}
     * contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value for the given {@link Key}
     * @throws RequiredContextKeyMissingException if no value was found for the
     *                                            given {@link Key}
     */
    <T> T getOrThrow(Key<T> key);

    /**
     * Associates the specified value with the specified {@link Key}
     * in this {@link Context}. If the context previously contained a mapping
     * for the key, the old value is replaced by the specified value.
     *
     * @param key {@link Key} with which the specified value
     *                   is to be associated
     * @return the previous value associated with {@link Key}, or
     * <code>null</code> if there was no mapping for key
     */
    <T> T put(Key<T> key, T value);

    /**
     * Removes the mapping for a {@link Key} from this {@link Context}
     * if it is present.
     * <p>
     * Returns the value to which this context previously associated the key,
     * or <code>null</code> if the context contained no mapping for the key.
     * <p>
     * <p>The context will not contain a mapping for the specified key once the
     * call returns.
     *
     * @param key {@link Key} whose mapping is to be removed
     *                   from the {@link Context}
     * @return the previous value associated with {@link Key}, or
     * <code>null</code> if there was no mapping for key
     */
    <T> T remove(Key<T> key);

    /**
     * Returns <code>true</code> if this {@link Context} contains a mapping
     * for the specified key and <code>false</code> otherwise.
     *
     * @param key {@link Key} whose presence in this
     *                   {@link Context} is to be tested
     * @return <code>true</code> if this map contains a mapping for the
     * specified key
     */
    <T> boolean contains(Key<T> key);

}
