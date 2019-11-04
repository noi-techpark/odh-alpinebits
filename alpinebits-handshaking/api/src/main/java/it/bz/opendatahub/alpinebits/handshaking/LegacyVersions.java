/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class contains a list of all AlpineBits legacy versions, that don't
 * get full Handshaking support.
 *
 * As of AlpineBits 2018-10, a Handshaking implementation can choose if
 * legacy versions should be part of the result. If they are part of the
 * result, no "actions" array must be returned for legacy versions. The
 * handshaking of the legacy versions must happen in subsequent requests
 * following the legacy rules (i.e. Housekeeping actions).
 */
public final class LegacyVersions {

    public static final Set<String> LEGACY_VERSIONS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            AlpineBitsVersion.V_2017_10,
            AlpineBitsVersion.V_2015_07B,
            AlpineBitsVersion.V_2014_04,
            AlpineBitsVersion.V_2013_04,
            AlpineBitsVersion.V_2012_05B,
            AlpineBitsVersion.V_2012_05,
            AlpineBitsVersion.V_2011_11,
            AlpineBitsVersion.V_2011_10,
            AlpineBitsVersion.V_2011_09,
            AlpineBitsVersion.V_2010_10,
            AlpineBitsVersion.V_2010_08
    )));

    private LegacyVersions() {
        // Empty
    }

    public static boolean isLegacy(String version) {
        return LEGACY_VERSIONS.contains(version);
    }

}
