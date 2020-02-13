/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.otaextensions.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.AddressesType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.CompanyNameType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfoRootType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfoType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfosType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.CountryNameType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.EmailsType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.PhonesType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.URLsType;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ;
import org.testng.annotations.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import static org.testng.Assert.*;

/**
 * Test cases for {@link ContactInfosConverter} class.
 */
public class ContactInfosConverterTest {

    public static final ContactInfosConverter CONTACT_INFOS_CONVERTER = ContactInfosConverter.newInstance();

    @Test
    public void testToContactInfosType_hotelDescriptiveContentNull() {
        ContactInfosType contactInfosType = CONTACT_INFOS_CONVERTER.toContactInfosType(null);
        assertNull(contactInfosType);
    }

    @Test
    public void testToContactInfosType_contactInfoNull() {
        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent hotelDescriptiveContent =
                new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent();
        ContactInfosType contactInfosType = CONTACT_INFOS_CONVERTER.toContactInfosType(hotelDescriptiveContent);
        assertNull(contactInfosType);
    }

    @Test
    public void testToContactInfosType_contactInfoEmpty() {
        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent hotelDescriptiveContent =
                new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent();

        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos contactInfos =
                new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos();
        hotelDescriptiveContent.setContactInfos(contactInfos);

        ContactInfosType contactInfosType = CONTACT_INFOS_CONVERTER.toContactInfosType(hotelDescriptiveContent);
        assertNull(contactInfosType);
    }

    @Test
    public void testToContactInfosType() throws JAXBException {
        String filename = "v_2017_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo_contact_info.xml";
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTAHotelDescriptiveContentNotifRQ> xmlConverter = new JAXBXmlToObjectConverter
                .Builder<>(OTAHotelDescriptiveContentNotifRQ.class)
                .schema(schema)
                .build();
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ = xmlConverter.toObject(inputXmlStream);

        ContactInfosType contactInfosType = CONTACT_INFOS_CONVERTER.toContactInfosType(
                otaHotelDescriptiveContentNotifRQ
                        .getHotelDescriptiveContents()
                        .getHotelDescriptiveContent()
        );

        ContactInfoRootType contactInfoRootType = contactInfosType.getContactInfos().get(0);

        AddressesType.Address address = contactInfoRootType.getAddresses().getAddresses().get(0);
        assertEquals(address.getLanguage(), "en");
        assertEquals(address.getUseType(), "12");
        assertEquals(address.getAddressLines().get(0), "Waltherplatz 1");
        assertEquals(address.getCityName(), "Bozen");
        assertEquals(address.getPostalCode(), "39100");
        assertEquals(address.getCounty(), "BZ");
        assertEquals(address.getCountryName().getValue(), "Italy");

        PhonesType.Phone phone1 = contactInfoRootType.getPhones().getPhones().get(0);
        assertEquals(phone1.getPhoneTechType(), "1");
        assertEquals(phone1.getPhoneUseType(), "5");
        assertEquals(phone1.getPhoneNumber(), "+39 0771 00000");

        PhonesType.Phone phone2 = contactInfoRootType.getPhones().getPhones().get(1);
        assertEquals(phone2.getPhoneTechType(), "3");
        assertEquals(phone2.getPhoneUseType(), "5");
        assertEquals(phone2.getPhoneNumber(), "+39 0771 00001");

        EmailsType.Email email = contactInfoRootType.getEmails().getEmails().get(0);
        assertEquals(email.getEmailType(), "5");
        assertEquals(email.getValue(), "in...@alpineb.com");

        URLsType.URL url = contactInfoRootType.getURLs().getURLS().get(0);
        assertEquals(url.getValue(), "https://www.alpinebits.com");

        CompanyNameType companyNameType = contactInfoRootType.getCompanyName();
        assertEquals(companyNameType.getCode(), "IT01234567890");
        assertEquals(companyNameType.getCodeContext(), "VATIN");
        assertEquals(companyNameType.getValue(), "Hotel Company LTD");
    }

    /**
     * This test checks if all possible attributes and direct child elements of the
     * OTA ContactInfos element are covered during conversion.
     *
     * @throws JAXBException if there was an error during conversion
     */
    @Test
    public void testToContactInfosType_coverAllElementsAndAttributes() throws JAXBException {
        String filename = "v_2017_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo_contact_info_cover_all.xml";
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTAHotelDescriptiveContentNotifRQ> xmlConverter = new JAXBXmlToObjectConverter
                .Builder<>(OTAHotelDescriptiveContentNotifRQ.class)
                .schema(schema)
                .build();
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ = xmlConverter.toObject(inputXmlStream);

        ContactInfosType contactInfosType = CONTACT_INFOS_CONVERTER.toContactInfosType(
                otaHotelDescriptiveContentNotifRQ
                        .getHotelDescriptiveContents()
                        .getHotelDescriptiveContent()
        );

        assertNotNull(contactInfosType.getContactInfos());
    }

    /**
     * This test checks if the OTA extension for Inventory -> ContactInfos
     * works properly when setting OTA values.
     * <p>
     * First, it reads a default AlpineBits Inventory XML and converts it to an
     * OTAHotelDescriptiveContentNotifRQ object. This object is then extended with
     * ContactInfo and then converted back to an XML. That XML is then compared
     * to another pre-defined XML, named "expected" XML.
     *
     * @throws JAXBException                if there was an error during the marshalling / unmarshalling
     * @throws UnsupportedEncodingException if a ByteArrayOutputStream to String conversion failed
     */
    @Test
    public void testApplyContactInfo() throws JAXBException, UnsupportedEncodingException {
        // Read default Inventory XML and convert it to an OTAHotelDescriptiveContentNotifRQ object
        String defaultInventoryFilename = "v_2017_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo.xml";
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTAHotelDescriptiveContentNotifRQ> xmlToObjectConverter = new JAXBXmlToObjectConverter
                .Builder<>(OTAHotelDescriptiveContentNotifRQ.class)
                .schema(schema)
                .build();
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(defaultInventoryFilename);
        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ = xmlToObjectConverter.toObject(inputXmlStream);

        // Apply an ContactInfosType to the OTAHotelDescriptiveContentNotifRQ
        ContactInfosType contactInfosType = getContactInfoType();
        CONTACT_INFOS_CONVERTER.applyContactInfo(
                otaHotelDescriptiveContentNotifRQ.getHotelDescriptiveContents().getHotelDescriptiveContent(),
                contactInfosType
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
        String expectedXmlFilename = "v_2017_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo_contact_info.xml";
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

    private ContactInfosType getContactInfoType() {
        ContactInfoRootType contactInfoType = new ContactInfoRootType();

        contactInfoType.setLocation("6");

        AddressesType.Address address = new AddressesType.Address();
        address.setLanguage("en");
        address.setUseType("12");
        address.getAddressLines().add("Waltherplatz 1");
        address.setCityName("Bozen");
        address.setPostalCode("39100");
        address.setCounty("BZ");

        CountryNameType countryNameType = new CountryNameType();
        countryNameType.setValue("Italy");
        address.setCountryName(countryNameType);

        AddressesType addressesType = new AddressesType();
        addressesType.getAddresses().add(address);
        contactInfoType.setAddresses(addressesType);

        PhonesType phonesType = new PhonesType();

        PhonesType.Phone phone1 = new PhonesType.Phone();
        phone1.setPhoneTechType("1");
        phone1.setPhoneUseType("5");
        phone1.setPhoneNumber("+39 0771 00000");
        phonesType.getPhones().add(phone1);

        PhonesType.Phone phone2 = new PhonesType.Phone();
        phone2.setPhoneTechType("3");
        phone2.setPhoneUseType("5");
        phone2.setPhoneNumber("+39 0771 00001");
        phonesType.getPhones().add(phone2);

        contactInfoType.setPhones(phonesType);

        EmailsType emailsType = new EmailsType();
        EmailsType.Email email = new EmailsType.Email();
        email.setEmailType("5");
        email.setValue("in...@alpineb.com");
        emailsType.getEmails().add(email);
        contactInfoType.setEmails(emailsType);

        URLsType urLsType = new URLsType();
        URLsType.URL url = new URLsType.URL();
        url.setValue("https://www.alpinebits.com");
        urLsType.getURLS().add(url);
        contactInfoType.setURLs(urLsType);

        ContactInfoType.CompanyName companyName = new ContactInfoType.CompanyName();
        companyName.setCode("IT01234567890");
        companyName.setCodeContext("VATIN");
        companyName.setValue("Hotel Company LTD");
        contactInfoType.setCompanyName(companyName);

        ContactInfosType contactInfosType = new ContactInfosType();
        contactInfosType.getContactInfos().add(contactInfoType);
        return contactInfosType;
    }
}