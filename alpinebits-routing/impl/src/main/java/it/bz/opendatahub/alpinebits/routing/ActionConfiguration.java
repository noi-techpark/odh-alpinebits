// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.routing;

import it.bz.opendatahub.alpinebits.middleware.Middleware;

import java.util.Set;

/**
 * An AlpineBits action configuration, containing
 * information about the capabilities an action
 * supports and the {@link Middleware} associated
 * with the action.
 */
public class ActionConfiguration {

    private final Set<String> capabilitites;

    private final Middleware middleware;

    public ActionConfiguration(Set<String> capabilitites, Middleware middleware) {
        this.capabilitites = capabilitites;
        this.middleware = middleware;
    }

    public Set<String> getCapabilitites() {
        return capabilitites;
    }

    public Middleware getMiddleware() {
        return middleware;
    }

    @Override
    public String toString() {
        return "ActionConfiguration{" +
                ", capabilitites=" + capabilitites +
                ", middleware=" + middleware +
                '}';
    }
}
