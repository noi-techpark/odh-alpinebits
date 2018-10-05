/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.Context;

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
            return Optional.of((T) result);
        }

        throw new ClassCastException(result.getClass() + " cannot be cast to " + clazz);

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
    public void handleException(Exception e) {
        throw new RuntimeException(e);
    }
}
