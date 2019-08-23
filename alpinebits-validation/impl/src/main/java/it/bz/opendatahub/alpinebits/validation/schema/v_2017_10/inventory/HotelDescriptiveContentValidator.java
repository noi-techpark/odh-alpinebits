/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.validation.context.inventory.InventoryContext;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent;

/**
 * Validate OTAHotelDescriptiveContentNotifRQ-&gt;HotelDescriptiveContents
 * -&gt;HotelDescriptiveContent elements.
 */
public class HotelDescriptiveContentValidator implements Validator<HotelDescriptiveContent, InventoryContext> {

    public static final String ELEMENT_NAME = Names.HOTEL_DESCRIPTIVE_CONTENT;

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final FacilityInfoValidator facilityInfoValidator = new FacilityInfoValidator();

    @Override
    public void validate(HotelDescriptiveContent hotelDescriptiveContent, InventoryContext ctx, ValidationPath path) {
        VALIDATOR.expectNotNull(hotelDescriptiveContent, ErrorMessage.EXPECT_HOTEL_DESCRIPTIVE_CONTENT_TO_BE_NOT_NULL, path);
        VALIDATOR.expectNotNull(ctx, ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL);

        VALIDATOR.expectHotelCodeAndNameNotBothNull(
                hotelDescriptiveContent.getHotelCode(),
                hotelDescriptiveContent.getHotelName(),
                ErrorMessage.EXPECT_HOTEL_CODE_AND_HOTEL_NAME_TO_BE_NOT_BOTH_NULL,
                path.withAttribute(String.format("%s/%s", Names.HOTEL_CODE, Names.HOTEL_NAME))
        );

        // Check, that the following elements are not present in
        // an Inventory/Basic push document (although they are optional
        // in an Inventory/HotelInfo push document):
        // - HotelInfo
        // - Policies
        // - AffilitationInfo
        // - ContactInfos
        if (isInventoryBasicAction(ctx)) {
            VALIDATOR.expectNull(
                    hotelDescriptiveContent.getHotelInfo(),
                    ErrorMessage.EXPECT_HOTEL_INFO_TO_BE_NULL,
                    path.withElement(Names.HOTEL_INFO)
            );
            VALIDATOR.expectNull(
                    hotelDescriptiveContent.getPolicies(),
                    ErrorMessage.EXPECT_POLICIES_TO_BE_NULL,
                    path.withElement(Names.POLICIES)
            );
            VALIDATOR.expectNull(
                    hotelDescriptiveContent.getAffiliationInfo(),
                    ErrorMessage.EXPECT_AFFILITATION_INFO_TO_BE_NULL,
                    path.withElement(Names.AFFILIATION_INFO)
            );
            VALIDATOR.expectNull(
                    hotelDescriptiveContent.getContactInfos(),
                    ErrorMessage.EXPECT_CONTACT_INFOS_TO_BE_NULL,
                    path.withElement(Names.POLICIES)
            );
        }

        this.facilityInfoValidator.validate(
                hotelDescriptiveContent.getFacilityInfo(),
                ctx,
                path.withElement(FacilityInfoValidator.ELEMENT_NAME)
        );
    }

    private boolean isInventoryBasicAction(InventoryContext ctx) {
        return AlpineBitsAction.INVENTORY_BASIC_PUSH.equals(ctx.getAction());
    }
}
