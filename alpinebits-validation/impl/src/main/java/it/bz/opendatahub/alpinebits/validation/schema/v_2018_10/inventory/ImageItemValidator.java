/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.inventory;

import it.bz.opendatahub.alpinebits.common.constants.OTACodePictureCategoryCode;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.inventory.common.Description;
import it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.inventory.common.DescriptionsValidator;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.ImageItems.ImageItem;

import java.util.List;

/**
 * Validate OTAHotelDescriptiveContentNotifRQ-&gt;HotelDescriptiveContents
 * -&gt;HotelDescriptiveContent-&gt;FacilityInfo-&gt;GuestRooms-&gt;GuestRoom
 * -&gt;MultimediaDescriptions-&gt;MultimediaDescription-&gt;ImageItems-&gt;ImageItem
 * elements.
 */
public class ImageItemValidator implements Validator<ImageItem, Void> {

    public static final String ELEMENT_NAME = Names.IMAGE_ITEM;

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final DescriptionsValidator descriptionsValidator = new DescriptionsValidator();

    @Override
    public void validate(ImageItem imageItem, Void ctx, ValidationPath path) {
        VALIDATOR.expectNotNull(imageItem, ErrorMessage.EXPECT_IMAGE_ITEM_TO_BE_NOT_NULL, path);
        // Note: this condition is also checked by XSD/RNG
        VALIDATOR.expectNotNull(imageItem.getCategory(), ErrorMessage.EXPECT_CATEGORY_TO_BE_NOT_NULL, path.withAttribute(Names.CATEGORY));

        ValidationPath imageFormatPath = path.withElement(Names.IMAGE_FORMAT);
        // Note: this condition is also checked by XSD/RNG
        VALIDATOR.expectNotNull(imageItem.getImageFormat(), ErrorMessage.EXPECT_IMAGE_FORMAT_TO_BE_NOT_NULL, imageFormatPath);
        // Note: this condition is also checked by XSD/RNG
        VALIDATOR.expectNotNull(imageItem.getImageFormat().getURL(), ErrorMessage.EXPECT_URL_TO_BE_NOT_NULL, imageFormatPath.withElement("URL"));

        int categoryCode = imageItem.getCategory().intValue();
        if (!OTACodePictureCategoryCode.isCodeDefined(categoryCode)) {
            String message = String.format(ErrorMessage.EXPECT_PICTURE_CATEGORY_CODE_TO_BE_DEFINED, categoryCode);
            VALIDATOR.throwValidationException(message, path.withAttribute(Names.CATEGORY));
        }

        if (imageItem.getDescriptions() != null) {
            List<Description> descriptions = Description.fromImageItemDescriptions(imageItem.getDescriptions());
            this.descriptionsValidator.validate(descriptions, null, path.withElement(Names.DESCRIPTION_LIST));
        }
    }

}
