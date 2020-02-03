/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.otaextensions.v_2018_10.inventory;

import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.AcceptedPaymentsType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.BankAcctType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.EncryptionTokenType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelDescriptiveContentType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ListPaymentCardIssuer;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ListPaymentCardIssuerBase;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.PaymentCardType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.PoliciesType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.RequiredPaymentsType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.TimeUnitType;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ;
import org.testng.annotations.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;

import static org.testng.Assert.*;

/**
 * Test cases for {@link PoliciesConverter} class.
 */
public class PoliciesConverterTest {

    @Test
    public void testToContactInfosType_hotelDescriptiveContentNull() {
        PoliciesConverter contactInfosConverter = PoliciesConverter.newInstance();
        HotelDescriptiveContentType.Policies policies = contactInfosConverter.toPolicies(null);
        assertNull(policies);
    }

    @Test
    public void testToContactInfosType_contactInfoNull() {
        PoliciesConverter contactInfosConverter = PoliciesConverter.newInstance();
        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent hotelDescriptiveContent =
                new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent();
        HotelDescriptiveContentType.Policies policies = contactInfosConverter.toPolicies(hotelDescriptiveContent);
        assertNull(policies);
    }

    @Test
    public void testToContactInfosType_contactInfoEmpty() {
        PoliciesConverter contactInfosConverter = PoliciesConverter.newInstance();
        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent hotelDescriptiveContent =
                new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent();

        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.Policies policies =
                new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.Policies();
        hotelDescriptiveContent.setPolicies(policies);

        HotelDescriptiveContentType.Policies policiesResult = contactInfosConverter.toPolicies(hotelDescriptiveContent);
        assertNull(policiesResult);
    }

    @Test
    public void testToPolicies() throws JAXBException {
        String filename = "v_2018_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo_policies.xml";
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2018-10");
        XmlToObjectConverter<OTAHotelDescriptiveContentNotifRQ> xmlConverter = new JAXBXmlToObjectConverter
                .Builder<>(OTAHotelDescriptiveContentNotifRQ.class)
                .schema(schema)
                .build();
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ = xmlConverter.toObject(inputXmlStream);

        PoliciesConverter policiesConverter = PoliciesConverter.newInstance();
        HotelDescriptiveContentType.Policies policiesType = policiesConverter.toPolicies(
                otaHotelDescriptiveContentNotifRQ
                        .getHotelDescriptiveContents()
                        .getHotelDescriptiveContent()
        );

        PoliciesType.Policy policy = policiesType.getPolicies().get(0);

        RequiredPaymentsType.GuaranteePayment guaranteePayment = policy
                .getGuaranteePaymentPolicy()
                .getGuaranteePayments()
                .get(0);

        AcceptedPaymentsType.AcceptedPayment acceptedPayment1 = guaranteePayment.getAcceptedPayments().getAcceptedPayments().get(0);
        assertEquals(acceptedPayment1.getBankAcct().getBankAcctName(), "My Bank");
        assertEquals(acceptedPayment1.getBankAcct().getBankAcctNumber().getPlainText(), "IBAN12345");
        assertEquals(acceptedPayment1.getBankAcct().getBankID().getPlainText(), "SWIFT123");

        AcceptedPaymentsType.AcceptedPayment acceptedPayment2 = guaranteePayment.getAcceptedPayments().getAcceptedPayments().get(1);
        assertEquals(acceptedPayment2.getPaymentCard().getCardType().getValue().value(), "VISA");

        AcceptedPaymentsType.AcceptedPayment acceptedPayment3 = guaranteePayment.getAcceptedPayments().getAcceptedPayments().get(2);
        assertEquals(acceptedPayment3.getPaymentCard().getCardType().getValue().value(), "Mastercard");

        assertEquals(guaranteePayment.getAmountPercent().getPercent(), BigDecimal.valueOf(30));
        RequiredPaymentsType.GuaranteePayment.Deadline deadline = guaranteePayment.getDeadlines().get(0);
        assertEquals(deadline.getOffsetDropTime(), "AfterBooking");
        assertEquals(deadline.getOffsetTimeUnit(), TimeUnitType.DAY);
        assertEquals(deadline.getOffsetUnitMultiplier().intValue(), 0);

        assertEquals(policy.getPolicyInfo().getCheckInTime(), "15:00:00");
        assertEquals(policy.getPolicyInfo().getCheckOutTime(), "10:00:00");
        assertEquals(policy.getPolicyInfo().getMinGuestAge().intValue(), 16);
    }

    /**
     * This test checks if all possible attributes and direct child elements of the
     * OTA Policies element are covered during conversion.
     *
     * @throws JAXBException if there was an error during conversion
     */
    @Test
    public void testToPolicies_coverAllElementsAndAttributes() throws JAXBException {
        String filename = "v_2018_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo_policies_cover_all.xml";
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2018-10");
        XmlToObjectConverter<OTAHotelDescriptiveContentNotifRQ> xmlConverter = new JAXBXmlToObjectConverter
                .Builder<>(OTAHotelDescriptiveContentNotifRQ.class)
                .schema(schema)
                .build();
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ = xmlConverter.toObject(inputXmlStream);

        PoliciesConverter policiesConverter = PoliciesConverter.newInstance();
        HotelDescriptiveContentType.Policies policiesType = policiesConverter.toPolicies(
                otaHotelDescriptiveContentNotifRQ
                        .getHotelDescriptiveContents()
                        .getHotelDescriptiveContent()
        );

        assertNotNull(policiesType.getPolicies());

        assertNotNull(policiesType.isGuaranteeRoomTypeViaCRC());
        assertNotNull(policiesType.isGuaranteeRoomTypeViaGDS());
        assertNotNull(policiesType.isGuaranteeRoomTypeViaProperty());
    }

    /**
     * This test checks if the OTA extension for Inventory -> Policies
     * works properly when setting OTA values.
     * <p>
     * First, it reads a default AlpineBits Inventory XML and converts it to an
     * OTAHotelDescriptiveContentNotifRQ object. This object is then extended with
     * Policies and then converted back to an XML. That XML is then compared
     * to another pre-defined XML, named "expected" XML.
     *
     * @throws JAXBException                if there was an error during the marshalling / unmarshalling
     * @throws UnsupportedEncodingException if a ByteArrayOutputStream to String conversion failed
     */
    @Test
    public void testApplyPolicies() throws JAXBException, UnsupportedEncodingException {
        // Read default Inventory XML and convert it to an OTAHotelDescriptiveContentNotifRQ object
        String defaultInventoryFilename = "v_2018_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo.xml";
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2018-10");
        XmlToObjectConverter<OTAHotelDescriptiveContentNotifRQ> xmlToObjectConverter = new JAXBXmlToObjectConverter
                .Builder<>(OTAHotelDescriptiveContentNotifRQ.class)
                .schema(schema)
                .build();
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(defaultInventoryFilename);
        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ = xmlToObjectConverter.toObject(inputXmlStream);

        // Apply an ContactInfosType to the OTAHotelDescriptiveContentNotifRQ
        HotelDescriptiveContentType.Policies policiesType = getPoliciesType();
        PoliciesConverter policiesConverter = PoliciesConverter.newInstance();
        policiesConverter.applyPolicies(
                otaHotelDescriptiveContentNotifRQ.getHotelDescriptiveContents().getHotelDescriptiveContent(),
                policiesType
        );

        // Convert the augmented OTAHotelDescriptiveContentNotifRQ to XML
        ObjectToXmlConverter<OTAHotelDescriptiveContentNotifRQ> objectToXmlConverter = new JAXBObjectToXmlConverter
                .Builder<>(OTAHotelDescriptiveContentNotifRQ.class)
                .schema(schema)
                .prettyPrint(true)
                .build();
        ByteArrayOutputStream outputXml = new ByteArrayOutputStream();
        objectToXmlConverter.toXml(otaHotelDescriptiveContentNotifRQ, outputXml);

        // Read the expected XML
        String expectedXmlFilename = "v_2018_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo_policies.xml";
        InputStream expectedXml = this.getClass().getClassLoader().getResourceAsStream(expectedXmlFilename);

        // Compare the augmented OTAHotelDescriptiveContentNotifRQ XML to the expected XML
        Diff xmlDiff = DiffBuilder.compare(expectedXml).withTest(outputXml.toString("UTF-8"))
                .checkForSimilar()
                .ignoreWhitespace()
                .ignoreComments()
                .build();

        if (xmlDiff.hasDifferences()) {
            //CHECKSTYLE:OFF
            System.err.println(defaultInventoryFilename + " XML conversion difference found:");
            //CHECKSTYLE:ON
            xmlDiff.getDifferences().forEach(difference -> fail(difference.toString()));
        }
    }

    private HotelDescriptiveContentType.Policies getPoliciesType() {
        PoliciesType.Policy policy = new PoliciesType.Policy();

        RequiredPaymentsType.GuaranteePayment guaranteePayment = new RequiredPaymentsType.GuaranteePayment();
        PoliciesType.Policy.GuaranteePaymentPolicy guaranteePaymentPolicy = new PoliciesType.Policy.GuaranteePaymentPolicy();
        guaranteePaymentPolicy.getGuaranteePayments().add(guaranteePayment);
        policy.setGuaranteePaymentPolicy(guaranteePaymentPolicy);

        // AcceptedPayments
        BankAcctType bankAcctType = new BankAcctType();
        bankAcctType.setBankAcctName("My Bank");

        EncryptionTokenType bankAccountNumber = new EncryptionTokenType();
        bankAccountNumber.setPlainText("IBAN12345");
        bankAcctType.setBankAcctNumber(bankAccountNumber);

        EncryptionTokenType bankId = new EncryptionTokenType();
        bankId.setPlainText("SWIFT123");
        bankAcctType.setBankID(bankId);

        AcceptedPaymentsType.AcceptedPayment acceptedPayment1 = new AcceptedPaymentsType.AcceptedPayment();
        acceptedPayment1.setBankAcct(bankAcctType);

        PaymentCardType paymentCardType1 = new PaymentCardType();
        ListPaymentCardIssuer listPaymentCardIssuer1 = new ListPaymentCardIssuer();
        listPaymentCardIssuer1.setValue(ListPaymentCardIssuerBase.fromValue("VISA"));
        paymentCardType1.setCardType(listPaymentCardIssuer1);

        AcceptedPaymentsType.AcceptedPayment acceptedPayment2 = new AcceptedPaymentsType.AcceptedPayment();
        acceptedPayment2.setPaymentCard(paymentCardType1);

        PaymentCardType paymentCardType2 = new PaymentCardType();
        ListPaymentCardIssuer listPaymentCardIssuer2 = new ListPaymentCardIssuer();
        listPaymentCardIssuer2.setValue(ListPaymentCardIssuerBase.fromValue("Mastercard"));
        paymentCardType2.setCardType(listPaymentCardIssuer2);

        AcceptedPaymentsType.AcceptedPayment acceptedPayment3 = new AcceptedPaymentsType.AcceptedPayment();
        acceptedPayment3.setPaymentCard(paymentCardType2);

        AcceptedPaymentsType acceptedPaymentsType = new AcceptedPaymentsType();
        acceptedPaymentsType.getAcceptedPayments().add(acceptedPayment1);
        acceptedPaymentsType.getAcceptedPayments().add(acceptedPayment2);
        acceptedPaymentsType.getAcceptedPayments().add(acceptedPayment3);

        guaranteePayment.setAcceptedPayments(acceptedPaymentsType);

        // AmountPercent
        RequiredPaymentsType.GuaranteePayment.AmountPercent amountPercentType = new RequiredPaymentsType.GuaranteePayment.AmountPercent();
        amountPercentType.setPercent(BigDecimal.valueOf(30));
        guaranteePayment.setAmountPercent(amountPercentType);

        // Deadline
        RequiredPaymentsType.GuaranteePayment.Deadline deadline = new RequiredPaymentsType.GuaranteePayment.Deadline();
        deadline.setOffsetDropTime("AfterBooking");
        deadline.setOffsetTimeUnit(TimeUnitType.DAY);
        deadline.setOffsetUnitMultiplier(0);
        guaranteePayment.getDeadlines().add(deadline);

        // PolicyInfo
        PoliciesType.Policy.PolicyInfo policyInfo = new PoliciesType.Policy.PolicyInfo();
        policyInfo.setCheckInTime("15:00:00");
        policyInfo.setCheckOutTime("10:00:00");
        policyInfo.setMinGuestAge(BigInteger.valueOf(16));
        policy.setPolicyInfo(policyInfo);

        HotelDescriptiveContentType.Policies policiesType = new HotelDescriptiveContentType.Policies();
        policiesType.getPolicies().add(policy);
        return policiesType;
    }
}