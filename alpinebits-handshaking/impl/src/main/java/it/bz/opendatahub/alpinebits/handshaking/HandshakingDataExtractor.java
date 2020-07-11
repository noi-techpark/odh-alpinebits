/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking;

import it.bz.opendatahub.alpinebits.handshaking.dto.HandshakingData;
import it.bz.opendatahub.alpinebits.handshaking.dto.SupportedAction;
import it.bz.opendatahub.alpinebits.handshaking.dto.SupportedVersion;
import it.bz.opendatahub.alpinebits.routing.Router;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class provides methods to extract {@link HandshakingData}.
 */
public final class HandshakingDataExtractor {

    private HandshakingDataExtractor() {
        // Empty
    }

    /**
     * Convert the {@link Router} configuration to a {@link HandshakingData}
     * instance, that can be used e.g. to compute the intersection with
     * other Handshaking data.
     *
     * @param router Convert this routers configuration to a {@link HandshakingData}
     *               instance. It must not be null.
     * @return A {@link HandshakingData} that represents the router configuration.
     * @throws IllegalArgumentException if the router is null.
     */
    public static HandshakingData fromRouter(Router router) {
        if (router == null) {
            throw new IllegalArgumentException("Router h1 must not be null");
        }

        if (router.getVersions() == null) {
            HandshakingData result = new HandshakingData();
            result.setVersions(Collections.emptySet());
            return result;
        }

        Set<SupportedVersion> versions = router.getVersions().stream()
                .map(version -> {
                    SupportedVersion supportedVersion = new SupportedVersion();
                    supportedVersion.setVersion(version);

                    router.getActionsForVersion(version).ifPresent(actions -> {
                        Set<SupportedAction> supportedActions = actions.stream()
                                .map(action -> {
                                    SupportedAction supportedAction = new SupportedAction();
                                    supportedAction.setAction(action.getName());

                                    router.getCapabilitiesForVersionAndActionName(version, action.getName())
                                            .ifPresent(supportedAction::setSupports);

                                    if (supportedAction.getSupports() == null) {
                                        supportedAction.setSupports(Collections.emptySet());
                                    }

                                    return supportedAction;
                                })
                                .collect(Collectors.toSet());
                        supportedVersion.setActions(supportedActions);
                    });

                    if (supportedVersion.getActions() == null) {
                        supportedVersion.setActions(Collections.emptySet());
                    }

                    return supportedVersion;
                })
                .collect(Collectors.toSet());

        HandshakingData handshakingData = new HandshakingData();
        handshakingData.setVersions(versions);
        return handshakingData;
    }
}
