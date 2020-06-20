/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.common.inventory;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.NotNullValidationException;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.context.inventory.InventoryContext;
import it.bz.opendatahub.alpinebits.xml.schema.ota.AffiliationInfoType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.ContactInfosType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.HotelInfoType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent;
import org.testng.annotations.Test;

/**
 * Abstract tests for {@link HotelDescriptiveContent} validator.
 */
public abstract class AbstractHotelDescriptiveContentValidatorTest {

    protected static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.HOTEL_DESCRIPTIVE_CONTENT);

    protected static final String DEFAULT_HOTEL_CODE = "XYZ";

    @Test
    public void testValidate_ShouldThrow_WhenHotelDescriptiveContentIsNull() {
        this.validateAndAssert(
                null,
                null,
                NullValidationException.class,
                ErrorMessage.EXPECT_HOTEL_DESCRIPTIVE_CONTENT_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenContextIsNull() {
        HotelDescriptiveContent content = new HotelDescriptiveContent();

        this.validateAndAssert(
                content,
                null,
                NullValidationException.class,
                ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenBothHotelCodeAndHotelNameAreMissing() {
        HotelDescriptiveContent content = new HotelDescriptiveContent();
        InventoryContext ctx = new InventoryContext(null);

        this.validateAndAssert(
                content,
                ctx,
                ValidationException.class,
                ErrorMessage.EXPECT_HOTEL_CODE_AND_HOTEL_NAME_TO_BE_NOT_BOTH_NULL
        );
    }

    @Test
    public void testValidate_GivenInventoryBasic_ShouldThrow_WhenHotelInfoIsNotNull() {
        HotelDescriptiveContent content = new HotelDescriptiveContent();
        content.setHotelCode(DEFAULT_HOTEL_CODE);
        content.setHotelInfo(new HotelInfoType());
        InventoryContext ctx = new InventoryContext(AlpineBitsAction.INVENTORY_BASIC_PUSH);

        this.validateAndAssert(
                content,
                ctx,
                NotNullValidationException.class,
                ErrorMessage.EXPECT_HOTEL_INFO_TO_BE_NULL
        );
    }

    @Test
    public void testValidate_GivenInventoryBasic_ShouldThrow_WhenHotelPoliciesIsNotNull() {
        HotelDescriptiveContent content = new HotelDescriptiveContent();
        content.setHotelCode(DEFAULT_HOTEL_CODE);
        content.setPolicies(new HotelDescriptiveContent.Policies());
        InventoryContext ctx = new InventoryContext(AlpineBitsAction.INVENTORY_BASIC_PUSH);

        this.validateAndAssert(
                content,
                ctx,
                NotNullValidationException.class,
                ErrorMessage.EXPECT_POLICIES_TO_BE_NULL
        );
    }

    @Test
    public void testValidate_GivenInventoryBasic_ShouldThrow_WhenAffiliationInfoIsNotNull() {
        HotelDescriptiveContent content = new HotelDescriptiveContent();
        content.setHotelCode(DEFAULT_HOTEL_CODE);
        content.setAffiliationInfo(new AffiliationInfoType());
        InventoryContext ctx = new InventoryContext(AlpineBitsAction.INVENTORY_BASIC_PUSH);

        this.validateAndAssert(
                content,
                ctx,
                NotNullValidationException.class,
                ErrorMessage.EXPECT_AFFILITATION_INFO_TO_BE_NULL
        );
    }

    @Test
    public void testValidate_GivenInventoryBasic_ShouldThrow_WhenContactInfosIsNotNull() {
        HotelDescriptiveContent content = new HotelDescriptiveContent();
        content.setHotelCode(DEFAULT_HOTEL_CODE);
        content.setContactInfos(new ContactInfosType());
        InventoryContext ctx = new InventoryContext(AlpineBitsAction.INVENTORY_BASIC_PUSH);

        this.validateAndAssert(
                content,
                ctx,
                NotNullValidationException.class,
                ErrorMessage.EXPECT_CONTACT_INFOS_TO_BE_NULL
        );
    }

    protected abstract void validateAndAssert(
            HotelDescriptiveContent data,
            InventoryContext ctx,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    );

}