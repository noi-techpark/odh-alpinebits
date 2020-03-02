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
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static it.bz.opendatahub.alpinebits.handshaking.utils.HandshakingDataBuilder.*;
import static org.testng.Assert.*;

/**
 * Test cases for {@link IntersectionComputer}.
 */
public class IntersectionComputerTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIntersectHandshakingData_ShouldThrow_IfFirstHandshakingDataIsNull() {
        HandshakingData hd = new HandshakingData();
        IntersectionComputer.intersectHandshakingData(null, hd);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIntersectHandshakingData_ShouldThrow_IfSecondHandshakingDataIsNull() {
        HandshakingData hd = new HandshakingData();
        IntersectionComputer.intersectHandshakingData(hd, null);
    }

    @Test
    public void testIntersectHandshakingData_ShouldReturnEmptyHandshakingData_IfNoVersionsMatch() {
        SupportedVersion version1 = getSupportedVersion(DEFAULT_VERSION, getDefaultSupportedActions());
        SupportedVersion version2 = getSupportedVersion(DEFAULT_VERSION + 1, getDefaultSupportedActions());
        HandshakingData hd1 = getHandshakingData(version1);
        HandshakingData hd2 = getHandshakingData(version2);
        HandshakingData result = IntersectionComputer.intersectHandshakingData(hd1, hd2);
        assertTrue(result.getVersions().isEmpty());
    }

    @Test
    public void testIntersectHandshakingData_ShouldReturnIntersection_IfVersionsMatch() {
        HandshakingData hd1 = getDefaultHandshakingData();
        HandshakingData hd2 = getDefaultHandshakingData();
        HandshakingData result = IntersectionComputer.intersectHandshakingData(hd1, hd2);

        assertEquals(result.getVersions().size(), 1);
        result.getVersions().forEach(version -> {
            assertEquals(version.getVersion(), DEFAULT_VERSION);

            assertEquals(version.getActions().size(), 1);
            version.getActions().forEach(action -> {
                assertEquals(action.getAction(), DEFAULT_ACTION);

                assertEquals(action.getSupports().size(), 1);
                action.getSupports().forEach(capability -> assertEquals(capability, DEFAULT_CAPABILITY));
            });
        });
    }

    @Test
    public void testIntersectVersions_ShouldReturnEmptySet_IfFirstVersionSetIsNull() {
        Set<SupportedVersion> versions = getDefaultSupportedVersions();
        Set<SupportedVersion> result = IntersectionComputer.intersectVersions(null, versions);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIntersectVersions_ShouldReturnEmptySet_IfFirstVersionSetIsEmpty() {
        Set<SupportedVersion> version1 = Collections.emptySet();
        Set<SupportedVersion> version2 = getDefaultSupportedVersions();
        Set<SupportedVersion> result = IntersectionComputer.intersectVersions(version1, version2);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIntersectVersions_ShouldReturnEmptySet_IfSecondVersionSetIsNull() {
        Set<SupportedVersion> versions = getDefaultSupportedVersions();
        Set<SupportedVersion> result = IntersectionComputer.intersectVersions(versions, null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIntersectVersions_ShouldReturnEmptySet_IfSecondVersionSetIsEmpty() {
        Set<SupportedVersion> version1 = getDefaultSupportedVersions();
        Set<SupportedVersion> version2 = Collections.emptySet();
        Set<SupportedVersion> result = IntersectionComputer.intersectVersions(version1, version2);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIntersectVersions_ShouldReturnEmptySet_IfNoVersionsMatch() {
        Set<SupportedVersion> version1 = getSupportedVersions(DEFAULT_VERSION, getDefaultSupportedActions());
        Set<SupportedVersion> version2 = getSupportedVersions(DEFAULT_VERSION + 1, getDefaultSupportedActions());
        Set<SupportedVersion> result = IntersectionComputer.intersectVersions(version1, version2);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIntersectVersions_ShouldReturnIntersection_IfVersionsMatch() {
        String s1 = "cap1";
        String s2 = "cap2";
        String s3 = "cap3";

        Set<String> cap1 = new HashSet<>(Arrays.asList(s1, s2));
        Set<String> cap2 = new HashSet<>(Arrays.asList(s2, s3));

        Set<SupportedAction> action1 = new HashSet<>(Arrays.asList(
                getSupportedAction(DEFAULT_ACTION, cap1),
                getSupportedAction(DEFAULT_ACTION + 1, cap1)
        ));
        Set<SupportedAction> action2 = new HashSet<>(Arrays.asList(
                getSupportedAction(DEFAULT_ACTION, cap2),
                getSupportedAction(DEFAULT_ACTION + 2, cap2)
        ));

        Set<SupportedVersion> version1 = getSupportedVersions(DEFAULT_VERSION, action1);
        Set<SupportedVersion> version2 = getSupportedVersions(DEFAULT_VERSION, action2);
        Set<SupportedVersion> result = IntersectionComputer.intersectVersions(version1, version2);
        assertEquals(result.size(), 1);
        result.forEach(version -> {
            assertEquals(version.getVersion(), DEFAULT_VERSION);

            Set<SupportedAction> actions = version.getActions();
            assertEquals(actions.size(), 1);
            actions.forEach(action -> {
                assertEquals(action.getAction(), DEFAULT_ACTION);
                assertEquals(action.getSupports(), Collections.singleton(s2));
            });
        });
    }

    @Test
    public void testIntersectVersions_ShouldHandleLegacyVersions() {
        String s1 = "cap1";
        String s2 = "cap2";
        String s3 = "cap3";

        Set<String> cap1 = new HashSet<>(Arrays.asList(s1, s2));
        Set<String> cap2 = new HashSet<>(Arrays.asList(s2, s3));

        Set<SupportedAction> action1 = new HashSet<>(Arrays.asList(
                getSupportedAction(DEFAULT_ACTION, cap1),
                getSupportedAction(DEFAULT_ACTION + 1, cap1)
        ));
        Set<SupportedAction> action2 = new HashSet<>(Arrays.asList(
                getSupportedAction(DEFAULT_ACTION, cap2),
                getSupportedAction(DEFAULT_ACTION + 2, cap2)
        ));

        Set<SupportedVersion> version1 = getSupportedVersions(AlpineBitsVersion.V_2017_10, action1);
        Set<SupportedVersion> version2 = getSupportedVersions(AlpineBitsVersion.V_2017_10, action2);
        Set<SupportedVersion> result = IntersectionComputer.intersectVersions(version1, version2);
        assertEquals(result.size(), 1);
        result.forEach(version -> {
            assertEquals(version.getVersion(), AlpineBitsVersion.V_2017_10);
            assertNull(version.getActions());
        });
    }

    @Test
    public void testIntersectActions_ShouldReturnEmptySet_IfFirstActionSetIsNull() {
        Set<SupportedAction> action = getDefaultSupportedActions();
        Set<SupportedAction> result = IntersectionComputer.intersectActions(null, action);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIntersectActions_ShouldReturnEmptySet_IfFirstActionSetIsEmpty() {
        Set<SupportedAction> action1 = Collections.emptySet();
        Set<SupportedAction> action2 = getDefaultSupportedActions();
        Set<SupportedAction> result = IntersectionComputer.intersectActions(action1, action2);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIntersectActions_ShouldReturnEmptySet_IfSecondVersionSetIsNull() {
        Set<SupportedAction> action = getDefaultSupportedActions();
        Set<SupportedAction> result = IntersectionComputer.intersectActions(action, null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIntersectActions_ShouldReturnEmptySet_IfSecondActionSetIsEmpty() {
        Set<SupportedAction> action1 = getDefaultSupportedActions();
        Set<SupportedAction> action2 = Collections.emptySet();
        Set<SupportedAction> result = IntersectionComputer.intersectActions(action1, action2);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIntersectActions_ShouldReturnEmptySet_IfNoActionsMatch() {
        Set<SupportedAction> action1 = getSupportedActions(DEFAULT_ACTION, getDefaultCapabilities());
        Set<SupportedAction> action2 = getSupportedActions(DEFAULT_ACTION + 1, getDefaultCapabilities());
        Set<SupportedAction> result = IntersectionComputer.intersectActions(action1, action2);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIntersectActions_ShouldReturnIntersection_IfActionsMatch() {
        String s1 = "cap1";
        String s2 = "cap2";
        String s3 = "cap3";

        Set<String> cap1 = new HashSet<>(Arrays.asList(s1, s2));
        Set<String> cap2 = new HashSet<>(Arrays.asList(s2, s3));

        Set<SupportedAction> action1 = new HashSet<>(Arrays.asList(
                getSupportedAction(DEFAULT_ACTION, cap1),
                getSupportedAction(DEFAULT_ACTION + 1, cap1)
        ));
        Set<SupportedAction> action2 = new HashSet<>(Arrays.asList(
                getSupportedAction(DEFAULT_ACTION, cap2),
                getSupportedAction(DEFAULT_ACTION + 2, cap2)
        ));
        Set<SupportedAction> result = IntersectionComputer.intersectActions(action1, action2);
        assertEquals(result.size(), 1);
        result.forEach(action -> {
            assertEquals(action.getAction(), DEFAULT_ACTION);
            assertEquals(action.getSupports(), Collections.singleton(s2));
        });
    }

    @Test
    public void testIntersectCapabilities_ShouldReturnEmptySet_IfFirstCapabilitySetIsNull() {
        Set<String> cap = getDefaultCapabilities();
        Set<String> result = IntersectionComputer.intersectCapabilities(null, cap);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIntersectCapabilities_ShouldReturnEmptySet_IfFirstCapabilitySetIsEmpty() {
        Set<String> cap1 = Collections.emptySet();
        Set<String> cap2 = getDefaultCapabilities();
        Set<String> result = IntersectionComputer.intersectCapabilities(cap1, cap2);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIntersectCapabilities_ShouldReturnEmptySet_IfSecondCapabilitySetIsNull() {
        Set<String> cap = getDefaultCapabilities();
        Set<String> result = IntersectionComputer.intersectCapabilities(cap, null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIntersectCapabilities_ShouldReturnEmptySet_IfSecondActionCapabilitySetIsEmpty() {
        Set<String> cap1 = getDefaultCapabilities();
        Set<String> cap2 = Collections.emptySet();
        Set<String> result = IntersectionComputer.intersectCapabilities(cap1, cap2);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIntersectCapabilities_ShouldReturnEmptySet_IfNoCapabilitiesMatch() {
        Set<String> cap1 = Collections.singleton("cap1");
        Set<String> cap2 = Collections.singleton("cap2");
        Set<String> result = IntersectionComputer.intersectCapabilities(cap1, cap2);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIntersectCapabilities_ShouldReturnIntersection_IfCapabilitiesMatch() {
        String s1 = "cap1";
        String s2 = "cap2";
        String s3 = "cap3";

        Set<String> cap1 = new HashSet<>(Arrays.asList(s1, s2));
        Set<String> cap2 = new HashSet<>(Arrays.asList(s2, s3));

        Set<String> result = IntersectionComputer.intersectCapabilities(cap1, cap2);
        assertEquals(result.size(), 1);
        assertEquals(result, new HashSet<>(Collections.singletonList(s2)));
    }

}