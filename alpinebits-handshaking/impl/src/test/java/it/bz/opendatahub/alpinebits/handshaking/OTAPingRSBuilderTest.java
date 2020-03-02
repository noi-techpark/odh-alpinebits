/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking;

import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAPingRS;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAPingRS.Warnings;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAPingRS.Warnings.Warning;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link OTAPingRSBuilder} class.
 */
public class OTAPingRSBuilderTest {

    @Test
    public void testBuild() {
        String echoDataRequest = "{}";
        String echoDataResponse = "{}";
        OTAPingRS rs = OTAPingRSBuilder.build(echoDataRequest, echoDataResponse);

        assertEquals(rs.getSuccess(), "");
        assertEquals(rs.getEchoData(), echoDataRequest);

        Warnings warnings = rs.getWarnings();
        Warning warning = warnings.getWarning();
        assertEquals(warning.getType(), OTAPingRSBuilder.TYPE_11);
        assertEquals(warning.getStatus(), OTAPingRSBuilder.ALPINEBITS_HANDSHAKE);
        assertEquals(warning.getContent(), echoDataResponse);
    }

}