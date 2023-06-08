// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.handshaking.dto.HandshakingData;
import it.bz.opendatahub.alpinebits.handshaking.dto.SupportedAction;
import it.bz.opendatahub.alpinebits.handshaking.dto.SupportedVersion;
import it.bz.opendatahub.alpinebits.handshaking.utils.EmptyRouter;
import it.bz.opendatahub.alpinebits.handshaking.utils.RouterBuilder;
import it.bz.opendatahub.alpinebits.handshaking.utils.RouterWithVersionsAndActions;
import it.bz.opendatahub.alpinebits.routing.Router;
import it.bz.opendatahub.alpinebits.routing.constants.Action;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests for {@link HandshakingDataExtractor}.
 */
public class HandshakingDataExtractorTest {

    private static final String DEFAULT_VERSION = AlpineBitsVersion.V_2018_10;
    private static final Action DEFAULT_ACTION = Action.of("action request param", "action name");
    private static final String DEFAULT_CAPABILITY = "some capability";
    private static final Router DEFAULT_ROUTER = RouterBuilder.buildRouter(
            DEFAULT_VERSION,
            DEFAULT_ACTION,
            DEFAULT_CAPABILITY
    );

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFromRouter_ShouldThrow_IfRouterIsNull() {
        HandshakingDataExtractor.fromRouter(null);
    }

    @Test
    public void testFromRouter_ShouldReturnEmptyHandshakingData_IfRouterVersionsIsNull() {
        HandshakingData hd = HandshakingDataExtractor.fromRouter(new EmptyRouter());
        assertTrue(hd.getVersions().isEmpty());
    }

    @Test
    public void testFromRouter_ShouldReturnHandshakingData_IfRouterIsDefinedWithVersionsOnly() {
        Router router = buildRouterWithVersionsAndActions(DEFAULT_VERSION, null);

        HandshakingData hd = HandshakingDataExtractor.fromRouter(router);
        Set<SupportedVersion> versions = hd.getVersions();

        assertEquals(versions.size(), 1);
        versions.forEach(version -> {
            assertEquals(version.getVersion(), DEFAULT_VERSION);

            Set<SupportedAction> actions = version.getActions();
            assertTrue(actions.isEmpty());
        });
    }

    @Test
    public void testFromRouter_ShouldReturnHandshakingData_IfRouterIsDefinedWithVersionsAndActions() {
        Router router = buildRouterWithVersionsAndActions();

        HandshakingData hd = HandshakingDataExtractor.fromRouter(router);
        Set<SupportedVersion> versions = hd.getVersions();

        assertEquals(versions.size(), 1);
        versions.forEach(version -> {
            assertEquals(version.getVersion(), DEFAULT_VERSION);

            Set<SupportedAction> actions = version.getActions();
            assertEquals(actions.size(), 1);
            actions.forEach(action -> {
                assertEquals(action.getAction(), DEFAULT_ACTION.getName());

                Set<String> capabilities = action.getSupports();
                assertTrue(capabilities.isEmpty());
            });
        });
    }

    @Test
    public void testFromRouter_ShouldReturnHandshakingData_IfRouterIsDefinedWithVersionsAndActionsAndCapabilities() {
        HandshakingData hd = HandshakingDataExtractor.fromRouter(DEFAULT_ROUTER);
        Set<SupportedVersion> versions = hd.getVersions();

        assertEquals(versions.size(), 1);
        versions.forEach(version -> {
            assertEquals(version.getVersion(), DEFAULT_VERSION);

            Set<SupportedAction> actions = version.getActions();
            assertEquals(actions.size(), 1);
            actions.forEach(action -> {
                assertEquals(action.getAction(), DEFAULT_ACTION.getName());

                Set<String> capabilities = action.getSupports();
                assertEquals(capabilities, Collections.singleton(DEFAULT_CAPABILITY));
            });
        });
    }

    private Router buildRouterWithVersionsAndActions() {
        return buildRouterWithVersionsAndActions(DEFAULT_VERSION, Collections.singleton(DEFAULT_ACTION));
    }

    private Router buildRouterWithVersionsAndActions(String version, Set<Action> actions) {
        Map<String, Set<Action>> versionsAndActions = new HashMap<>();
        versionsAndActions.put(version, actions);
        return new RouterWithVersionsAndActions(versionsAndActions);
    }
}