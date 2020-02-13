/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.guestrequests;

import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.readrq.GuestRequestsReadRequest;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.GuestRequestsMapperInstances;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAReadRQ;
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
public class GuestRequestsReadRequestMappingTest {

    @DataProvider(name = "xmlValid")
    public static Object[][] badBasicAuthentication() {
        return new Object[][]{
                {"GuestRequests-OTA_ReadRQ.xml"},
        };
    }

    @Test(dataProvider = "xmlValid")
    public void fullConversion(String xmlFile) throws Exception {
        String filename = "examples/v_2018_10/" + xmlFile;
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2018-10");
        XmlToObjectConverter<OTAReadRQ> converter = this.validatingXmlToObjectConverter(OTAReadRQ.class, schema);

        OTAReadRQ otaReadRQ = converter.toObject(inputXmlStream);

        GuestRequestsReadRequest guestRequestsReadRequest =
                GuestRequestsMapperInstances.HOTEL_RESERVATION_READ_REQUEST_MAPPER.toRequestResult(otaReadRQ, null);
        assertNotNull(guestRequestsReadRequest);

        OTAReadRQ otaReadRQ2 = GuestRequestsMapperInstances.HOTEL_RESERVATION_READ_REQUEST_MAPPER.toOTAReadRQ(guestRequestsReadRequest, null);
        assertNotNull(otaReadRQ2);

        GuestRequestsReadRequest guestRequestsReadRequest2 =
                GuestRequestsMapperInstances.HOTEL_RESERVATION_READ_REQUEST_MAPPER.toRequestResult(otaReadRQ2, null);
        assertNotNull(guestRequestsReadRequest2);

        assertEquals(guestRequestsReadRequest2.toString(), guestRequestsReadRequest.toString());

        ObjectToXmlConverter<OTAReadRQ> toObjectConverter = this.validatingObjectToXmlConverter(OTAReadRQ.class, schema);

        toObjectConverter.toXml(otaReadRQ2, new ByteArrayOutputStream());
    }

    private <T> XmlToObjectConverter<T> validatingXmlToObjectConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBXmlToObjectConverter.Builder<>(classToBeBound).schema(schema).build();
    }

    private <T> ObjectToXmlConverter<T> validatingObjectToXmlConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBObjectToXmlConverter.Builder<>(classToBeBound).schema(schema).build();
    }

}