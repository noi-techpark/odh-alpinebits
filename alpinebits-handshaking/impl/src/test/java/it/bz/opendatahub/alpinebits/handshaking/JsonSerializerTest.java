/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsCapability;
import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.handshaking.dto.HandshakingData;
import it.bz.opendatahub.alpinebits.handshaking.dto.SupportedAction;
import it.bz.opendatahub.alpinebits.handshaking.dto.SupportedVersion;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.testng.Assert.assertTrue;

/**
 * Test cases for {@link JsonSerializer} class.
 */
public class JsonSerializerTest {

    private final JsonSerializer mapper = new JsonSerializer();

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testToJson_ShouldThrowOnEmptyInput() {
        this.mapper.toJson(null);
    }

    @Test
    public void testToJson_ShouldMapValidSupportData() {
        String version = AlpineBitsVersion.V_2018_10;
        String action = AlpineBitsAction.HANDSHAKING;
        String capability = AlpineBitsCapability.HANDSHAKING;

        SupportedAction supportedAction = new SupportedAction();
        supportedAction.setAction(action);
        supportedAction.setSupports(Collections.singleton(capability));

        SupportedVersion supportedVersion = new SupportedVersion();
        supportedVersion.setVersion(version);
        supportedVersion.setActions(Collections.singleton(supportedAction));

        HandshakingData handshakingData = new HandshakingData();
        handshakingData.setVersions(Collections.singleton(supportedVersion));

        String handshakingDataAsString = this.mapper.toJson(handshakingData);
        assertTrue(handshakingDataAsString.contains(version));
        assertTrue(handshakingDataAsString.contains(action));
        assertTrue(handshakingDataAsString.contains(capability));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFromJson_ShouldThrowOnEmptyJson() {
        this.mapper.fromJson(null);
    }

    @Test(expectedExceptions = HandshakingDataConversionException.class)
    public void testFromJson_ShouldThrowOnInvalidJson() {
        this.mapper.fromJson("{invalid JSON]");
    }

    @Test(expectedExceptions = HandshakingDataConversionException.class)
    public void testFromJson_ShouldThrowIfJsonIsNotMappable() {
        this.mapper.fromJson("{\"prop:\":\"doesn't exist\"}");
    }

    //TODO: linelength
    @Test
    public void testFromJson_ShouldMapValidJson() {
        this.mapper.fromJson("{\"versions\":[{\"version\":\"2018-10\",\"actions\":" +
                "[{\"action\":\"OTA_Ping:Handshaking\",\"supports\":[\"action_OTA_Ping\"]}]}]}");
    }

}