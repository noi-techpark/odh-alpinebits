// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking.utils;

import it.bz.opendatahub.alpinebits.routing.constants.Action;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Router implementation with configurable versions and actions, for test purpose only.
 */
public class RouterWithVersionsAndActions extends EmptyRouter {

    private final Map<String, Set<Action>> versionsWithActions;

    public RouterWithVersionsAndActions(Map<String, Set<Action>> versionsWithActions) {
        this.versionsWithActions = versionsWithActions;
    }

    @Override
    public Set<String> getVersions() {
        return versionsWithActions.keySet();
    }

    @Override
    public Optional<Set<Action>> getActionsForVersion(String version) {
        return Optional.ofNullable(versionsWithActions.get(version));
    }
}
