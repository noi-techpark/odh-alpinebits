/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.otaextensions;

import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.AffiliationInfoType;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * Test cases for {@link ConverterUtil} class.
 */
public class ConverterUtilTest {

    @Test
    public void testConvertToElements_nodeListIsNull() {
        List<Element> elements = ConverterUtil.convertToElements(null);
        assertTrue(elements.isEmpty());
    }

    @Test
    public void testConvertToElements_oneElement() throws ParserConfigurationException, IOException, SAXException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><a></a>";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xmlString)));

        List<Element> elements = ConverterUtil.convertToElements(document.getChildNodes());
        assertEquals(elements.size(), 1);
        assertEquals(elements.get(0).getTagName(), "a");
    }

    @Test
    public void testConvertToAttributeMap_namedNodeMapIsNull() {
        Map<QName, String> attributeMap = ConverterUtil.convertToAttributeMap(null);
        assertTrue(attributeMap.isEmpty());
    }

    @Test
    public void testConvertToAttributeMap_namedNodeMapOneElement() throws ParserConfigurationException, IOException, SAXException {
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><a testattr=\"some value\"></a>";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xmlString)));

        NamedNodeMap namedNodeMap = document.getChildNodes().item(0).getAttributes();
        Map<QName, String> attributeMap = ConverterUtil.convertToAttributeMap(namedNodeMap);
        assertEquals(attributeMap.size(), 1);
        assertEquals(attributeMap.get(QName.valueOf("testattr")), "some value");
    }

    @Test
    public void testUnmarshallElement() throws JAXBException {
        String defaultInventoryFilename = "v_2017_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo_affiliation_info.xml";
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTAHotelDescriptiveContentNotifRQ> xmlToObjectConverter = new JAXBXmlToObjectConverter
                .Builder<>(OTAHotelDescriptiveContentNotifRQ.class)
                .schema(schema)
                .build();
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(defaultInventoryFilename);
        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ = xmlToObjectConverter.toObject(inputXmlStream);

        JAXBContext jaxbContext = JAXBContext.newInstance(
                AffiliationInfoType.class
        );
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        AffiliationInfoType.Awards awards = ConverterUtil.unmarshallElement(
                unmarshaller,
                otaHotelDescriptiveContentNotifRQ
                        .getHotelDescriptiveContents()
                        .getHotelDescriptiveContent()
                        .getAffiliationInfo()
                        .getAnies()
                        .get(0),
                AffiliationInfoType.Awards.class
        );

        assertNotNull(awards);

        AffiliationInfoType.Awards.Award award = awards.getAwards().get(0);
        assertEquals(award.isOfficialAppointmentInd(), Boolean.TRUE);
        assertEquals(award.getProvider(), "Trustpilot");
        assertEquals(award.getRating(), "4.5");
        assertEquals(award.getRatingSymbol(), "Star");
    }

    @Test
    public void testMarshallToElement() throws JAXBException {
        AffiliationInfoType.Awards.Award award = new AffiliationInfoType.Awards.Award();
        award.setOfficialAppointmentInd(true);
        award.setProvider("Trustpilot");
        award.setRating("4.5");
        award.setRatingSymbol("Star");

        AffiliationInfoType.Awards awards = new AffiliationInfoType.Awards();
        awards.getAwards().add(award);

        AffiliationInfoType affiliationInfoType = new AffiliationInfoType();
        affiliationInfoType.setAwards(awards);

        JAXBContext jaxbContext = JAXBContext.newInstance(
                AffiliationInfoType.class
        );
        Marshaller marshaller = jaxbContext.createMarshaller();

        Element element = ConverterUtil.marshallToElement(
                marshaller,
                "AffiliationInfo",
                AffiliationInfoType.class,
                affiliationInfoType
        );

        assertEquals(element.getTagName(), "AffiliationInfo");

        assertNotNull(element.getFirstChild());
        assertEquals(element.getFirstChild().getNodeName(), "Awards");

        assertNotNull(element.getFirstChild().getFirstChild());
        assertEquals(element.getFirstChild().getFirstChild().getNodeName(), "Award");

        Element awardElement = (Element) element.getFirstChild().getFirstChild();

        assertEquals(awardElement.getAttribute("OfficialAppointmentInd"), "true");
        assertEquals(awardElement.getAttribute("Provider"), "Trustpilot");
        assertEquals(awardElement.getAttribute("Rating"), "4.5");
        assertEquals(awardElement.getAttribute("RatingSymbol"), "Star");
    }

    @Test
    public void testIsNodeEmpty_aniesNullAndAttributeMapNull() {
        assertTrue(ConverterUtil.isNodeEmpty(null, null));
    }

    @Test
    public void testIsNodeEmpty_aniesEmptyAndAttributeMapEmpty() {
        assertTrue(ConverterUtil.isNodeEmpty(new ArrayList<>(), new HashMap<>()));
    }

    @Test
    public void testIsNodeEmpty_aniesSetAndAttributeMapNull() {
        List<Element> elements = new ArrayList<>();
        elements.add(new TestElement());
        assertFalse(ConverterUtil.isNodeEmpty(elements, new HashMap<>()));
    }

    @Test
    public void testIsNodeEmpty_aniesEmptyAndAttributeMapSet() {
        Map<QName, String> attributeMap = new HashMap<>();
        attributeMap.put(QName.valueOf("some value"), "test");
        assertFalse(ConverterUtil.isNodeEmpty(null, attributeMap));
    }

    @Test
    public void testIsNodeEmpty_aniesSetAndAttributeMapSet() {
        List<Element> elements = new ArrayList<>();
        elements.add(new TestElement());
        Map<QName, String> attributeMap = new HashMap<>();
        attributeMap.put(QName.valueOf("some value"), "test");
        assertFalse(ConverterUtil.isNodeEmpty(elements, attributeMap));
    }
}