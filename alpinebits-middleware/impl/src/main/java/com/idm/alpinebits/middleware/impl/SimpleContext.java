/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.idm.alpinebits.middleware.impl;

import com.idm.alpinebits.middleware.RequiredContextKeyMissingException;
import com.idm.alpinebits.middleware.Context;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple {@link Context} implementation, that uses a {@link ConcurrentHashMap}
 * to handleContext the context state.
 */
public class SimpleContext implements Context {

    private final Map<String, Object> state = new ConcurrentHashMap<>();

    @Override
    public <T> Optional<T> get(String key, Class<T> clazz) {
        if (!this.state.containsKey(key)) {
            return Optional.empty();
        }

        Object result = this.state.get(key);

        if (clazz.isAssignableFrom(result.getClass())) {
            // Suppressing the "unchecked" warning is ok, since an assignability check is done
            @SuppressWarnings("unchecked")
            Optional<T> castedResult = Optional.of((T) result);
            return castedResult;
        }

        throw new ClassCastException(result.getClass() + " cannot be cast to " + clazz);

    }

    @Override
    public <T> T getOrThrow(String key, Class<T> clazz) {
        Optional<T> value = this.get(key, clazz);
        if (!value.isPresent()) {
            throw new RequiredContextKeyMissingException("The required key " + key + " is missing in the context");
        }

        return value.get();
    }

    @Override
    public Context set(String key, Object value) {
        this.state.put(key, value);
        return this;
    }

    @Override
    public Object remove(String key) {
        return this.state.remove(key);
    }

    @Override
    public boolean contains(String key) {
        return this.state.containsKey(key);
    }

    @Override
    // Suppress warning "Generic exceptions should never be thrown", since in this place
    // a generic runtime exception should be thrown
    @SuppressWarnings("squid:S00112")
    public void handleException(Exception e) {
        throw new RuntimeException(e);
    }
}
