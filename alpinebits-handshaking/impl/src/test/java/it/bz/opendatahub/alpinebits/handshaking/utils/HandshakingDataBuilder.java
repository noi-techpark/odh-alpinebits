// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking.utils;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.handshaking.dto.HandshakingData;
import it.bz.opendatahub.alpinebits.handshaking.dto.SupportedAction;
import it.bz.opendatahub.alpinebits.handshaking.dto.SupportedVersion;
import it.bz.opendatahub.alpinebits.routing.Router;

import java.util.Collections;
import java.util.Set;

/**
 * This class provides methods to build a {@link Router}.
 */
public final class HandshakingDataBuilder {

    public static final String DEFAULT_VERSION = AlpineBitsVersion.V_2018_10;
    public static final String DEFAULT_ACTION = "some action";
    public static final String DEFAULT_CAPABILITY = "some capability";

    private HandshakingDataBuilder() {
        // Empty
    }

    public static HandshakingData getDefaultHandshakingData() {
        return getHandshakingData(getDefaultSupportedVersion());
    }

    public static HandshakingData getHandshakingData(SupportedVersion supportedVersion) {
        HandshakingData hd = new HandshakingData();
        hd.setVersions(Collections.singleton(supportedVersion));
        return hd;
    }

    public static Set<SupportedVersion> getDefaultSupportedVersions() {
        return Collections.singleton(getDefaultSupportedVersion());
    }

    private static SupportedVersion getDefaultSupportedVersion() {
        return getSupportedVersion(DEFAULT_VERSION, getDefaultSupportedActions());
    }

    public static Set<SupportedVersion> getSupportedVersions(String version, Set<SupportedAction> actions) {
        return Collections.singleton(getSupportedVersion(version, actions));
    }

    public static SupportedVersion getSupportedVersion(String version, Set<SupportedAction> actions) {
        SupportedVersion supportedVersion = new SupportedVersion();
        supportedVersion.setVersion(version);
        supportedVersion.setActions(actions);
        return supportedVersion;
    }

    public static Set<SupportedAction> getDefaultSupportedActions() {
        return getSupportedActions(DEFAULT_ACTION, getDefaultCapabilities());
    }

    public static Set<SupportedAction> getSupportedActions(String action, Set<String> capabilities) {
        return Collections.singleton(getSupportedAction(action, capabilities));
    }

    public static SupportedAction getSupportedAction(String action, Set<String> capabilities) {
        SupportedAction supportedAction = new SupportedAction();
        supportedAction.setAction(action);
        supportedAction.setSupports(capabilities);
        return supportedAction;
    }

    public static Set<String> getDefaultCapabilities() {
        return Collections.singleton(DEFAULT_CAPABILITY);
    }
}
