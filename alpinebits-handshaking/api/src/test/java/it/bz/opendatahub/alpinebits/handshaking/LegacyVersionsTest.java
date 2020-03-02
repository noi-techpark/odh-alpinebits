/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Tests for {@link LegacyVersions}.
 */
public class LegacyVersionsTest {

    @Test
    public void testIsLegacy_ShouldReturnTrue_OnLegacyVersion() {
        LegacyVersions.LEGACY_VERSIONS.forEach(version -> assertTrue(LegacyVersions.isLegacy(version)));
    }

    @Test
    public void testIsLegacy_ShouldReturnTrue_WhenNotLegacyVersion() {
        assertFalse(LegacyVersions.isLegacy(AlpineBitsVersion.V_2018_10));
    }

}