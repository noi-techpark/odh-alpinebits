// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking;

import it.bz.opendatahub.alpinebits.common.AlpineBitsVersionChecker;
import it.bz.opendatahub.alpinebits.handshaking.dto.HandshakingData;
import it.bz.opendatahub.alpinebits.handshaking.dto.SupportedAction;
import it.bz.opendatahub.alpinebits.handshaking.dto.SupportedVersion;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class defines methods to compute the intersections for data related to the
 * AlpineBits Handshaking action.
 */
public final class IntersectionComputer {

    private IntersectionComputer() {
        // Empty
    }

    /**
     * Compute the intersection between two {@link HandshakingData} instances, as
     * defined by the AlpineBits standard.
     * <p>
     * The result contains also information about AlpineBits legacy versions, as
     * defined in the AlpineBits 2018-10 standard, i.e. the result contains version
     * information for legacy versions without the actions. See
     * {@link AlpineBitsVersionChecker} for a list of all legacy versions.
     * <p>
     * Please note, that the implemented algorithm returns empty sets instead of
     * null values, e.g. for the version-set when no versions match. This is best
     * considered best practice when working with collections. A JSON serializer may
     * chose to not serialize empty sets.
     *
     * @param h1 This HandshakingData will be intersected with h2. It must not be null.
     * @param h2 This HandshakingData will be intersected with h1. It must not be null.
     * @return The intersection of h1 and h2, as defined by the AlpineBits standard.
     * This method throws an {@link IllegalArgumentException} if h1 or h2 are null.
     * @throws IllegalArgumentException If h1 or h2 are null.
     */
    public static HandshakingData intersectHandshakingData(HandshakingData h1, HandshakingData h2) {
        if (h1 == null) {
            throw new IllegalArgumentException("HandshakingData h1 must not be null");
        }
        if (h2 == null) {
            throw new IllegalArgumentException("HandshakingData h2 must not be null");
        }

        Set<SupportedVersion> supportedVersions = intersectVersions(h1.getVersions(), h2.getVersions());

        HandshakingData result = new HandshakingData();
        result.setVersions(supportedVersions);
        return result;
    }

    /**
     * Compute the intersection between two version sets, as defined by the
     * AlpineBits standard.
     * <p>
     * Please note, that the implemented algorithm returns empty sets instead
     * of null values.
     *
     * @param versions1 This set of versions will be intersected with versions2.
     * @param versions2 This set of versions will be intersected with versions1.
     * @return The intersection of versions1 and versions2, as defined by the
     * AlpineBits standard. An empty set will be returned, if versions1 or
     * versions2 are null.
     */
    public static Set<SupportedVersion> intersectVersions(Set<SupportedVersion> versions1, Set<SupportedVersion> versions2) {
        if (versions1 == null || versions1.isEmpty() || versions2 == null || versions2.isEmpty()) {
            return Collections.emptySet();
        }

        Map<String, SupportedVersion> h2VersionIndex = versions2.stream()
                .collect(Collectors.toMap(SupportedVersion::getVersion, Function.identity()));

        return versions1.stream()
                .filter(supportedVersion -> h2VersionIndex.containsKey(supportedVersion.getVersion()))
                .map(h1Version -> {
                    String version = h1Version.getVersion();
                    SupportedVersion h2Version = h2VersionIndex.get(version);

                    SupportedVersion result = new SupportedVersion();
                    result.setVersion(version);

                    // Compute actions intersection for non-legacy AlpineBits versions (2018-10 going on)
                    if (!AlpineBitsVersionChecker.isLegacyVersion(version)) {
                        Set<SupportedAction> actionsIntersection = intersectActions(h1Version.getActions(), h2Version.getActions());
                        result.setActions(actionsIntersection);
                    }

                    return result;
                })
                .collect(Collectors.toSet());
    }

    /**
     * Compute the intersection between two action sets, as defined by the
     * AlpineBits standard.
     * <p>
     * Please note, that the implemented algorithm returns empty sets instead
     * of null values.
     *
     * @param actions1 This set of actions will be intersected with actions2.
     * @param actions2 This set of actions will be intersected with actions1.
     * @return The intersection of actions1 and action2, as defined by the
     * AlpineBits standard. An empty set will be returned, if actions1 or
     * actions2 are null.
     */
    public static Set<SupportedAction> intersectActions(Set<SupportedAction> actions1, Set<SupportedAction> actions2) {
        if (actions1 == null || actions1.isEmpty() || actions2 == null || actions2.isEmpty()) {
            return Collections.emptySet();
        }

        Map<String, SupportedAction> action2Index = actions2.stream()
                .collect(Collectors.toMap(SupportedAction::getAction, Function.identity()));

        return actions1.stream()
                .filter(supportedAction -> action2Index.containsKey(supportedAction.getAction()))
                .map(h1Action -> {
                    String action = h1Action.getAction();
                    SupportedAction h2Action = action2Index.get(action);

                    SupportedAction result = new SupportedAction();
                    result.setAction(action);
                    Set<String> capabilities = intersectCapabilities(h1Action.getSupports(), h2Action.getSupports());
                    result.setSupports(capabilities);
                    return result;
                })
                .collect(Collectors.toSet());
    }

    /**
     * Compute the intersection between two capabilities sets, as defined by
     * the AlpineBits standard.
     * <p>
     * Please note, that the implemented algorithm returns empty sets instead
     * of null values.
     *
     * @param cap1 This set of capabilities will be intersected with cap2.
     * @param cap2 This set of capabilities will be intersected with cap1.
     * @return The intersection of cap1 and cap2, as defined by the AlpineBits
     * standard. An empty set will be returned, if cap1 or cap2 are null.
     */
    public static Set<String> intersectCapabilities(Set<String> cap1, Set<String> cap2) {
        if (cap1 == null || cap1.isEmpty() || cap2 == null || cap2.isEmpty()) {
            return Collections.emptySet();
        }
        Set<String> tmp = new HashSet<>(cap1);
        tmp.retainAll(cap2);
        return tmp;
    }

}
