/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.mapper.v_2017_10.guestrequests;

import it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers.GuestRequestsReadResponse;
import it.bz.idm.alpinebits.mapping.mapper.GuestRequestsMapperInstances;
import it.bz.idm.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.idm.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.idm.alpinebits.xml.ObjectToXmlConverter;
import it.bz.idm.alpinebits.xml.XmlToObjectConverter;
import it.bz.idm.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAResRetrieveRS;
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
public class GuestRequestsReadResponseMappingTest {

    @DataProvider(name = "xmlValid")
    public static Object[][] badBasicAuthentication() {
        return new Object[][]{
                {"GuestRequests-OTA_ResRetrieveRS-cancellation.xml"},
                {"GuestRequests-OTA_ResRetrieveRS-error.xml"},
                {"GuestRequests-OTA_ResRetrieveRS-reservation.xml"},
                {"GuestRequests-OTA_ResRetrieveRS-reservation-with-duration.xml"},
                {"GuestRequests-OTA_ResRetrieveRS-reservation-empty.xml"},
        };
    }

    @Test(dataProvider = "xmlValid")
    public void fullConversion(String xmlFile) throws Exception {
        String filename = "examples/v_2017_10/" + xmlFile;
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTAResRetrieveRS> converter = this.validatingXmlToObjectConverter(OTAResRetrieveRS.class, schema);

        OTAResRetrieveRS otaResRetrieveRS = converter.toObject(inputXmlStream);

        GuestRequestsReadResponse guestRequestsReadResponse =
                GuestRequestsMapperInstances.HOTEL_RESERVATION_READ_RESPONSE_MAPPER.toHotelReservationReadResult(otaResRetrieveRS);
        assertNotNull(guestRequestsReadResponse);

        OTAResRetrieveRS otaResRetrieveRS2 =
                GuestRequestsMapperInstances.HOTEL_RESERVATION_READ_RESPONSE_MAPPER.toOTAResRetrieveRS(guestRequestsReadResponse);
        assertNotNull(otaResRetrieveRS2);

        GuestRequestsReadResponse guestRequestsReadResponse2 =
                GuestRequestsMapperInstances.HOTEL_RESERVATION_READ_RESPONSE_MAPPER.toHotelReservationReadResult(otaResRetrieveRS2);
        assertNotNull(guestRequestsReadResponse2);

        assertEquals(guestRequestsReadResponse2.toString(), guestRequestsReadResponse.toString());

        ObjectToXmlConverter<OTAResRetrieveRS> toObjectConverter = this.validatingObjectToXmlConverter(OTAResRetrieveRS.class, schema);

        toObjectConverter.toXml(otaResRetrieveRS2, new ByteArrayOutputStream());
    }

    private <T> XmlToObjectConverter<T> validatingXmlToObjectConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBXmlToObjectConverter.Builder(classToBeBound).schema(schema).build();
    }

    private <T> ObjectToXmlConverter<T> validatingObjectToXmlConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBObjectToXmlConverter.Builder(classToBeBound).schema(schema).build();
    }

}