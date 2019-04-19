/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.guestrequests;

import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.notifreportrq.GuestRequestsConfirmationRequest;
import it.bz.opendatahub.alpinebits.mapping.mapper.GuestRequestsMapperInstances;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTANotifReportRQ;
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
public class GuestRequestsConfirmationRequestMappingTest {

    @DataProvider(name = "xmlValid")
    public static Object[][] badBasicAuthentication() {
        return new Object[][]{
                {"GuestRequests-OTA_ReadRQ-ack.xml"},
        };
    }

    @Test(dataProvider = "xmlValid")
    public void fullConversion(String xmlFile) throws Exception {
        String filename = "examples/v_2017_10/" + xmlFile;
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTANotifReportRQ> converter = this.validatingXmlToObjectConverter(OTANotifReportRQ.class, schema);

        OTANotifReportRQ otaNotifReportRQ = converter.toObject(inputXmlStream);

        GuestRequestsConfirmationRequest guestRequestsConfirmationRequest =
                GuestRequestsMapperInstances.HOTEL_RESERVATION_CONFIRMATION_REQUEST_MAPPER.toHotelReservationConfirmationRequest(otaNotifReportRQ, null);
        assertNotNull(guestRequestsConfirmationRequest);

        OTANotifReportRQ otaNotifReportRQ2 =
                GuestRequestsMapperInstances.HOTEL_RESERVATION_CONFIRMATION_REQUEST_MAPPER.toOTANotifReportRQ(guestRequestsConfirmationRequest, null);
        assertNotNull(otaNotifReportRQ2);

        GuestRequestsConfirmationRequest guestRequestsConfirmationRequest2 =
                GuestRequestsMapperInstances.HOTEL_RESERVATION_CONFIRMATION_REQUEST_MAPPER.toHotelReservationConfirmationRequest(otaNotifReportRQ2, null);
        assertNotNull(guestRequestsConfirmationRequest2);

        assertEquals(guestRequestsConfirmationRequest2.toString(), guestRequestsConfirmationRequest.toString());

        ObjectToXmlConverter<OTANotifReportRQ> toObjectConverter = this.validatingObjectToXmlConverter(OTANotifReportRQ.class, schema);

        toObjectConverter.toXml(otaNotifReportRQ2, new ByteArrayOutputStream());
    }

    private <T> XmlToObjectConverter<T> validatingXmlToObjectConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBXmlToObjectConverter.Builder<>(classToBeBound).schema(schema).build();
    }

    private <T> ObjectToXmlConverter<T> validatingObjectToXmlConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBObjectToXmlConverter.Builder<>(classToBeBound).schema(schema).build();
    }

}