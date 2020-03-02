/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.inventory;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.context.inventory.InventoryContext;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents;

/**
 * Validate OTAHotelDescriptiveContentNotifRQ-&gt;HotelDescriptiveContents
 * elements.
 */
public class HotelDescriptiveContentsValidator implements Validator<HotelDescriptiveContents, InventoryContext> {

    public static final String ELEMENT_NAME = Names.HOTEL_DESCRIPTIVE_CONTENTS;

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final HotelDescriptiveContentValidator hotelDescriptiveContentValidator = new HotelDescriptiveContentValidator();

    @Override
    public void validate(HotelDescriptiveContents hotelDescriptiveContents, InventoryContext ctx, ValidationPath path) {
        VALIDATOR.expectNotNull(hotelDescriptiveContents, ErrorMessage.EXPECT_HOTEL_DESCRIPTIVE_CONTENTS_TO_BE_NOT_NULL, path);
        VALIDATOR.expectNotNull(ctx, ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL);

        this.hotelDescriptiveContentValidator.validate(
                hotelDescriptiveContents.getHotelDescriptiveContent(),
                ctx,
                path.withElement(HotelDescriptiveContentValidator.ELEMENT_NAME)
        );
    }

}
