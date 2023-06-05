// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.utils.response;

import it.bz.opendatahub.alpinebits.xml.schema.ota.MessageAcknowledgementType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveContentNotifRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelInvCountNotifRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelPostEventNotifRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelRatePlanNotifRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelRatePlanRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelResNotifRS;

/**
 * Use this builder to generate response outcomes for the different AlpineBits actions.
 */
public final class ResponseOutcomeBuilder {

    private static final String PARAMETER_MESSAGE_ACKNOWLEDGEMENT_TYPE_MUST_NOT_BE_NULL = "Parameter MessageAcknowledgementType must not be null";

    private ResponseOutcomeBuilder() {
        // Empty
    }

    /**
     * This method returns a new {@link OTAHotelAvailNotifRS} instance with the information
     * of the given {@link MessageAcknowledgementType} applied to it.
     *
     * @param mat The information of this MessageAcknowledgementType is applied to the result.
     * @return A new OTAHotelAvailNotifRS instance.
     * @throws IllegalArgumentException if the parameter <code>mat</code> is null.
     */
    public static OTAHotelAvailNotifRS forOTAHotelAvailNotifRS(MessageAcknowledgementType mat) {
        if (mat == null) {
            throw new IllegalArgumentException(PARAMETER_MESSAGE_ACKNOWLEDGEMENT_TYPE_MUST_NOT_BE_NULL);
        }
        return new OTAHotelAvailNotifRS(mat);
    }

    /**
     * This method returns a new {@link OTAHotelInvCountNotifRS} instance with the information
     * of the given {@link MessageAcknowledgementType} applied to it.
     *
     * @param mat The information of this MessageAcknowledgementType is applied to the result.
     * @return A new OTAHotelInvCountNotifRS instance.
     * @throws IllegalArgumentException if the parameter <code>mat</code> is null.
     */
    public static OTAHotelInvCountNotifRS forOTAHotelInvCountNotifRS(MessageAcknowledgementType mat) {
        if (mat == null) {
            throw new IllegalArgumentException(PARAMETER_MESSAGE_ACKNOWLEDGEMENT_TYPE_MUST_NOT_BE_NULL);
        }
        OTAHotelInvCountNotifRS otaHotelInvCountNotifRS = new OTAHotelInvCountNotifRS();
        otaHotelInvCountNotifRS.setErrors(mat.getErrors());
        otaHotelInvCountNotifRS.setWarnings(mat.getWarnings());
        otaHotelInvCountNotifRS.setSuccess(mat.getSuccess());
        otaHotelInvCountNotifRS.setVersion(mat.getVersion());
        return otaHotelInvCountNotifRS;
    }

    /**
     * This method returns a new {@link OTAHotelDescriptiveContentNotifRS} instance with the information
     * of the given {@link MessageAcknowledgementType} applied to it.
     *
     * @param mat The information of this MessageAcknowledgementType is applied to the result.
     * @return A new OTAHotelDescriptiveContentNotifRS instance.
     * @throws IllegalArgumentException if the parameter <code>mat</code> is null.
     */
    public static OTAHotelDescriptiveContentNotifRS forOTAHotelDescriptiveContentNotifRS(MessageAcknowledgementType mat) {
        if (mat == null) {
            throw new IllegalArgumentException(PARAMETER_MESSAGE_ACKNOWLEDGEMENT_TYPE_MUST_NOT_BE_NULL);
        }
        return new OTAHotelDescriptiveContentNotifRS(mat);
    }

    /**
     * This method returns a new {@link OTAHotelDescriptiveInfoRS} instance with the information
     * of the given {@link MessageAcknowledgementType} applied to it.
     *
     * @param mat The information of this MessageAcknowledgementType is applied to the result.
     * @return A new OTAHotelDescriptiveInfoRS instance.
     * @throws IllegalArgumentException if the parameter <code>mat</code> is null.
     */
    public static OTAHotelDescriptiveInfoRS forOTAHotelDescriptiveInfoRS(MessageAcknowledgementType mat) {
        if (mat == null) {
            throw new IllegalArgumentException(PARAMETER_MESSAGE_ACKNOWLEDGEMENT_TYPE_MUST_NOT_BE_NULL);
        }
        OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = new OTAHotelDescriptiveInfoRS();
        otaHotelDescriptiveInfoRS.setErrors(mat.getErrors());
        otaHotelDescriptiveInfoRS.setWarnings(mat.getWarnings());
        otaHotelDescriptiveInfoRS.setSuccess(mat.getSuccess());
        otaHotelDescriptiveInfoRS.setVersion(mat.getVersion());
        return otaHotelDescriptiveInfoRS;
    }

    /**
     * This method returns a new {@link OTAHotelResNotifRS} instance with the information
     * of the given {@link MessageAcknowledgementType} applied to it.
     *
     * @param mat The information of this MessageAcknowledgementType is applied to the result.
     * @return A new OTAHotelResNotifRS instance.
     * @throws IllegalArgumentException if the parameter <code>mat</code> is null.
     */
    public static OTAHotelResNotifRS forOTAHotelResNotifRS(MessageAcknowledgementType mat) {
        if (mat == null) {
            throw new IllegalArgumentException(PARAMETER_MESSAGE_ACKNOWLEDGEMENT_TYPE_MUST_NOT_BE_NULL);
        }
        OTAHotelResNotifRS otaHotelResNotifRS = new OTAHotelResNotifRS();
        otaHotelResNotifRS.setErrors(mat.getErrors());
        otaHotelResNotifRS.setWarnings(mat.getWarnings());
        otaHotelResNotifRS.setSuccess(mat.getSuccess());
        otaHotelResNotifRS.setVersion(mat.getVersion());
        return otaHotelResNotifRS;
    }

    /**
     * This method returns a new {@link OTAHotelRatePlanNotifRS} instance with the information
     * of the given {@link MessageAcknowledgementType} applied to it.
     *
     * @param mat The information of this MessageAcknowledgementType is applied to the result.
     * @return A new OTAHotelRatePlanNotifRS instance.
     * @throws IllegalArgumentException if the parameter <code>mat</code> is null.
     */
    public static OTAHotelRatePlanNotifRS forOTAHotelRatePlanNotifRS(MessageAcknowledgementType mat) {
        if (mat == null) {
            throw new IllegalArgumentException(PARAMETER_MESSAGE_ACKNOWLEDGEMENT_TYPE_MUST_NOT_BE_NULL);
        }
        OTAHotelRatePlanNotifRS otaHotelRatePlanNotifRS = new OTAHotelRatePlanNotifRS();
        otaHotelRatePlanNotifRS.setErrors(mat.getErrors());
        otaHotelRatePlanNotifRS.setWarnings(mat.getWarnings());
        otaHotelRatePlanNotifRS.setSuccess(mat.getSuccess());
        otaHotelRatePlanNotifRS.setVersion(mat.getVersion());
        return otaHotelRatePlanNotifRS;
    }

    /**
     * This method returns a new {@link OTAHotelRatePlanRS} instance with the information
     * of the given {@link MessageAcknowledgementType} applied to it.
     *
     * @param mat The information of this MessageAcknowledgementType is applied to the result.
     * @return A new OTAHotelRatePlanRS instance.
     * @throws IllegalArgumentException if the parameter <code>mat</code> is null.
     */
    public static OTAHotelRatePlanRS forOTAHotelRatePlanRS(MessageAcknowledgementType mat) {
        if (mat == null) {
            throw new IllegalArgumentException(PARAMETER_MESSAGE_ACKNOWLEDGEMENT_TYPE_MUST_NOT_BE_NULL);
        }
        OTAHotelRatePlanRS otaHotelRatePlanRS = new OTAHotelRatePlanRS();
        otaHotelRatePlanRS.setErrors(mat.getErrors());
        otaHotelRatePlanRS.setWarnings(mat.getWarnings());
        otaHotelRatePlanRS.setSuccess(mat.getSuccess());
        otaHotelRatePlanRS.setVersion(mat.getVersion());
        return otaHotelRatePlanRS;
    }

    /**
     * This method returns a new {@link OTAHotelPostEventNotifRS} instance with the information
     * of the given {@link MessageAcknowledgementType} applied to it.
     *
     * @param mat The information of this MessageAcknowledgementType is applied to the result.
     * @return A new OTAHotelPostEventNotifRS instance.
     * @throws IllegalArgumentException if the parameter <code>mat</code> is null.
     */
    public static OTAHotelPostEventNotifRS forOTAHotelPostEventNotifRS(MessageAcknowledgementType mat) {
        if (mat == null) {
            throw new IllegalArgumentException(PARAMETER_MESSAGE_ACKNOWLEDGEMENT_TYPE_MUST_NOT_BE_NULL);
        }
        return new OTAHotelPostEventNotifRS(mat);
    }

}
