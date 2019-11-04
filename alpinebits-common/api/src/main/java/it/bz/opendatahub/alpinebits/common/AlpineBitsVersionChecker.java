/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;

/**
 * This class implements facilities to check AlpineBits versions
 * for different criteria.
 */
public class AlpineBitsVersionChecker {

    /**
     * This method checks if the provided AlpineBits Version is a legacy
     * version as defined in AlpineBits 2018-10 going on.
     *
     * @param alpineBitsVersion The AlpineBits version to check.
     * @return <code>true</code> if the provided version is a legacy
     * version, <code>false</code> otherwise.
     */
    // Disable CheckStyle rule "BooleanExpressionComplexity" for this method,
    // which forbids boolean expressions with more than 7 conditions
    @SuppressWarnings("checkstyle:booleanexpressioncomplexity")
    public static boolean isLegacyVersion(String alpineBitsVersion) {
        return AlpineBitsVersion.V_2017_10.equals(alpineBitsVersion)
                || AlpineBitsVersion.V_2015_07B.equals(alpineBitsVersion)
                || AlpineBitsVersion.V_2015_07.equals(alpineBitsVersion)
                || AlpineBitsVersion.V_2014_04.equals(alpineBitsVersion)
                || AlpineBitsVersion.V_2013_04.equals(alpineBitsVersion)
                || AlpineBitsVersion.V_2012_05B.equals(alpineBitsVersion)
                || AlpineBitsVersion.V_2012_05.equals(alpineBitsVersion)
                || AlpineBitsVersion.V_2011_11.equals(alpineBitsVersion)
                || AlpineBitsVersion.V_2011_10.equals(alpineBitsVersion)
                || AlpineBitsVersion.V_2011_09.equals(alpineBitsVersion)
                || AlpineBitsVersion.V_2010_10.equals(alpineBitsVersion)
                || AlpineBitsVersion.V_2010_08.equals(alpineBitsVersion);
    }

}
