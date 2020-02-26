/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking;

import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAPingRS;

/**
 * Builder to help creating Handshaking responses.
 */
public final class OTAPingRSBuilder {

    public static final String TYPE_11 = "11";
    public static final String ALPINEBITS_HANDSHAKE = "ALPINEBITS_HANDSHAKE";
    public static final String VERSION = "8.000";

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
     * @return
     */
    public static OTAPingRS build(String echoData, String warningData) {
        OTAPingRS.Warnings.Warning warning = new OTAPingRS.Warnings.Warning();
        warning.setType(TYPE_11);
        warning.setStatus(ALPINEBITS_HANDSHAKE);
        warning.setContent(warningData);

        OTAPingRS.Warnings warnings = new OTAPingRS.Warnings();
        warnings.setWarning(warning);

        OTAPingRS otaPingRS = new OTAPingRS();
        otaPingRS.setVersion(VERSION);
        otaPingRS.setEchoData(echoData);
        otaPingRS.setSuccess("");
        otaPingRS.setWarnings(warnings);
        return otaPingRS;
    }
}
