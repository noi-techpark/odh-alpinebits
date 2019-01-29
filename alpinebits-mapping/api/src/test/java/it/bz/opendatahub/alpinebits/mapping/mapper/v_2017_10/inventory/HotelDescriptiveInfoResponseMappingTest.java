/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoResponse;
import it.bz.opendatahub.alpinebits.mapping.mapper.InventoryMapperInstances;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveInfoRS;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * This class tests the mapper of AlpineBits objects
 * to business objects.
 */
public class HotelDescriptiveInfoResponseMappingTest {

    @DataProvider(name = "xmlValid")
    public static Object[][] xmlFiles() {
        return new Object[][]{
                {"Inventory-OTA_HotelDescriptiveInfoRS-basicpullRS.xml"},
                {"Inventory-OTA_HotelDescriptiveInfoRS-hotelinfo.xml"},
        };
    }

    @Test(dataProvider = "xmlValid")
    public void fullConversion(String xmlFile) throws Exception {
        String filename = "examples/v_2017_10/" + xmlFile;
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTAHotelDescriptiveInfoRS> converter = this.validatingXmlToObjectConverter(
                OTAHotelDescriptiveInfoRS.class, schema);

        OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = converter.toObject(inputXmlStream);

        HotelDescriptiveInfoResponse hotelDescriptiveInfoResponse =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_INFO_RESPONSE_MAPPER
                        .toHotelDescriptiveInfoResponse(otaHotelDescriptiveInfoRS);
        assertNotNull(hotelDescriptiveInfoResponse);

        OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS2 =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_INFO_RESPONSE_MAPPER
                        .toOTAHotelDescriptiveInfoRS(hotelDescriptiveInfoResponse);
        assertNotNull(otaHotelDescriptiveInfoRS2);

        HotelDescriptiveInfoResponse hotelDescriptiveInfoResponse2 =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_INFO_RESPONSE_MAPPER
                        .toHotelDescriptiveInfoResponse(otaHotelDescriptiveInfoRS2);
        assertNotNull(hotelDescriptiveInfoResponse2);

        assertEquals(hotelDescriptiveInfoResponse2.toString(), hotelDescriptiveInfoResponse.toString());

        ObjectToXmlConverter<OTAHotelDescriptiveInfoRS> toObjectConverter = this.validatingObjectToXmlConverter(
                OTAHotelDescriptiveInfoRS.class, schema);

        toObjectConverter.toXml(otaHotelDescriptiveInfoRS2, new ByteArrayOutputStream());
    }

    private <T> XmlToObjectConverter<T> validatingXmlToObjectConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBXmlToObjectConverter.Builder(classToBeBound).schema(schema).build();
    }

    private <T> ObjectToXmlConverter<T> validatingObjectToXmlConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBObjectToXmlConverter.Builder(classToBeBound).schema(schema).build();
    }

}