/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.utils.response;

import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveContentNotifRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelInvCountNotifRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelPostEventNotifRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelRatePlanNotifRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelRatePlanRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelResNotifRS;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for {@link ResponseOutcomeBuilder}.
 */
public class ResponseOutcomeBuilderTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testForOTAHotelAvailNotifRS_ShouldThrow_IfParameterIsNull() {
        ResponseOutcomeBuilder.forOTAHotelAvailNotifRS(null);
    }

    @Test
    public void testForOTAHotelAvailNotifRS() {
        OTAHotelAvailNotifRS otaHotelAvailNotifRS = ResponseOutcomeBuilder.forOTAHotelAvailNotifRS(MessageAcknowledgementTypeBuilder.success());
        assertNotNull(otaHotelAvailNotifRS);
        assertNotNull(otaHotelAvailNotifRS.getValue().getSuccess());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testForOTAHotelInvCountNotifRS_ShouldThrow_IfParameterIsNull() {
        ResponseOutcomeBuilder.forOTAHotelInvCountNotifRS(null);
    }

    @Test
    public void testForOTAHotelInvCountNotifRS() {
        OTAHotelInvCountNotifRS otaHotelInvCountNotifRS = ResponseOutcomeBuilder.forOTAHotelInvCountNotifRS(MessageAcknowledgementTypeBuilder.success());
        assertNotNull(otaHotelInvCountNotifRS);
        assertNotNull(otaHotelInvCountNotifRS.getSuccess());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testForOTAHotelDescriptiveContentNotifRS_ShouldThrow_IfParameterIsNull() {
        ResponseOutcomeBuilder.forOTAHotelDescriptiveContentNotifRS(null);
    }

    @Test
    public void testForOTAHotelDescriptiveContentNotifRS() {
        OTAHotelDescriptiveContentNotifRS otaHotelDescriptiveContentNotifRS = ResponseOutcomeBuilder
                .forOTAHotelDescriptiveContentNotifRS(MessageAcknowledgementTypeBuilder.success());
        assertNotNull(otaHotelDescriptiveContentNotifRS);
        assertNotNull(otaHotelDescriptiveContentNotifRS.getValue().getSuccess());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testForOTAHotelDescriptiveInfoRS_ShouldThrow_IfParameterIsNull() {
        ResponseOutcomeBuilder.forOTAHotelDescriptiveInfoRS(null);
    }

    @Test
    public void testForOTAHotelDescriptiveInfoRS() {
        OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = ResponseOutcomeBuilder
                .forOTAHotelDescriptiveInfoRS(MessageAcknowledgementTypeBuilder.success());
        assertNotNull(otaHotelDescriptiveInfoRS);
        assertNotNull(otaHotelDescriptiveInfoRS.getSuccess());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testForOTAHotelResNotifRS_ShouldThrow_IfParameterIsNull() {
        ResponseOutcomeBuilder.forOTAHotelResNotifRS(null);
    }

    @Test
    public void testForOTAHotelResNotifRS() {
        OTAHotelResNotifRS otaHotelResNotifRS = ResponseOutcomeBuilder.forOTAHotelResNotifRS(MessageAcknowledgementTypeBuilder.success());
        assertNotNull(otaHotelResNotifRS);
        assertNotNull(otaHotelResNotifRS.getSuccess());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testForOTAHotelRatePlanNotifRS_ShouldThrow_IfParameterIsNull() {
        ResponseOutcomeBuilder.forOTAHotelRatePlanNotifRS(null);
    }

    @Test
    public void testForOTAHotelRatePlanNotifRS() {
        OTAHotelRatePlanNotifRS otaHotelRatePlanNotifRS = ResponseOutcomeBuilder.forOTAHotelRatePlanNotifRS(MessageAcknowledgementTypeBuilder.success());
        assertNotNull(otaHotelRatePlanNotifRS);
        assertNotNull(otaHotelRatePlanNotifRS.getSuccess());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testForOTAHotelRatePlanRS_ShouldThrow_IfParameterIsNull() {
        ResponseOutcomeBuilder.forOTAHotelRatePlanRS(null);
    }

    @Test
    public void testForOTAHotelRatePlanRS() {
        OTAHotelRatePlanRS otaHotelRatePlanRS = ResponseOutcomeBuilder.forOTAHotelRatePlanRS(MessageAcknowledgementTypeBuilder.success());
        assertNotNull(otaHotelRatePlanRS);
        assertNotNull(otaHotelRatePlanRS.getSuccess());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testForOTAHotelPostEventNotifRS_ShouldThrow_IfParameterIsNull() {
        ResponseOutcomeBuilder.forOTAHotelPostEventNotifRS(null);
    }

    @Test
    public void testForOTAHotelPostEventNotifRS() {
        OTAHotelPostEventNotifRS otaHotelPostEventNotifRS = ResponseOutcomeBuilder.forOTAHotelPostEventNotifRS(MessageAcknowledgementTypeBuilder.success());
        assertNotNull(otaHotelPostEventNotifRS);
        assertNotNull(otaHotelPostEventNotifRS.getValue().getSuccess());
    }
}