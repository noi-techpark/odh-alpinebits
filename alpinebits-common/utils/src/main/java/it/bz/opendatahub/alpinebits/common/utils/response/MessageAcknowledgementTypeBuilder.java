// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.utils.response;

import it.bz.opendatahub.alpinebits.common.constants.OTACodeErrorCodes;
import it.bz.opendatahub.alpinebits.common.constants.OTACodeErrorWarningType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ErrorType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ErrorsType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MessageAcknowledgementType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.SuccessType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.WarningType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.WarningsType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to build instances of {@link MessageAcknowledgementType}.
 */
public final class MessageAcknowledgementTypeBuilder {

    private MessageAcknowledgementTypeBuilder() {
        // Empty
    }

    /**
     * Build a {@link MessageAcknowledgementType} that represents a successful outcome.
     *
     * @return A MessageAcknowledgementType for successful outcome.
     */
    public static MessageAcknowledgementType success() {
        MessageAcknowledgementType mat = new MessageAcknowledgementType();
        mat.setSuccess(new SuccessType());
        mat.setVersion(BigDecimal.ONE);
        return mat;
    }

    /**
     * Build a {@link MessageAcknowledgementType} that represents an advisory outcome.
     * <p>
     * Note that the type of the Warning elements is set to "11", according to the
     * AlpineBits specification for advisory outcomes.
     *
     * @param message         The first advisory message.
     * @param furtherMessages Further advisory messages (optional).
     * @return A MessageAcknowledgementType for advisory outcome.
     */
    public static MessageAcknowledgementType advisory(String message, String... furtherMessages) {
        List<WarningType> warningTypes = new ArrayList<>();

        // Add fist message to list of warning types
        warningTypes.add(buildAdvisory(message));

        // Add further messages to list of warning types (if such messages are defined)
        for (String m : furtherMessages) {
            warningTypes.add(buildAdvisory(m));
        }

        return buildMatForWarningTypes(warningTypes);
    }

    /**
     * Build a {@link MessageAcknowledgementType} that represents a warning outcome.
     * <p>
     * Note that the type of the Warning elements is set to "11", according to the
     * AlpineBits specification for advisory outcomes.
     *
     * @param entry          The first warning entry.
     * @param furtherEntries Further warning entries (optional).
     * @return A MessageAcknowledgementType for warning outcome.
     */
    public static MessageAcknowledgementType warning(WarningEntry entry, WarningEntry... furtherEntries) {
        List<WarningType> warningTypes = new ArrayList<>();

        // Add fist message to list of warning types
        warningTypes.add(buildWarning(entry.getMessage(), entry.getType().getCode()));

        // Add further messages to list of warning types (if such messages are defined)
        for (WarningEntry furtherEntry : furtherEntries) {
            warningTypes.add(buildWarning(furtherEntry.getMessage(), furtherEntry.getType().getCode()));
        }

        return buildMatForWarningTypes(warningTypes);
    }


    /**
     * Build a {@link MessageAcknowledgementType} that represents a error outcome.
     * <p>
     * Note that the type of the Error elements is set to "13", according to the
     * AlpineBits specification for advisory outcomes.
     *
     * @param entry          The first error entry.
     * @param furtherEntries Further error entries (optional).
     * @return A MessageAcknowledgementType for error outcome.
     */
    public static MessageAcknowledgementType error(ErrorEntry entry, ErrorEntry... furtherEntries) {
        List<ErrorType> errorTypes = new ArrayList<>();

        // Add fist message to list of warning types
        errorTypes.add(buildError(entry.getMessage(), entry.getCode()));

        // Add further messages to list of warning types (if such messages are defined)
        for (ErrorEntry furtherEntry : furtherEntries) {
            errorTypes.add(buildError(furtherEntry.getMessage(), furtherEntry.getCode()));
        }

        // Build and return correct structure for advisory
        ErrorsType errorsType = new ErrorsType();
        errorsType.getErrors().addAll(errorTypes);

        MessageAcknowledgementType mat = new MessageAcknowledgementType();
        mat.setErrors(errorsType);
        mat.setVersion(BigDecimal.ONE);
        return mat;
    }

    private static WarningType buildAdvisory(String message) {
        return buildWarning(message, OTACodeErrorWarningType.ADVISORY.getCode());
    }

    private static WarningType buildWarning(String message, String type) {
        WarningType warningType = new WarningType();
        warningType.setValue(message);
        warningType.setType(type);
        return warningType;
    }

    private static ErrorType buildError(String message, OTACodeErrorCodes code) {
        ErrorType errorType = new ErrorType();
        errorType.setValue(message);
        errorType.setCode(code.getCode());
        errorType.setType(OTACodeErrorWarningType.APPLICATION_ERROR.getCode());
        return errorType;
    }

    private static MessageAcknowledgementType buildMatForWarningTypes(List<WarningType> warningTypes) {
        WarningsType warningsType = new WarningsType();
        warningsType.getWarnings().addAll(warningTypes);

        MessageAcknowledgementType mat = new MessageAcknowledgementType();
        mat.setSuccess(new SuccessType());
        mat.setWarnings(warningsType);
        mat.setVersion(BigDecimal.ONE);
        return mat;
    }
}
