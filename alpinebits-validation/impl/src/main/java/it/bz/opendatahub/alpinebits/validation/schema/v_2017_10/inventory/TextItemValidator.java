// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

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
import it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory.common.Description;
import it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory.common.DescriptionsValidator;
import it.bz.opendatahub.alpinebits.xml.schema.ota.TextItemsType.TextItem;

import java.util.List;

/**
 * Use this validator to validate the TextItem in AlpineBits 2017
 * Inventory documents.
 *
 * @see TextItem
 */
public class TextItemValidator implements Validator<TextItem, Void> {

    public static final String ELEMENT_NAME = Names.TEXT_ITEM;

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final DescriptionsValidator descriptionsValidator = new DescriptionsValidator();

    @Override
    public void validate(TextItem textItem, Void ctx, ValidationPath path) {
        VALIDATOR.expectNotNull(textItem, ErrorMessage.EXPECT_TEXT_ITEM_TO_BE_NOT_NULL, path);
        VALIDATOR.expectNonEmptyCollection(
                textItem.getDescriptions(),
                ErrorMessage.EXPECT_DESCRIPTION_LIST_TO_BE_NOT_EMPTY,
                path
        );

        List<Description> descriptions = Description.fromTextItemDescriptions(textItem.getDescriptions());
        this.descriptionsValidator.validate(descriptions, null, path.withElement(Names.DESCRIPTION_LIST));
    }
}
