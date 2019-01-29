/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebits.mapping.mapper.InventoryMapperInstances;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRS;
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
public class HotelDescriptiveContentNotifResponseMappingTest {

    @DataProvider(name = "xmlValid")
    public static Object[][] xmlFiles() {
        return new Object[][]{
                {"Inventory-OTA_HotelDescriptiveContentNotifRS-advisory.xml"},
                {"Inventory-OTA_HotelDescriptiveContentNotifRS-error.xml"},
                {"Inventory-OTA_HotelDescriptiveContentNotifRS-success.xml"},
                {"Inventory-OTA_HotelDescriptiveContentNotifRS-warning.xml"},
        };
    }

    @Test(dataProvider = "xmlValid")
    public void fullConversion(String xmlFile) throws Exception {
        String filename = "examples/v_2017_10/" + xmlFile;
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTAHotelDescriptiveContentNotifRS> converter = this.validatingXmlToObjectConverter(
                OTAHotelDescriptiveContentNotifRS.class, schema);

        OTAHotelDescriptiveContentNotifRS otaHotelDescriptiveContentNotifRS = converter.toObject(inputXmlStream);

        GenericResponse genericResponse =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_CONTENT_NOTIF_RESPONSE_MAPPER
                        .toGenericResponse(otaHotelDescriptiveContentNotifRS);
        assertNotNull(genericResponse);

        OTAHotelDescriptiveContentNotifRS otaHotelDescriptiveContentNotifRS2 =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_CONTENT_NOTIF_RESPONSE_MAPPER
                        .toOTAHotelDescriptiveContentNotifRS(genericResponse);
        assertNotNull(otaHotelDescriptiveContentNotifRS2);

        GenericResponse genericResponse2 =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_CONTENT_NOTIF_RESPONSE_MAPPER
                        .toGenericResponse(otaHotelDescriptiveContentNotifRS2);
        assertNotNull(genericResponse2);

        assertEquals(genericResponse2.toString(), genericResponse.toString());

        ObjectToXmlConverter<OTAHotelDescriptiveContentNotifRS> toObjectConverter = this.validatingObjectToXmlConverter(
                OTAHotelDescriptiveContentNotifRS.class, schema);

        toObjectConverter.toXml(otaHotelDescriptiveContentNotifRS2, new ByteArrayOutputStream());
    }

    private <T> XmlToObjectConverter<T> validatingXmlToObjectConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBXmlToObjectConverter.Builder(classToBeBound).schema(schema).build();
    }

    private <T> ObjectToXmlConverter<T> validatingObjectToXmlConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBObjectToXmlConverter.Builder(classToBeBound).schema(schema).build();
    }

}