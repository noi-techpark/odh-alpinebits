/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.routing;

import it.bz.idm.alpinebits.middleware.Middleware;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An AlpineBits version configuration, containing
 * information about the the actions defined.
 */
public class VersionConfiguration {

    private Map<String, ActionConfiguration> actions = new HashMap<>();

    public void addActionConfiguration(String action, ActionConfiguration actionConfiguration) {
        this.actions.put(action, actionConfiguration);
    }

    public Map<String, ActionConfiguration> getActions() {
        return actions;
    }

    public Set<String> getCapabilities() {
        return this.actions.values().stream()
                .map(ActionConfiguration::getCapabilitites)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public Middleware findMiddlewareForAction(String action) {
        return this.actions.containsKey(action) ? this.actions.get(action).getMiddleware() : null;
    }

    @Override
    public String toString() {
        return "VersionConfiguration{" +
                "actions=" + actions +
                '}';
    }
}
