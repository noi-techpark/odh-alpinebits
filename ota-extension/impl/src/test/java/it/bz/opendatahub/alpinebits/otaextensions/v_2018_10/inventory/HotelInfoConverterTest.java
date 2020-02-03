/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.otaextensions.v_2018_10.inventory;

import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.CategoryCodesType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelInfoType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ImageDescriptionType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ImageItemsType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.MultimediaDescriptionType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.TextDescriptionType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.TextItemsType;
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
 * Test cases for {@link HotelInfoConverter} class.
 */
public class HotelInfoConverterTest {

    @Test
    public void testToHotelInfoType_hotelDescriptiveContentNull() {
        HotelInfoConverter hotelInfoConverter = HotelInfoConverter.newInstance();
        HotelInfoType hotelInfoType = hotelInfoConverter.toHotelInfoType(null);
        assertNull(hotelInfoType);
    }

    @Test
    public void testToHotelInfoType_hotelInfoNull() {
        HotelInfoConverter hotelInfoConverter = HotelInfoConverter.newInstance();
        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent hotelDescriptiveContent =
                new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent();
        HotelInfoType hotelInfoType = hotelInfoConverter.toHotelInfoType(hotelDescriptiveContent);
        assertNull(hotelInfoType);
    }

    @Test
    public void testToHotelInfoType_hotelInfoEmpty() {
        HotelInfoConverter hotelInfoConverter = HotelInfoConverter.newInstance();
        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent hotelDescriptiveContent =
                new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent();

        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.HotelInfo hotelInfo =
                new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.HotelInfo();
        hotelDescriptiveContent.setHotelInfo(hotelInfo);

        HotelInfoType hotelInfoType = hotelInfoConverter.toHotelInfoType(hotelDescriptiveContent);
        assertNull(hotelInfoType);
    }

    @Test
    public void testToHotelInfoType() throws JAXBException {
        String filename = "v_2018_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo_hotel_info.xml";
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2018-10");
        XmlToObjectConverter<OTAHotelDescriptiveContentNotifRQ> xmlConverter = new JAXBXmlToObjectConverter
                .Builder<>(OTAHotelDescriptiveContentNotifRQ.class)
                .schema(schema)
                .build();
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ = xmlConverter.toObject(inputXmlStream);

        HotelInfoConverter hotelInfoConverter = HotelInfoConverter.newInstance();
        HotelInfoType hotelInfoType = hotelInfoConverter.toHotelInfoType(
                otaHotelDescriptiveContentNotifRQ
                        .getHotelDescriptiveContents()
                        .getHotelDescriptiveContent()
        );

        assertEquals(hotelInfoType.getHotelStatusCode(), "1");

        CategoryCodesType.HotelCategory hotelCategory = hotelInfoType.getCategoryCodes().getHotelCategories().get(0);
        assertEquals(hotelCategory.getCode(), "20");
        assertEquals(hotelCategory.getCodeDetail(), "4S");

        List<MultimediaDescriptionType> multimediaDescriptionTypes = hotelInfoType.getDescriptions().getMultimediaDescriptions().getMultimediaDescriptions();

        MultimediaDescriptionType multimediaDescriptionType1 = multimediaDescriptionTypes.get(0);
        assertEquals(multimediaDescriptionType1.getInfoCode(), "17");
        TextDescriptionType.Description description1 = multimediaDescriptionType1.getTextItems().getTextItems().get(0).getDescriptions().get(0);
        assertEquals(description1.getLanguage(), "en");
        assertEquals(description1.getTextFormat(), "PlainText");
        assertEquals(description1.getValue(), "A short description");

        MultimediaDescriptionType multimediaDescriptionType2 = multimediaDescriptionTypes.get(1);
        assertEquals(multimediaDescriptionType2.getInfoCode(), "1");
        TextDescriptionType.Description description2 = multimediaDescriptionType2.getTextItems().getTextItems().get(0).getDescriptions().get(0);
        assertEquals(description2.getLanguage(), "en");
        assertEquals(description2.getTextFormat(), "HTML");
        assertEquals(description2.getValue(), "A long description");

        MultimediaDescriptionType multimediaDescriptionType3 = multimediaDescriptionTypes.get(2);
        ImageItemsType.ImageItem imageItem1 = multimediaDescriptionType3.getImageItems().getImageItems().get(0);
        assertEquals(imageItem1.getCategory(), "15");
        assertEquals(imageItem1.getImageFormats().get(0).getCopyrightNotice(), "Image copyright");
        assertEquals(imageItem1.getImageFormats().get(0).getURL(), "https://..../HotelLogo.jpg");

        ImageItemsType.ImageItem imageItem2 = multimediaDescriptionType3.getImageItems().getImageItems().get(1);
        assertEquals(imageItem2.getCategory(), "1");
        assertEquals(imageItem2.getImageFormats().get(0).getCopyrightNotice(), "Image copyright");
        assertEquals(imageItem2.getImageFormats().get(0).getApplicableStart(), "--12-01");
        assertEquals(imageItem2.getImageFormats().get(0).getApplicableEnd(), "--03-30");
        assertEquals(imageItem2.getImageFormats().get(0).getURL(), "https://..../HotelExteriorWinterView.jpg");
        assertEquals(imageItem2.getDescriptions().get(0).getTextFormat(), "PlainText");
        assertEquals(imageItem2.getDescriptions().get(0).getLanguage(), "en");
        assertEquals(imageItem2.getDescriptions().get(0).getValue(), "Image description");

        assertEquals(hotelInfoType.getPosition().getAltitude(), "200");
        assertEquals(hotelInfoType.getPosition().getLatitude(), "11.23334");
        assertEquals(hotelInfoType.getPosition().getLongitude(), "42.34543");

        assertEquals(hotelInfoType.getServices().getServices().get(0).getCode(), "223");
        assertEquals(hotelInfoType.getServices().getServices().get(1).getCode(), "165");
        assertEquals(hotelInfoType.getServices().getServices().get(2).getCode(), "224");
    }

    /**
     * This test checks if all possible attributes and direct child elements of the
     * OTA HotelInfo element are covered during conversion.
     *
     * @throws JAXBException if there was an error during conversion
     */
    @Test
    public void testToHotelInfoType_coverAllElementsAndAttributes() throws JAXBException {
        String filename = "v_2018_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo_hotel_info_cover_all.xml";
        XmlToObjectConverter<OTAHotelDescriptiveContentNotifRQ> xmlConverter = new JAXBXmlToObjectConverter
                .Builder<>(OTAHotelDescriptiveContentNotifRQ.class)
                .build();
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ = xmlConverter.toObject(inputXmlStream);

        HotelInfoConverter hotelInfoConverter = HotelInfoConverter.newInstance();
        HotelInfoType hotelInfoType = hotelInfoConverter.toHotelInfoType(
                otaHotelDescriptiveContentNotifRQ
                        .getHotelDescriptiveContents()
                        .getHotelDescriptiveContent()
        );

        assertNotNull(hotelInfoType.getHotelName());
        assertNotNull(hotelInfoType.getClosedSeasons());
        assertNotNull(hotelInfoType.getBlackoutDates());
        assertNotNull(hotelInfoType.getRelativePositions());
        assertNotNull(hotelInfoType.getCategoryCodes());
        assertNotNull(hotelInfoType.getDescriptions());
        assertNotNull(hotelInfoType.getHotelInfoCodes());
        assertNotNull(hotelInfoType.getPosition());
        assertNotNull(hotelInfoType.getServices());
        assertNotNull(hotelInfoType.getWeatherInfos());
        assertNotNull(hotelInfoType.getOwnershipManagementInfos());
        assertNotNull(hotelInfoType.getLanguages());

        assertNotNull(hotelInfoType.getWhenBuilt());
        assertNotNull(hotelInfoType.getLastUpdated());
        assertNotNull(hotelInfoType.getAreaWeather());
        assertNotNull(hotelInfoType.getInterfaceCompliance());
        assertNotNull(hotelInfoType.getPMSSystem());
        assertNotNull(hotelInfoType.getHotelStatus());
        assertNotNull(hotelInfoType.getHotelStatusCode());
        assertNotNull(hotelInfoType.getTaxID());
        assertNotNull(hotelInfoType.isDaylightSavingIndicator());
        assertNotNull(hotelInfoType.isISO9000CertifiedInd());
        assertNotNull(hotelInfoType.getStart());
        assertNotNull(hotelInfoType.getDuration());
        assertNotNull(hotelInfoType.getEnd());
    }

    /**
     * This test checks if the OTA extension for Inventory -> HotelInfo
     * works properly when setting OTA values.
     * <p>
     * First, it reads a default AlpineBits Inventory XML and converts it to an
     * OTAHotelDescriptiveContentNotifRQ object. This object is then extended with
     * HotelInfo and then converted back to an XML. That XML is then compared
     * to another pre-defined XML, named "expected" XML.
     *
     * @throws JAXBException                if there was an error during the marshalling / unmarshalling
     * @throws UnsupportedEncodingException if a ByteArrayOutputStream to String conversion failed
     */
    @Test
    public void testApplyHotelInfo() throws JAXBException, UnsupportedEncodingException {
        // Read default Inventory XML and convert it to an OTAHotelDescriptiveContentNotifRQ object
        String defaultInventoryFilename = "v_2018_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo.xml";
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2018-10");
        XmlToObjectConverter<OTAHotelDescriptiveContentNotifRQ> xmlToObjectConverter = new JAXBXmlToObjectConverter
                .Builder<>(OTAHotelDescriptiveContentNotifRQ.class)
                .schema(schema)
                .build();
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(defaultInventoryFilename);
        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ = xmlToObjectConverter.toObject(inputXmlStream);

        // Apply an HotelInfoType to the OTAHotelDescriptiveContentNotifRQ
        HotelInfoType hotelInfoType = getHotelInfoType();
        HotelInfoConverter hotelInfoConverter = HotelInfoConverter.newInstance();
        hotelInfoConverter.applyHotelInfo(
                otaHotelDescriptiveContentNotifRQ.getHotelDescriptiveContents().getHotelDescriptiveContent(),
                hotelInfoType
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
        String expectedXmlFilename = "v_2018_10/Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo_hotel_info.xml";
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

    private HotelInfoType getHotelInfoType() {
        HotelInfoType hotelInfoType = new HotelInfoType();

        hotelInfoType.setHotelStatusCode("1");

        CategoryCodesType categoryCodesType = new CategoryCodesType();
        CategoryCodesType.HotelCategory hotelCategory = new CategoryCodesType.HotelCategory();
        hotelCategory.setCode("20");
        hotelCategory.setCodeDetail("4S");
        categoryCodesType.getHotelCategories().add(hotelCategory);
        hotelInfoType.setCategoryCodes(categoryCodesType);

        HotelInfoType.Descriptions descriptions = new HotelInfoType.Descriptions();
        HotelInfoType.Descriptions.MultimediaDescriptions multimediaDescriptions = new HotelInfoType.Descriptions.MultimediaDescriptions();
        descriptions.setMultimediaDescriptions(multimediaDescriptions);
        hotelInfoType.setDescriptions(descriptions);

        MultimediaDescriptionType multimediaDescriptionType1 = new MultimediaDescriptionType();
        multimediaDescriptionType1.setInfoCode("17");
        TextItemsType textItemsType1 = new TextItemsType();
        TextItemsType.TextItem textItem1 = new TextItemsType.TextItem();
        TextDescriptionType.Description description1 = new TextDescriptionType.Description();
        description1.setLanguage("en");
        description1.setTextFormat("PlainText");
        description1.setValue("A short description");
        textItem1.getDescriptions().add(description1);
        textItemsType1.getTextItems().add(textItem1);
        multimediaDescriptionType1.setTextItems(textItemsType1);
        multimediaDescriptions.getMultimediaDescriptions().add(multimediaDescriptionType1);

        MultimediaDescriptionType multimediaDescriptionType2 = new MultimediaDescriptionType();
        multimediaDescriptionType2.setInfoCode("1");
        TextItemsType textItemsType2 = new TextItemsType();
        TextItemsType.TextItem textItem2 = new TextItemsType.TextItem();
        TextDescriptionType.Description description2 = new TextDescriptionType.Description();
        description2.setLanguage("en");
        description2.setTextFormat("HTML");
        description2.setValue("A long description");
        textItem2.getDescriptions().add(description2);
        textItemsType2.getTextItems().add(textItem2);
        multimediaDescriptionType2.setTextItems(textItemsType2);
        multimediaDescriptions.getMultimediaDescriptions().add(multimediaDescriptionType2);

        MultimediaDescriptionType multimediaDescriptionType3 = new MultimediaDescriptionType();
        ImageItemsType imageItemsType = new ImageItemsType();
        multimediaDescriptionType3.setImageItems(imageItemsType);
        multimediaDescriptionType3.setInfoCode("23");

        ImageItemsType.ImageItem imageItem1 = new ImageItemsType.ImageItem();
        imageItem1.setCategory("15");

        ImageDescriptionType.Description imageDescriptionType1 = new ImageDescriptionType.Description();
        imageDescriptionType1.setTextFormat("PlainText");
        imageDescriptionType1.setLanguage("en");
        imageDescriptionType1.setValue("Image description");
        imageItem1.getDescriptions().add(imageDescriptionType1);

        ImageDescriptionType.ImageFormat imageFormat1 = new ImageDescriptionType.ImageFormat();
        imageFormat1.setCopyrightNotice("Image copyright");
        imageFormat1.setURL("https://..../HotelLogo.jpg");
        imageItem1.getImageFormats().add(imageFormat1);
        imageItemsType.getImageItems().add(imageItem1);

        ImageItemsType.ImageItem imageItem2 = new ImageItemsType.ImageItem();
        imageItem2.setCategory("1");

        ImageDescriptionType.Description imageDescriptionType2 = new ImageDescriptionType.Description();
        imageDescriptionType2.setTextFormat("PlainText");
        imageDescriptionType2.setLanguage("en");
        imageDescriptionType2.setValue("Image description");
        imageItem2.getDescriptions().add(imageDescriptionType2);

        ImageDescriptionType.ImageFormat imageFormat2 = new ImageDescriptionType.ImageFormat();
        imageFormat2.setCopyrightNotice("Image copyright");
        imageFormat2.setApplicableStart("--12-01");
        imageFormat2.setApplicableEnd("--03-30");
        imageFormat2.setURL("https://..../HotelExteriorWinterView.jpg");
        imageItem2.getImageFormats().add(imageFormat2);
        imageItemsType.getImageItems().add(imageItem2);

        multimediaDescriptions.getMultimediaDescriptions().add(multimediaDescriptionType3);

        HotelInfoType.Position position = new HotelInfoType.Position();
        position.setAltitude("200");
        position.setLatitude("11.23334");
        position.setLongitude("42.34543");
        hotelInfoType.setPosition(position);

        HotelInfoType.Services services = new HotelInfoType.Services();
        HotelInfoType.Services.Service service1 = new HotelInfoType.Services.Service();
        service1.setCode("223");
        services.getServices().add(service1);
        HotelInfoType.Services.Service service2 = new HotelInfoType.Services.Service();
        service2.setCode("165");
        services.getServices().add(service2);
        HotelInfoType.Services.Service service3 = new HotelInfoType.Services.Service();
        service3.setCode("224");
        services.getServices().add(service3);
        hotelInfoType.setServices(services);

        return hotelInfoType;
    }
}