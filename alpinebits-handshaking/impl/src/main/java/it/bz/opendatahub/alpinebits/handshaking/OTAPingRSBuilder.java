// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking;

import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAPingRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.SuccessType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.WarningType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.WarningsType;

import java.math.BigDecimal;

/**
 * Builder to help creating Handshaking responses.
 */
public final class OTAPingRSBuilder {

    public static final String TYPE_11 = "11";
    public static final String ALPINEBITS_HANDSHAKE = "ALPINEBITS_HANDSHAKE";
    public static final BigDecimal VERSION = BigDecimal.valueOf(8);

    private OTAPingRSBuilder() {
        // Empty
    }

    /**
     * Build an {@link OTAPingRS} with the given data.
     * <p>
     * The result is build according to AlpineBits 2018-10 standard:
     * <ul>
     *     <li>The echoData is included in the <code>EchoData</code> element</li>
     *     <li>The warningData is included the <code>Warning</code> element</li>
     *     <li>The type is set to {@link #TYPE_11}</li>
     *     <li>The status is set to {@link #ALPINEBITS_HANDSHAKE}</li>
     *     <li>The <code>Success</code> flag is set</li>
     * </ul>
     * <p>
     *
     * @param echoData    The data to include in the  <code>EchoData</code> element.
     * @param warningData data to include in the  <code>Warning</code> element.
     * @return An instance of {@link OTAPingRS} containing the given echo and
     * warning data.
     */
    public static OTAPingRS build(String echoData, String warningData) {
        WarningType warningType = new WarningType();
        warningType.setType(TYPE_11);
        warningType.setStatus(ALPINEBITS_HANDSHAKE);
        warningType.setValue(warningData);

        WarningsType warningsType = new WarningsType();
        warningsType.getWarnings().add(warningType);

        OTAPingRS otaPingRS = new OTAPingRS();
        otaPingRS.setVersion(VERSION);

        otaPingRS.getSuccessesAndEchoDatasAndWarnings().add(new SuccessType());
        otaPingRS.getSuccessesAndEchoDatasAndWarnings().add(warningsType);
        otaPingRS.getSuccessesAndEchoDatasAndWarnings().add(echoData);
        return otaPingRS;
    }
}
