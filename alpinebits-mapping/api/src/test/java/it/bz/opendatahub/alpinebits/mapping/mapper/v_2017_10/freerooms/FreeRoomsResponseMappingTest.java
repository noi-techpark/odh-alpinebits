/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.freerooms;

import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebits.mapping.mapper.FreeRoomsMapperInstances;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRS;
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

        GenericResponse genericResponse = FreeRoomsMapperInstances.FREE_ROOMS_RESPONSE_MAPPER
                .toGenericResponse(otaHotelAvailNotifRS, null);
        assertNotNull(genericResponse);

        OTAHotelAvailNotifRS otaHotelAvailNotifRS2 = FreeRoomsMapperInstances.FREE_ROOMS_RESPONSE_MAPPER
                .toOTAHotelAvailNotifRS(genericResponse, null);
        assertNotNull(otaHotelAvailNotifRS2);

        GenericResponse genericResponse2 = FreeRoomsMapperInstances.FREE_ROOMS_RESPONSE_MAPPER
                .toGenericResponse(otaHotelAvailNotifRS2, null);
        assertNotNull(genericResponse2);

        assertEquals(genericResponse2.toString(), genericResponse.toString());

        ObjectToXmlConverter<OTAHotelAvailNotifRS> toObjectConverter = this.validatingObjectToXmlConverter(
                OTAHotelAvailNotifRS.class, schema);

        toObjectConverter.toXml(otaHotelAvailNotifRS2, new ByteArrayOutputStream());
    }

    private <T> XmlToObjectConverter<T> validatingXmlToObjectConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBXmlToObjectConverter.Builder<>(classToBeBound).schema(schema).build();
    }

    private <T> ObjectToXmlConverter<T> validatingObjectToXmlConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBObjectToXmlConverter.Builder<>(classToBeBound).schema(schema).build();
    }

}