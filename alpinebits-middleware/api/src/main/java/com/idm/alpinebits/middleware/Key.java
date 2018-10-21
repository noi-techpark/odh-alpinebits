/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.idm.alpinebits.middleware;

import java.util.Objects;

/**
 * This class implementats for {@link Context} keys.
 *
 * @param <T> type of the key
 */
public class Key<T> {
    private final String identifier;
    private final Class<T> type;

    private Key(String identifier, Class<T> type) {
        this.identifier = identifier;
        this.type = type;
    }

    /**
     * This method simplifies key building. It just wraps the
     * <code>new Key(String, Class)</code> call.
     * @param identifier key identifier
     * @param type key type
     * @return the key, created with the given parameters
     */
    public static <T> Key<T> key(String identifier, Class<T> type) {
        return new Key<>(identifier, type);
    }

    /**
     * Get the identifier of the key.
     *
     * @return identifier of the key
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Get the type of the key.
     *
     * @return type of the key
     */
    public Class<T> getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Key<?> key = (Key<?>) o;
        return Objects.equals(identifier, key.identifier) &&
                Objects.equals(type, key.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, type);
    }

}
