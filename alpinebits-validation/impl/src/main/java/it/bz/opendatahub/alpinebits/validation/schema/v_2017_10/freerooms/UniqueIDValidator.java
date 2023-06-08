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
import it.bz.opendatahub.alpinebits.xml.schema.ota.UniqueIDType;

/**
 * Use this validator to validate the UniqueID in AlpineBits 2017
 * FreeRooms documents.
 *
 * @see UniqueIDType
 */
public class UniqueIDValidator implements Validator<UniqueIDType, Boolean> {

    public static final String ELEMENT_NAME = Names.UNIQUE_ID;
    public static final String COMPLETE_SET = "CompleteSet";
    public static final String VALID_TYPE_16 = "16";
    public static final String VALID_TYPE_35 = "35";

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final String noDeltaUpdateSupportErrorMessage;

    public UniqueIDValidator(String noDeltaUpdateSupportErrorMessage) {
        this.noDeltaUpdateSupportErrorMessage = noDeltaUpdateSupportErrorMessage;
    }

    @Override
    public void validate(UniqueIDType uniqueID, Boolean supportsDeltas, ValidationPath path) {
        VALIDATOR.expectNotNull(supportsDeltas, ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL, path);

        // If the server supports deltas (capability
        // OTA_HotelAvailNotif_accept_deltas is set),
        // the element UniqueID is optional. If UniqueID
        // exists (= complete information is send), it
        // needs to be validated.
        if (Boolean.TRUE.equals(supportsDeltas) && uniqueID != null) {
            this.validateUniqueId(uniqueID, path);
        }

        // If the server doesn't support deltas, the element
        // UniqueID is required (the existence of UniqueID
        // means that complete information is send).
        if (Boolean.FALSE.equals(supportsDeltas)) {
            VALIDATOR.expectNotNull(uniqueID, noDeltaUpdateSupportErrorMessage, path);

            this.validateUniqueId(uniqueID, path);
        }
    }

    private void validateUniqueId(UniqueIDType uniqueID, ValidationPath path) {
        this.validateType(uniqueID.getType(), path);
        this.validateID(uniqueID.getID(), path);
        this.validateInstance(uniqueID.getInstance(), path);
    }

    private void validateType(String type, ValidationPath path) {
        VALIDATOR.expectNotNull(type, ErrorMessage.EXPECT_TYPE_TO_BE_NOT_NULL, path.withAttribute(Names.TYPE));

        if (!VALID_TYPE_16.equals(type) && !VALID_TYPE_35.equals(type)) {
            VALIDATOR.throwValidationException(ErrorMessage.EXPECT_TYPE_TO_BE_16_OR_35, path.withAttribute(Names.TYPE));
        }
    }

    private void validateID(String id, ValidationPath path) {
        VALIDATOR.expectNotNull(id, ErrorMessage.EXPECT_ID_TO_BE_NOT_NULL, path.withAttribute(Names.ID));
    }

    private void validateInstance(String instance, ValidationPath path) {
        VALIDATOR.expectNotNull(instance, ErrorMessage.EXPECT_INSTANCE_TO_BE_NOT_NULL, path.withAttribute(Names.INSTANCE));

        VALIDATOR.expectEquals(
                instance,
                COMPLETE_SET,
                ErrorMessage.EXPECT_INSTANCE_TO_BE_COMPLETE_SET,
                path.withAttribute(Names.INSTANCE)
        );
    }
}
