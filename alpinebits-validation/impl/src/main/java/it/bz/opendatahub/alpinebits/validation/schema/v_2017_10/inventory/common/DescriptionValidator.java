// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory.common;

import it.bz.opendatahub.alpinebits.common.constants.Iso6391;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;

/**
 * Validate a {@link Description}.
 */
public class DescriptionValidator implements Validator<Description, Void> {

    public static final String ELEMENT_NAME = Names.DESCRIPTION;

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    @Override
    public void validate(Description description, Void ctx, ValidationPath path) {
        VALIDATOR.expectNotNull(description, ErrorMessage.EXPECT_DESCRIPTION_TO_BE_NOT_NULL, path);

        String language = description.getLanguage();
        String textFormat = description.getTextFormat();

        // Note: this condition is also checked by XSD/RNG
        VALIDATOR.expectNotNull(language, ErrorMessage.EXPECT_LANGUAGE_TO_BE_NOT_NULL, path.withAttribute(Names.LANGUAGE));
        // Note: this condition is also checked by XSD/RNG
        VALIDATOR.expectNotNull(textFormat, ErrorMessage.EXPECT_TEXT_FORMAT_TO_BE_NOT_NULL, path.withAttribute(Names.TEXT_FORMAT));

        if (!Iso6391.isCodeDefined(language)) {
            String message = String.format(ErrorMessage.EXPECT_LANGUAGE_ISO639_1_CODE_TO_BE_DEFINED, language);
            VALIDATOR.throwValidationException(message, path.withAttribute(Names.LANGUAGE));
        }

        // Note: this condition is also checked by XSD/RNG
        // Important: the XSD and RNG schemas contradict the written 2017-10
        // standard and allow only the value "PlainText". To adhere to the
        // written standard, both values are checked.
        if (!Names.PLAIN_TEXT.equals(textFormat) && !Names.HTML.equals(textFormat)) {
            String message = String.format(ErrorMessage.EXPECT_TEXT_FORMAT_TO_BE_PLAINTEXT_OR_HTML, textFormat);
            VALIDATOR.throwValidationException(message, path.withAttribute(Names.TEXT_FORMAT));
        }
    }

}
