// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.xml.schema.ota.StatusApplicationControlType;

/**
 * Use this validator to validate the StatusApplicationControlType in AlpineBits 2017
 * FreeRooms documents.
 *
 * @see StatusApplicationControlType
 */
public class StatusApplicationControlValidator implements Validator<StatusApplicationControlType, Void> {

    public static final String ELEMENT_NAME = Names.STATUS_APPLICATION_CONTROL;

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    @Override
    public void validate(StatusApplicationControlType statusApplicationControl, Void ctx, ValidationPath path) {
        VALIDATOR.expectNotNull(statusApplicationControl, ErrorMessage.EXPECT_STATUS_APPLICATION_CONTROL_TO_BE_NOT_NULL, path);

        VALIDATOR.expectNotNull(
                statusApplicationControl.getStart(),
                ErrorMessage.EXPECT_START_TO_BE_NOT_NULL,
                path.withAttribute(Names.START)
        );
        VALIDATOR.expectNotNull(
                statusApplicationControl.getEnd(),
                ErrorMessage.EXPECT_END_TO_BE_NOT_NULL,
                path.withAttribute(Names.END)
        );
        VALIDATOR.expectNotNull(
                statusApplicationControl.getInvTypeCode(),
                ErrorMessage.EXPECT_INV_TYPE_CODE_TO_BE_NOT_NULL,
                path.withAttribute(Names.INV_TYPE_CODE)
        );
    }
}
