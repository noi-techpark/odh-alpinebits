/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.otaextensions.v_2018_10.inventory;

import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.AffiliationInfoType;
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
import java.util.List;

import static org.testng.Assert.*;

/**
 * Test cases for {@link AffiliationInfoConverter} class.
 */
public class AffiliationInfoConverterTest {

    @Test
    public void testToAffiliationInfoType_hotelDescriptiveContentNull() {
        AffiliationInfoConverter affiliationInfoConverter = AffiliationInfoConverter.newInstance();
        AffiliationInfoType affiliationInfoType = affiliationInfoConverter.toAffiliationInfoType(null);
        assertNull(affiliationInfoType);
    }

    @Test
    public void testToAffiliationInfoType_affiliationInfoNull() {
        AffiliationInfoConverter affiliationInfoConverter = AffiliationInfoConverter.newInstance();
        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent hotelDescriptiveContent =
                new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent();
        AffiliationInfoType affiliationInfoType = affiliationInfoConverter.toAffiliationInfoType(hotelDescriptiveContent);
        assertNull(affiliationInfoType);
    }

    @Test
    public void testToAffiliationInfoType_affiliationInfoEmpty() {
        AffiliationInfoConverter affiliationInfoConverter = AffiliationInfoConverter.newInstance();
        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent hotelDescriptiveContent =
                new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent();

        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.AffiliationInfo affiliationInfo =
                new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.AffiliationInfo();
        hotelDescriptiveContent.setAffiliationInfo(affiliationInfo);

        AffiliationInfoType affiliationInfoType = affiliationInfoConverter.toAffiliationInfoType(hotelDescriptiveContent);
        assertNull(affiliationInfoType);
    }

    @Test
    public void testToAffiliationInfoType() throws Exception {
        String filename = "v_2018_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo_affiliation_info.xml";
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2018-10");
        XmlToObjectConverter<OTAHotelDescriptiveContentNotifRQ> xmlConverter = new JAXBXmlToObjectConverter
                .Builder<>(OTAHotelDescriptiveContentNotifRQ.class)
                .schema(schema)
                .build();
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ = xmlConverter.toObject(inputXmlStream);

        AffiliationInfoConverter affiliationInfoConverter = AffiliationInfoConverter.newInstance();
        AffiliationInfoType affiliationInfoType = affiliationInfoConverter.toAffiliationInfoType(
                otaHotelDescriptiveContentNotifRQ
                        .getHotelDescriptiveContents()
                        .getHotelDescriptiveContent()
        );
        List<AffiliationInfoType.Awards.Award> awards = affiliationInfoType.getAwards().getAwards();
        assertEquals(awards.size(), 1);

        AffiliationInfoType.Awards.Award award = affiliationInfoType.getAwards().getAwards().get(0);
        assertEquals(award.getProvider(), "Trustpilot");
        assertEquals(award.getRating(), "4.5");
        assertEquals(award.getRatingSymbol(), "Star");
        assertEquals(award.isOfficialAppointmentInd(), Boolean.TRUE);
    }

    /**
     * This test checks if all possible attributes and direct child elements of the
     * OTA AffiliationInfo element are covered during conversion.
     *
     * @throws JAXBException if there was an error during conversion
     */
    @Test
    public void testToHotelInfoType_coverAllElementsAndAttributes() throws JAXBException {
        String filename = "v_2018_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo_affiliation_info_cover_all.xml";
        XmlToObjectConverter<OTAHotelDescriptiveContentNotifRQ> xmlConverter = new JAXBXmlToObjectConverter
                .Builder<>(OTAHotelDescriptiveContentNotifRQ.class)
                .build();
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ = xmlConverter.toObject(inputXmlStream);

        AffiliationInfoConverter affiliationInfoConverter = AffiliationInfoConverter.newInstance();
        AffiliationInfoType affiliationInfoType = affiliationInfoConverter.toAffiliationInfoType(
                otaHotelDescriptiveContentNotifRQ
                        .getHotelDescriptiveContents()
                        .getHotelDescriptiveContent()
        );

        assertNotNull(affiliationInfoType.getAwards());
        assertNotNull(affiliationInfoType.getBrands());
        assertNotNull(affiliationInfoType.getDistribSystems());
        assertNotNull(affiliationInfoType.getLoyalPrograms());
        assertNotNull(affiliationInfoType.getPartnerInfos());
        assertNotNull(affiliationInfoType.getDescriptions());

        assertNotNull(affiliationInfoType.getLastUpdated());
    }

    /**
     * This test checks if the OTA extension for Inventory -> AffiliationInfos
     * works properly when setting OTA values.
     * <p>
     * First, it reads a default AlpineBits Inventory XML and converts it to an
     * OTAHotelDescriptiveContentNotifRQ object. This object is then extended with
     * AffiliationInfo and then converted back to an XML. That XML is then compared
     * to another pre-defined XML, named "expected" XML.
     *
     * @throws JAXBException                if there was an error during the marshalling / unmarshalling
     * @throws UnsupportedEncodingException if a ByteArrayOutputStream to String conversion failed
     */
    @Test
    public void testApplyAffiliationInfo() throws JAXBException, UnsupportedEncodingException {
        // Read default Inventory XML and convert it to an OTAHotelDescriptiveContentNotifRQ object
        String defaultInventoryFilename = "v_2018_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo.xml";
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2018-10");
        XmlToObjectConverter<OTAHotelDescriptiveContentNotifRQ> xmlToObjectConverter = new JAXBXmlToObjectConverter
                .Builder<>(OTAHotelDescriptiveContentNotifRQ.class)
                .schema(schema)
                .build();
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(defaultInventoryFilename);
        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ = xmlToObjectConverter.toObject(inputXmlStream);

        // Apply an AffiliationInfoType to the OTAHotelDescriptiveContentNotifRQ
        AffiliationInfoType affiliationInfoType = getAffiliationInfoType();
        AffiliationInfoConverter affiliationInfoConverter = AffiliationInfoConverter.newInstance();
        affiliationInfoConverter.applyAffiliationInfo(
                otaHotelDescriptiveContentNotifRQ.getHotelDescriptiveContents().getHotelDescriptiveContent(),
                affiliationInfoType
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
        String expectedXmlFilename = "v_2018_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo_affiliation_info.xml";
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

    private AffiliationInfoType getAffiliationInfoType() {
        AffiliationInfoType.Awards.Award award = new AffiliationInfoType.Awards.Award();
        award.setOfficialAppointmentInd(true);
        award.setProvider("Trustpilot");
        award.setRating("4.5");
        award.setRatingSymbol("Star");

        AffiliationInfoType.Awards awards = new AffiliationInfoType.Awards();
        awards.getAwards().add(award);

        AffiliationInfoType affiliationInfoType = new AffiliationInfoType();
        affiliationInfoType.setAwards(awards);
        return affiliationInfoType;
    }

}