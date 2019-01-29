/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.freerooms;

import it.bz.opendatahub.alpinebits.mapping.entity.freerooms.FreeRoomsRequest;
import it.bz.opendatahub.alpinebits.mapping.mapper.FreeRoomsMapperInstances;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ;
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
public class FreeRoomsRequestMappingTest {

    @DataProvider(name = "xmlValid")
    public static Object[][] xmlFiles() {
        return new Object[][]{
                {"FreeRooms-OTA_HotelAvailNotifRQ.xml"},
                {"FreeRooms-OTA_HotelAvailNotifRQ-empty.xml"},
        };
    }

    @Test(dataProvider = "xmlValid")
    public void fullConversion(String xmlFile) throws Exception {
        String filename = "examples/v_2017_10/" + xmlFile;
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTAHotelAvailNotifRQ> converter = this.validatingXmlToObjectConverter(
                OTAHotelAvailNotifRQ.class, schema);

        OTAHotelAvailNotifRQ otaHotelAvailNotifRQ = converter.toObject(inputXmlStream);

        FreeRoomsRequest freeRoomsRequest = FreeRoomsMapperInstances.FREE_ROOMS_REQUEST_MAPPER
                .toFreeRoomsRequest(otaHotelAvailNotifRQ);
        assertNotNull(freeRoomsRequest);

        OTAHotelAvailNotifRQ otaHotelAvailNotifRQ2 = FreeRoomsMapperInstances.FREE_ROOMS_REQUEST_MAPPER
                .toOTAHotelAvailNotifRQ(freeRoomsRequest);
        assertNotNull(otaHotelAvailNotifRQ2);

        FreeRoomsRequest freeRoomsRequest2 = FreeRoomsMapperInstances.FREE_ROOMS_REQUEST_MAPPER
                .toFreeRoomsRequest(otaHotelAvailNotifRQ2);
        assertNotNull(freeRoomsRequest2);

        assertEquals(freeRoomsRequest2.toString(), freeRoomsRequest.toString());

        ObjectToXmlConverter<OTAHotelAvailNotifRQ> toObjectConverter = this.validatingObjectToXmlConverter(
                OTAHotelAvailNotifRQ.class, schema);

        toObjectConverter.toXml(otaHotelAvailNotifRQ2, new ByteArrayOutputStream());
    }

    private <T> XmlToObjectConverter<T> validatingXmlToObjectConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBXmlToObjectConverter.Builder<>(classToBeBound).schema(schema).build();
    }

    private <T> ObjectToXmlConverter<T> validatingObjectToXmlConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBObjectToXmlConverter.Builder<>(classToBeBound).schema(schema).build();
    }

}