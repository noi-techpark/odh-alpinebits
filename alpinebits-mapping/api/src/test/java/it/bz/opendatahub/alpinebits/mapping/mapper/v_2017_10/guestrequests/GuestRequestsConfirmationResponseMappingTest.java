/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.guestrequests;

import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.notifreportrs.GuestRequestsConfirmationResponse;
import it.bz.opendatahub.alpinebits.mapping.mapper.GuestRequestsMapperInstances;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTANotifReportRS;
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
public class GuestRequestsConfirmationResponseMappingTest {

    @DataProvider(name = "xmlValid")
    public static Object[][] badBasicAuthentication() {
        return new Object[][]{
                {"GuestRequests-OTA_NotifReportRS-error.xml"},
                {"GuestRequests-OTA_NotifReportRS-success.xml"},
        };
    }

    @Test(dataProvider = "xmlValid")
    public void fullConversion(String xmlFile) throws Exception {
        String filename = "examples/v_2017_10/" + xmlFile;
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTANotifReportRS> converter = this.validatingXmlToObjectConverter(OTANotifReportRS.class, schema);

        OTANotifReportRS otaNotifReportRS = converter.toObject(inputXmlStream);

        GuestRequestsConfirmationResponse guestRequestsConfirmationResponse =
                GuestRequestsMapperInstances.HOTEL_RESERVATION_CONFIRMATION_RESPONSE_MAPPER.toHotelReservationConfirmationResponse(otaNotifReportRS);
        assertNotNull(guestRequestsConfirmationResponse);

        OTANotifReportRS otaNotifReportRS2 =
                GuestRequestsMapperInstances.HOTEL_RESERVATION_CONFIRMATION_RESPONSE_MAPPER.toOTANotifReportRS(guestRequestsConfirmationResponse);
        assertNotNull(otaNotifReportRS2);

        GuestRequestsConfirmationResponse guestRequestsConfirmationResponse2 =
                GuestRequestsMapperInstances.HOTEL_RESERVATION_CONFIRMATION_RESPONSE_MAPPER.toHotelReservationConfirmationResponse(otaNotifReportRS2);
        assertNotNull(guestRequestsConfirmationResponse2);

        assertEquals(guestRequestsConfirmationResponse2.toString(), guestRequestsConfirmationResponse.toString());

        ObjectToXmlConverter<OTANotifReportRS> toObjectConverter = this.validatingObjectToXmlConverter(OTANotifReportRS.class, schema);

        toObjectConverter.toXml(otaNotifReportRS2, new ByteArrayOutputStream());
    }

    private <T> XmlToObjectConverter<T> validatingXmlToObjectConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBXmlToObjectConverter.Builder(classToBeBound).schema(schema).build();
    }

    private <T> ObjectToXmlConverter<T> validatingObjectToXmlConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBObjectToXmlConverter.Builder(classToBeBound).schema(schema).build();
    }

}