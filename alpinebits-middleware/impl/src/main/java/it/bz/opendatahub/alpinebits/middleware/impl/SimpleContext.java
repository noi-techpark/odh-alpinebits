/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.middleware.impl;

import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.RequiredContextKeyMissingException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple {@link Context} implementation, that uses a
 * {@link ConcurrentHashMap} to store the context values.
 */
public class SimpleContext implements Context {

    private final Map<Key<?>, Object> map = new ConcurrentHashMap<>();

    @Override
    public <T> Optional<T> get(Key<T> key) {
        T value = key.getType().cast(this.map.get(key));
        return Optional.ofNullable(value);
    }

    @Override
    public <T> T getOrThrow(Key<T> key) {
        Optional<T> value = this.get(key);

        if (value.isPresent()) {
            return value.get();
        }

        throw new RequiredContextKeyMissingException(
                "The required key " + key + " is missing in the context"
        );
    }

    @Override
    public <T> T put(Key<T> key, T value) {
        Object previousValue = this.map.put(key, value);
        return key.getType().cast(previousValue);
    }

    @Override
    public <T> T remove(Key<T> key) {
        Object removedValue = this.map.remove(key);
        return key.getType().cast(removedValue);
    }

    @Override
    public <T> boolean contains(Key<T> key) {
        return this.map.containsKey(key);
    }

    @Override
    // Suppress warning "Generic exceptions should never be thrown", since in this place
    // a generic runtime exception should be thrown
    @SuppressWarnings("squid:S00112")
    public void handleException(Exception e) {
        throw new RuntimeException(e);
    }
}
