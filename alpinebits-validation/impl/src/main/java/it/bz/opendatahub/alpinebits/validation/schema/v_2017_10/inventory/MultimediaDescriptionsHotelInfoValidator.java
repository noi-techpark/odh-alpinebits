// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

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
import it.bz.opendatahub.alpinebits.xml.schema.ota.ImageItemsType.ImageItem;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MultimediaDescriptionType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MultimediaDescriptionsType;

import java.util.List;

/**
 * Use this validator to validate the MultimediaDescriptionsType in AlpineBits 2017
 * Inventory documents.
 *
 * @see MultimediaDescriptionsType
 */
public class MultimediaDescriptionsHotelInfoValidator implements Validator<MultimediaDescriptionsType, Void> {

    public static final String ELEMENT_NAME = Names.MULTIMEDIA_DESCRIPTIONS;

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final ImageItemValidator imageItemValidator = new ImageItemValidator();

    @Override
    public void validate(MultimediaDescriptionsType multimediaDescriptions, Void ctx, ValidationPath path) {
        if (multimediaDescriptions == null
                || multimediaDescriptions.getMultimediaDescriptions() == null
                || multimediaDescriptions.getMultimediaDescriptions().isEmpty()) {
            return;
        }

        List<MultimediaDescriptionType> descriptions = multimediaDescriptions.getMultimediaDescriptions();

        for (int i = 0; i < descriptions.size(); i++) {
            MultimediaDescriptionType description = descriptions.get(i);

            ValidationPath indexedPath = path.withElement(Names.MULTIMEDIA_DESCRIPTION).withIndex(i);

            VALIDATOR.expectNull(
                    description.getInfoCode(),
                    ErrorMessage.EXPECT_INFO_CODE_TO_BE_NULL,
                    indexedPath.withAttribute(Names.INFO_CODE)
            );
            VALIDATOR.expectNull(
                    description.getTextItems(),
                    ErrorMessage.EXPECT_TEXT_ITEMS_TO_BE_NULL,
                    indexedPath.withElement(Names.TEXT_ITEMS)
            );

            this.validateImageItems(description, indexedPath);
        }
    }

    private void validateImageItems(MultimediaDescriptionType description, ValidationPath path) {
        // Note: this condition is also checked by XSD/RNG
        VALIDATOR.expectNotNull(
                description.getImageItems(),
                ErrorMessage.EXPECT_IMAGE_ITEMS_TO_BE_NOT_NULL,
                path.withElement(Names.IMAGE_ITEMS)
        );

        List<ImageItem> imageItems = description.getImageItems().getImageItems();
        // Note: this condition is also checked by XSD/RNG
        VALIDATOR.expectNonEmptyCollection(
                imageItems,
                ErrorMessage.EXPECT_IMAGE_ITEM_LIST_TO_BE_NOT_EMPTY,
                path.withElement(Names.IMAGE_ITEMS).withElement(Names.IMAGE_ITEM_LIST)
        );

        for (int i = 0; i < imageItems.size(); i++) {
            ImageItem imageItem = imageItems.get(i);

            ValidationPath imageItemIndexedPath = path.withElement(Names.IMAGE_ITEMS).withElement(Names.IMAGE_ITEM).withIndex(i);

            this.imageItemValidator.validate(imageItem, null, imageItemIndexedPath);
        }
    }


}