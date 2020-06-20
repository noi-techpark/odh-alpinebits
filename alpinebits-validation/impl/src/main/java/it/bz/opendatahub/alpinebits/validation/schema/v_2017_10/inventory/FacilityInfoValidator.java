/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.context.inventory.InventoryContext;
import it.bz.opendatahub.alpinebits.xml.schema.ota.FacilityInfoType;

/**
 * Use this validator to validate the FacilityInfoType in AlpineBits 2017
 * Inventory documents.
 *
 * @see FacilityInfoType
 */
public class FacilityInfoValidator implements Validator<FacilityInfoType, InventoryContext> {

    public static final String ELEMENT_NAME = Names.FACILITY_INFO;

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final GuestRoomsValidator guestRoomsValidator = new GuestRoomsValidator();

    @Override
    public void validate(FacilityInfoType facilityInfo, InventoryContext ctx, ValidationPath path) {
        VALIDATOR.expectNotNull(facilityInfo, ErrorMessage.EXPECT_FACILITY_INFO_TO_BE_NOT_NULL, path);
        VALIDATOR.expectNotNull(ctx, ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL);

        this.guestRoomsValidator.validate(
                facilityInfo.getGuestRooms(),
                ctx,
                path.withElement(GuestRoomsValidator.ELEMENT_NAME)
        );
    }
}
