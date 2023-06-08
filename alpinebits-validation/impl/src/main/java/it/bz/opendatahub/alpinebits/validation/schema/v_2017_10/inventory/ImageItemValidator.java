// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.common.constants.OTACodePictureCategoryCode;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory.common.Description;
import it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory.common.DescriptionsValidator;
import it.bz.opendatahub.alpinebits.validation.utils.ListUtil;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ImageItemsType.ImageItem;

import java.util.List;

/**
 * Use this validator to validate the ImageItem in AlpineBits 2017
 * Inventory documents.
 *
 * @see ImageItem
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
        VALIDATOR.expectNotNull(ListUtil.extractFirst(imageItem.getImageFormats()), ErrorMessage.EXPECT_IMAGE_FORMAT_TO_BE_NOT_NULL, imageFormatPath);
        // Note: this condition is also checked by XSD/RNG
        VALIDATOR.expectNotNull(
                ListUtil.extractFirst(imageItem.getImageFormats()).getURL(),
                ErrorMessage.EXPECT_URL_TO_BE_NOT_NULL,
                imageFormatPath.withElement("URL")
        );

        String categoryCode = imageItem.getCategory();
        if (!OTACodePictureCategoryCode.isCodeDefined(categoryCode)) {
            String message = String.format(ErrorMessage.EXPECT_PICTURE_CATEGORY_CODE_TO_BE_DEFINED, categoryCode);
            VALIDATOR.throwValidationException(message, path.withAttribute(Names.CATEGORY));
        }

        if (!imageItem.getDescriptions().isEmpty()) {
            List<Description> descriptions = Description.fromImageItemDescriptions(imageItem.getDescriptions());
            this.descriptionsValidator.validate(descriptions, null, path.withElement(Names.DESCRIPTION_LIST));
        }
    }

}
