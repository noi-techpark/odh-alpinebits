/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.utils.response;

import it.bz.opendatahub.alpinebits.common.constants.OTACodeErrorCodes;
import it.bz.opendatahub.alpinebits.common.constants.OTACodeErrorWarningType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ErrorType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MessageAcknowledgementType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.WarningType;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Tests for {@link MessageAcknowledgementTypeBuilder}.
 */
public class MessageAcknowledgementTypeBuilderTest {

    @Test
    public void testSuccess() {
        MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.success();
        assertNotNull(mat.getSuccess());
        assertNull(mat.getErrors());
        assertNull(mat.getWarnings());
        assertDefaultProperties(mat);
    }

    @Test
    public void testAdvisory_WithSingleMessage() {
        String message = "test message";
        MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.advisory(message);
        assertNotNull(mat.getSuccess());
        assertNotNull(mat.getWarnings());
        assertNull(mat.getErrors());
        assertDefaultProperties(mat);

        List<WarningType> warnings = mat.getWarnings().getWarnings();
        assertEquals(warnings.size(), 1);

        assertEquals(warnings.get(0).getType(), OTACodeErrorWarningType.ADVISORY.getCode());
        assertEquals(warnings.get(0).getValue(), message);
    }

    @Test
    public void testAdvisory_WithManyMessages() {
        String message1 = "test message 1";
        String message2 = "test message 2";
        MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.advisory(message1, message2);
        assertNotNull(mat.getSuccess());
        assertNotNull(mat.getWarnings());
        assertNull(mat.getErrors());
        assertDefaultProperties(mat);

        List<WarningType> warnings = mat.getWarnings().getWarnings();
        assertEquals(warnings.size(), 2);

        assertEquals(warnings.get(0).getType(), OTACodeErrorWarningType.ADVISORY.getCode());
        assertEquals(warnings.get(0).getValue(), message1);
        assertEquals(warnings.get(1).getType(), OTACodeErrorWarningType.ADVISORY.getCode());
        assertEquals(warnings.get(1).getValue(), message2);
    }

    @Test
    public void testWarning_WithSingleMessage() {
        WarningEntry warningEntry = new WarningEntry("test message", WarningEntry.Type.BIZ_RULE);
        MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.warning(warningEntry);
        assertNotNull(mat.getSuccess());
        assertNotNull(mat.getWarnings());
        assertNull(mat.getErrors());
        assertDefaultProperties(mat);

        List<WarningType> warnings = mat.getWarnings().getWarnings();
        assertEquals(warnings.size(), 1);

        assertEquals(warnings.get(0).getType(), warningEntry.getType().getCode());
        assertEquals(warnings.get(0).getValue(), warningEntry.getMessage());
        assertNull(warnings.get(0).getCode());
    }

    @Test
    public void testWarning_WithManyMessages() {
        WarningEntry warningEntry1 = new WarningEntry("test message 1", WarningEntry.Type.BIZ_RULE);
        WarningEntry warningEntry2 = new WarningEntry("test message 2", WarningEntry.Type.PROCESSING_EXCEPTION);

        MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.warning(warningEntry1, warningEntry2);
        assertNotNull(mat.getSuccess());
        assertNotNull(mat.getWarnings());
        assertNull(mat.getErrors());
        assertDefaultProperties(mat);

        List<WarningType> warnings = mat.getWarnings().getWarnings();
        assertEquals(warnings.size(), 2);

        WarningType warningType1 = warnings.get(0);
        assertEquals(warningType1.getType(), warningEntry1.getType().getCode());
        assertEquals(warningType1.getValue(), warningEntry1.getMessage());
        assertNull(warningType1.getCode());

        WarningType warningType2 = warnings.get(1);
        assertEquals(warningType2.getType(), warningEntry2.getType().getCode());
        assertEquals(warningType2.getValue(), warningEntry2.getMessage());
        assertNull(warningType2.getCode());
    }

    @Test
    public void testError_WithSingleMessage() {
        ErrorEntry errorEntry = new ErrorEntry("test message", OTACodeErrorCodes.NO_SERVICE_AT_REQUESTED_TIME);
        MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.error(errorEntry);
        assertNotNull(mat.getErrors());
        assertNull(mat.getSuccess());
        assertNull(mat.getWarnings());
        assertDefaultProperties(mat);

        List<ErrorType> errors = mat.getErrors().getErrors();
        assertEquals(errors.size(), 1);

        assertEquals(errors.get(0).getType(), OTACodeErrorWarningType.APPLICATION_ERROR.getCode());
        assertEquals(errors.get(0).getValue(), errorEntry.getMessage());
        assertEquals(errors.get(0).getCode(), errorEntry.getCode().getCode());
    }

    @Test
    public void testError_WithManyMessages() {
        ErrorEntry errorEntry1 = new ErrorEntry("test message 1", OTACodeErrorCodes.NO_SERVICE_AT_REQUESTED_TIME);
        ErrorEntry errorEntry2 = new ErrorEntry("test message 2", OTACodeErrorCodes.ERROR_ENTRY_CODE);

        MessageAcknowledgementType mat = MessageAcknowledgementTypeBuilder.error(errorEntry1, errorEntry2);
        assertNotNull(mat.getErrors());
        assertNull(mat.getSuccess());
        assertNull(mat.getWarnings());
        assertDefaultProperties(mat);

        List<ErrorType> errors = mat.getErrors().getErrors();
        assertEquals(errors.size(), 2);

        ErrorType errorType1 = errors.get(0);
        assertEquals(errorType1.getType(), OTACodeErrorWarningType.APPLICATION_ERROR.getCode());
        assertEquals(errorType1.getValue(), errorEntry1.getMessage());
        assertEquals(errorType1.getCode(), errorEntry1.getCode().getCode());

        ErrorType errorType2 = errors.get(1);
        assertEquals(errorType2.getType(), OTACodeErrorWarningType.APPLICATION_ERROR.getCode());
        assertEquals(errorType2.getValue(), errorEntry2.getMessage());
        assertEquals(errorType2.getCode(), errorEntry2.getCode().getCode());
    }

    private void assertDefaultProperties(MessageAcknowledgementType mat) {
        assertNotNull(mat.getVersion());
        assertNull(mat.getAltLangID());
        assertNull(mat.getCorrelationID());
        assertNull(mat.getEchoToken());
        assertNull(mat.getPrimaryLangID());
        assertNull(mat.getSequenceNmbr());
        assertNull(mat.getTarget());
        assertNull(mat.getTargetName());
        assertNull(mat.getTimeStamp());
        assertNull(mat.getTPAExtensions());
        assertNull(mat.getTransactionIdentifier());
        assertNull(mat.getTransactionStatusCode());
        assertNull(mat.getUniqueID());
        assertNull(mat.isRetransmissionIndicator());
    }

}