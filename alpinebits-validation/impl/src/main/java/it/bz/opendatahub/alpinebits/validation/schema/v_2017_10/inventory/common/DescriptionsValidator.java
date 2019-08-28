/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory.common;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validate a list of {@link Description}s.
 */
public class DescriptionsValidator implements Validator<List<Description>, Void> {

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final DescriptionValidator descriptionValidator = new DescriptionValidator();

    @Override
    public void validate(List<Description> descriptions, Void ctx, ValidationPath path) {
        VALIDATOR.expectNotNull(descriptions, ErrorMessage.EXPECT_DESCRIPTIONS_TO_BE_NOT_NULL, path);
        VALIDATOR.expectNonEmptyCollection(descriptions, ErrorMessage.EXPECT_DESCRIPTION_TO_EXIST, path);

        Set<String> combinations = new HashSet<>();
        Map<String, List<Description>> descriptionsByLanguage = new HashMap<>();

        for (int i = 0; i < descriptions.size(); i++) {
            Description description = descriptions.get(i);

            ValidationPath indexedPath = path.withElement(DescriptionValidator.ELEMENT_NAME).withIndex(i);

            this.descriptionValidator.validate(description, null, indexedPath);

            String language = description.getLanguage();
            String textFormat = description.getTextFormat();
            String combination = language + textFormat;

            if (combinations.contains(combination)) {
                String message = String.format(ErrorMessage.EXPECT_NO_DUPLICATE_LANGUAGE_AND_TEXT_FORMAT, language, textFormat);
                VALIDATOR.throwValidationException(message, path);
            }
            combinations.add(combination);

            List<Description> descriptionList = descriptionsByLanguage.get(language);
            if (descriptionList == null) {
                descriptionList = new ArrayList<>();
            }
            descriptionList.add(description);
            descriptionsByLanguage.put(language, descriptionList);
        }

        // For a given language it is not allowed that there
        // exists only an element with TextFormat "HTML"

        // Note: the AlpineBits 2017-10 standard defines, that
        // TextFormat may have a value of "PlainText" or "HTML",
        // but the according XSD allows the value "PlainText" only.
        // At this point, the standard contradicts the XSD. But
        // since it is defined in the standard, the validation is
        // implemented.
        descriptionsByLanguage.forEach((key, descriptionList) -> {
            if (descriptionList.size() == 1) {
                String textFormat = descriptionList.get(0).getTextFormat();
                if (Names.HTML.equals(textFormat)) {
                    String message = String.format(ErrorMessage.EXPECT_PLAIN_TEXT_TO_EXIST, key);
                    VALIDATOR.throwValidationException(message, path);
                }
            }
        });
    }
}
