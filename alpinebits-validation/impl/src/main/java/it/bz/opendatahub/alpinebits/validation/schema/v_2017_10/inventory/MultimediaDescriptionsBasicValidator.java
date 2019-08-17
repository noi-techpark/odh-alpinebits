/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.common.constants.OTACodeInformationType;
import it.bz.opendatahub.alpinebits.validation.ContextWithAction;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.ImageItems.ImageItem;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validate Inventory/Basic OTAHotelDescriptiveContentNotifRQ
 * ->HotelDescriptiveContents->HotelDescriptiveContent
 * ->FacilityInfo->GuestRooms->GuestRoom->MultimediaDescriptions
 * elements.
 */
public class MultimediaDescriptionsBasicValidator implements Validator<MultimediaDescriptions, ContextWithAction> {

    public static final String ELEMENT_NAME = Names.MULTIMEDIA_DESCRIPTIONS;

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final ImageItemValidator imageItemValidator = new ImageItemValidator();
    private final TextItemValidator textItemValidator = new TextItemValidator();

    @Override
    public void validate(MultimediaDescriptions multimediaDescriptions, ContextWithAction ctx, ValidationPath path) {
        VALIDATOR.expectNotNull(multimediaDescriptions, ErrorMessage.EXPECT_MULTIMEDIA_DESCRIPTIONS_TO_BE_NOT_NULL);
        VALIDATOR.expectNotNull(ctx, ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL);

        List<MultimediaDescription> descriptions = multimediaDescriptions.getMultimediaDescriptions();

        ValidationPath multimediaDescriptionPath = path.withElement(Names.MULTIMEDIA_DESCRIPTION_LIST);

        // Inventory Basic push requires at least a MultimediaDescription element with InfoCode=25,
        // and empty collection is therefor not allowed.
        // Note: this condition is also checked by RNG (but not by XSD)
        VALIDATOR.expectNonEmptyCollection(
                descriptions,
                ErrorMessage.EXPECT_REQUIRED_MULTIMEDIA_DESCRIPTION_TO_EXIST,
                multimediaDescriptionPath
        );

        // At most 3 MultimediaDescription elements are allowed (with InfoCodes 1, 23, and 15)
        if (descriptions.size() > 3) {
            VALIDATOR.throwValidationException(
                    ErrorMessage.EXPECT_MULTIMEDIA_DESCRIPTIONS_TO_HAVE_AT_MOST_THREE_ELEMENTS,
                    multimediaDescriptionPath
            );
        }

        this.validateInfoCodes(descriptions, path);

        this.validateMultimediaDescriptions(descriptions, path);
    }

    private void validateInfoCodes(List<MultimediaDescription> descriptions, ValidationPath path) {
        Set<BigInteger> codes = new HashSet<>();

        for (int i = 0; i < descriptions.size(); i++) {
            MultimediaDescription md = descriptions.get(i);

            ValidationPath indexedPath = path.withElement(Names.MULTIMEDIA_DESCRIPTION).withIndex(i);

            BigInteger infoCode = md.getInfoCode();

            // InfoCode is required
            VALIDATOR.expectNotNull(
                    infoCode,
                    ErrorMessage.EXPECT_INFO_CODE_TO_BE_NOT_NULL,
                    indexedPath.withAttribute(Names.INFO_CODE)
            );

            // The value of InfoCode must be well defined
            // Note: this condition is also checked by XSD/RNG
            if (!OTACodeInformationType.isCodeDefined(infoCode.intValue())) {
                VALIDATOR.throwValidationException(
                        String.format(ErrorMessage.EXPECT_INFORMATION_TYPE_CODE_TO_BE_DEFINED, infoCode),
                        indexedPath.withAttribute(Names.INFO_CODE)
                );
            }

            // Check for duplicate InfoCode values
            if (codes.contains(infoCode)) {
                String message = String.format(ErrorMessage.EXPECT_NO_DUPLICATE_INFO_CODE, infoCode);
                VALIDATOR.throwValidationException(message, indexedPath.withAttribute(Names.INFO_CODE));
            }
            codes.add(infoCode);
        }
    }

    private void validateMultimediaDescriptions(List<MultimediaDescription> descriptions, ValidationPath path) {
        Map<BigInteger, MultimediaDescriptionWithIndex> index = new HashMap<>();
        for (int i = 0; i < descriptions.size(); i++) {
            MultimediaDescription mm = descriptions.get(i);
            MultimediaDescriptionWithIndex mmwi = new MultimediaDescriptionWithIndex(mm, i);
            index.put(mm.getInfoCode(), mmwi);
        }

        this.validateInfoCode25MultimediaDescription(index.get(BigInteger.valueOf(25)), path);
        this.validateInfoCode1MultimediaDescription(index.get(BigInteger.valueOf(1)), path);
        this.validateInfoCode23MultimediaDescription(index.get(BigInteger.valueOf(23)), path);
    }

    private void validateInfoCode25MultimediaDescription(
            MultimediaDescriptionWithIndex descriptionWithIndex,
            ValidationPath path
    ) {
        // A MultimediaDescription with InfoCode 25 (Long name) is mandatory
        VALIDATOR.expectNotNull(
                descriptionWithIndex,
                ErrorMessage.EXPECT_INFO_CODE_25_TO_BE_DEFINED,
                path.withElement(Names.MULTIMEDIA_DESCRIPTION_LIST)
        );

        MultimediaDescription description = descriptionWithIndex.getMultimediaDescription();
        int index = descriptionWithIndex.getIndex();
        ValidationPath indexedPath = path.withElement(Names.MULTIMEDIA_DESCRIPTION).withIndex(index);

        VALIDATOR.expectNotNull(
                description.getTextItems(),
                ErrorMessage.EXPECT_TEXT_ITEMS_TO_BE_NOT_NULL,
                indexedPath.withElement(Names.TEXT_ITEMS)
        );

        // Note: this condition is also checked by RNG (but not by XSD) - only TextItems allowed
        VALIDATOR.expectNull(
                description.getImageItems(),
                ErrorMessage.EXPECT_IMAGE_ITEMS_TO_BE_NULL,
                indexedPath.withElement(Names.IMAGE_ITEMS)
        );

        this.textItemValidator.validate(
                description.getTextItems().getTextItem(),
                null,
                indexedPath.withElement(Names.TEXT_ITEMS).withElement(TextItemValidator.ELEMENT_NAME)
        );
    }

    private void validateInfoCode1MultimediaDescription(
            MultimediaDescriptionWithIndex descriptionWithIndex,
            ValidationPath path
    ) {
        // A MultimediaDescription with InfoCode 1 (Description) is optional
        if (descriptionWithIndex != null) {
            MultimediaDescription description = descriptionWithIndex.getMultimediaDescription();
            int index = descriptionWithIndex.getIndex();
            ValidationPath indexedPath = path.withElement(Names.MULTIMEDIA_DESCRIPTION).withIndex(index);

            VALIDATOR.expectNotNull(
                    description.getTextItems(),
                    ErrorMessage.EXPECT_TEXT_ITEMS_TO_BE_NOT_NULL,
                    indexedPath.withElement(Names.TEXT_ITEMS)
            );

            // Note: this condition is also checked by RNG (but not by XSD) - only TextItems allowed
            VALIDATOR.expectNull(
                    description.getImageItems(),
                    ErrorMessage.EXPECT_IMAGE_ITEMS_TO_BE_NULL,
                    indexedPath.withElement(Names.IMAGE_ITEMS)
            );

            this.textItemValidator.validate(
                    description.getTextItems().getTextItem(),
                    null,
                    indexedPath.withElement(Names.TEXT_ITEMS).withElement(TextItemValidator.ELEMENT_NAME)
            );
        }
    }

    private void validateInfoCode23MultimediaDescription(
            MultimediaDescriptionWithIndex descriptionWithIndex,
            ValidationPath path
    ) {
        // A MultimediaDescription with InfoCode 23 (Pictures) is optional
        if (descriptionWithIndex != null) {
            MultimediaDescription description = descriptionWithIndex.getMultimediaDescription();
            int index = descriptionWithIndex.getIndex();
            ValidationPath indexedPath = path.withElement(Names.MULTIMEDIA_DESCRIPTION).withIndex(index);

            // Note: this condition is also checked by RNG (but not by XSD) - only ImageItems allowed
            VALIDATOR.expectNull(
                    description.getTextItems(),
                    ErrorMessage.EXPECT_TEXT_ITEMS_TO_BE_NULL,
                    indexedPath.withElement(Names.TEXT_ITEMS)
            );
            VALIDATOR.expectNotNull(
                    description.getImageItems(),
                    ErrorMessage.EXPECT_IMAGE_ITEMS_TO_BE_NOT_NULL,
                    indexedPath.withElement(Names.IMAGE_ITEMS)
            );

            List<ImageItem> imageItems = description.getImageItems().getImageItems();
            VALIDATOR.expectNonEmptyCollection(
                    imageItems,
                    ErrorMessage.EXPECT_IMAGE_ITEM_LIST_TO_BE_NOT_EMPTY,
                    indexedPath.withElement(Names.IMAGE_ITEMS).withElement(Names.IMAGE_ITEM_LIST)
            );

            for (int i = 0; i < imageItems.size(); i++) {
                ImageItem imageItem = imageItems.get(i);

                ValidationPath imageItemIndexedPath = indexedPath.withElement(Names.IMAGE_ITEMS).withElement(Names.IMAGE_ITEM).withIndex(i);

                this.imageItemValidator.validate(imageItem, null, imageItemIndexedPath);
            }
        }
    }

    /**
     * This helper class is used to provide better error outputs.
     * <p>
     * It contains a {@link MultimediaDescription} and its index.
     */
    private class MultimediaDescriptionWithIndex {

        private final MultimediaDescription multimediaDescription;
        private final int index;

        MultimediaDescriptionWithIndex(MultimediaDescription multimediaDescription, int index) {
            this.multimediaDescription = multimediaDescription;
            this.index = index;
        }

        MultimediaDescription getMultimediaDescription() {
            return multimediaDescription;
        }

        int getIndex() {
            return index;
        }
    }

}