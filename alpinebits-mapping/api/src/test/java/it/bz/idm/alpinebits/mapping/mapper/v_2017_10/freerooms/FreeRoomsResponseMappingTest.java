/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.mapper.v_2017_10.freerooms;

import it.bz.idm.alpinebits.mapping.entity.freerooms.FreeRoomsResponse;
import it.bz.idm.alpinebits.mapping.mapper.FreeRoomsMapperInstances;
import it.bz.idm.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.idm.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.idm.alpinebits.xml.ObjectToXmlConverter;
import it.bz.idm.alpinebits.xml.XmlToObjectConverter;
import it.bz.idm.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRS;
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
public class FreeRoomsResponseMappingTest {

    @DataProvider(name = "xmlValid")
    public static Object[][] xmlFiles() {
        return new Object[][]{
                {"FreeRooms-OTA_HotelAvailNotifRS-advisory.xml"},
                {"FreeRooms-OTA_HotelAvailNotifRS-error.xml"},
                {"FreeRooms-OTA_HotelAvailNotifRS-success.xml"},
                {"FreeRooms-OTA_HotelAvailNotifRS-warning.xml"},
        };
    }

    @Test(dataProvider = "xmlValid")
    public void fullConversion(String xmlFile) throws Exception {
        String filename = "examples/v_2017_10/" + xmlFile;
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTAHotelAvailNotifRS> converter = this.validatingXmlToObjectConverter(
                OTAHotelAvailNotifRS.class, schema);

        OTAHotelAvailNotifRS otaHotelAvailNotifRS = converter.toObject(inputXmlStream);

        FreeRoomsResponse freeRoomsResponse = FreeRoomsMapperInstances.FREE_ROOMS_RESPONSE_MAPPER
                .toFreeRoomsResponse(otaHotelAvailNotifRS);
        assertNotNull(freeRoomsResponse);

        OTAHotelAvailNotifRS otaHotelAvailNotifRS2 = FreeRoomsMapperInstances.FREE_ROOMS_RESPONSE_MAPPER
                .toOTAHotelAvailNotifRS(freeRoomsResponse);
        assertNotNull(otaHotelAvailNotifRS2);

        FreeRoomsResponse freeRoomsResponse2 = FreeRoomsMapperInstances.FREE_ROOMS_RESPONSE_MAPPER
                .toFreeRoomsResponse(otaHotelAvailNotifRS2);
        assertNotNull(freeRoomsResponse2);

        assertEquals(freeRoomsResponse2.toString(), freeRoomsResponse.toString());

        ObjectToXmlConverter<OTAHotelAvailNotifRS> toObjectConverter = this.validatingObjectToXmlConverter(
                OTAHotelAvailNotifRS.class, schema);

        toObjectConverter.toXml(otaHotelAvailNotifRS2, new ByteArrayOutputStream());
    }

    private <T> XmlToObjectConverter<T> validatingXmlToObjectConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBXmlToObjectConverter.Builder(classToBeBound).schema(schema).build();
    }

    private <T> ObjectToXmlConverter<T> validatingObjectToXmlConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBObjectToXmlConverter.Builder(classToBeBound).schema(schema).build();
    }

}