/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveContentNotifRequest;
import it.bz.opendatahub.alpinebits.mapping.mapper.InventoryMapperInstances;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ;
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
public class HotelDescriptiveContentNotifRequestMappingTest {

    @DataProvider(name = "xmlValid")
    public static Object[][] xmlFiles() {
        return new Object[][]{
                {"Inventory-OTA_HotelDescriptiveContentNotifRQ.xml"},
                {"Inventory-OTA_HotelDescriptiveContentNotifRQ-delete-all.xml"},
                {"Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo.xml"},
        };
    }

    @Test(dataProvider = "xmlValid")
    public void fullConversion(String xmlFile) throws Exception {
        String filename = "examples/v_2017_10/" + xmlFile;
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTAHotelDescriptiveContentNotifRQ> converter = this.validatingXmlToObjectConverter(
                OTAHotelDescriptiveContentNotifRQ.class, schema);

        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ = converter.toObject(inputXmlStream);

        HotelDescriptiveContentNotifRequest hotelDescriptiveContentNotifRequest =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_CONTENT_NOTIF_REQUEST_MAPPER
                        .toHotelDescriptiveContentNotifRequest(otaHotelDescriptiveContentNotifRQ);
        assertNotNull(hotelDescriptiveContentNotifRequest);

        OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ2 =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_CONTENT_NOTIF_REQUEST_MAPPER
                        .toOTAHotelDescriptiveContentNotifRQ(hotelDescriptiveContentNotifRequest);
        assertNotNull(otaHotelDescriptiveContentNotifRQ2);

        HotelDescriptiveContentNotifRequest hotelDescriptiveContentNotifRequest2 =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_CONTENT_NOTIF_REQUEST_MAPPER
                        .toHotelDescriptiveContentNotifRequest(otaHotelDescriptiveContentNotifRQ2);
        assertNotNull(hotelDescriptiveContentNotifRequest2);

        assertEquals(hotelDescriptiveContentNotifRequest2.toString(), hotelDescriptiveContentNotifRequest.toString());

        ObjectToXmlConverter<OTAHotelDescriptiveContentNotifRQ> toObjectConverter = this.validatingObjectToXmlConverter(
                OTAHotelDescriptiveContentNotifRQ.class, schema);

        toObjectConverter.toXml(otaHotelDescriptiveContentNotifRQ2, new ByteArrayOutputStream());
    }

    private <T> XmlToObjectConverter<T> validatingXmlToObjectConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBXmlToObjectConverter.Builder(classToBeBound).schema(schema).build();
    }

    private <T> ObjectToXmlConverter<T> validatingObjectToXmlConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBObjectToXmlConverter.Builder(classToBeBound).schema(schema).build();
    }

}