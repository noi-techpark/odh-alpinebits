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
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test cases for {@link OTAPingRSBuilder} class.
 */
public class OTAPingRSBuilderTest {

    @Test
    public void testBuild() {
        String echoDataRequest = "{}";
        String echoDataResponse = "{}";
        OTAPingRS rs = OTAPingRSBuilder.build(echoDataRequest, echoDataResponse);

        boolean echoElementFound = false;
        boolean successElementFound = false;
        boolean warningElementFound = false;
        for (Object o : rs.getSuccessesAndEchoDatasAndWarnings()) {
            if (o instanceof String) {
                echoElementFound = true;
                String echoData = (String) o;
                assertEquals(echoData, echoDataRequest);
            } else if (o instanceof SuccessType) {
                successElementFound = true;
            } else if (o instanceof WarningsType) {
                warningElementFound = true;
                WarningsType warningsType = (WarningsType) o;

                assertEquals(warningsType.getWarnings().size(), 1);

                WarningType wt = warningsType.getWarnings().get(0);
                assertEquals(wt.getValue(), echoDataRequest);
                assertEquals(wt.getType(), OTAPingRSBuilder.TYPE_11);
                assertEquals(wt.getStatus(), OTAPingRSBuilder.ALPINEBITS_HANDSHAKE);
                assertEquals(wt.getValue(), echoDataResponse);

            } else {
                throw new RuntimeException("Found unknown type in list of objects retrieved by OTAPingRS#getSuccessesAndEchoDatasAndWarnings");
            }
        }

        assertTrue(echoElementFound);
        assertTrue(successElementFound);
        assertTrue(warningElementFound);
    }

}