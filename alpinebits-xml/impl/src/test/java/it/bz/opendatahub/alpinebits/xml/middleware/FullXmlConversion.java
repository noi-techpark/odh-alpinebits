/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.xml.middleware;

import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.common.context.ResponseContextKeys;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.impl.SimpleContext;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.middleware.utils.UnitTestDifferenceEvaluator;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRS;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRS;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveInfoRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelRatePlanNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelRatePlanNotifRS;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelRatePlanRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelRatePlanRS;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTANotifReportRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAReadRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAResRetrieveRS;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.testng.Assert.assertFalse;

/**
 * This test does a full conversion of XML-to-object
 * and object-to-XML, using {@link XmlRequestMappingMiddleware}
 * and {@link XmlResponseMappingMiddleware}. Each test
 * asserts that the original XML and the result XML
 * are similar.
 */
public class FullXmlConversion {

    @DataProvider(name = "xmlValid")
    public static Object[][] badBasicAuthentication() {
        return new Object[][]{
                {"BaseRates-OTA_HotelRatePlanRQ.xml", OTAHotelRatePlanRQ.class},
                {"BaseRates-OTA_HotelRatePlanRS.xml", OTAHotelRatePlanRS.class},
                {"FreeRooms-OTA_HotelAvailNotifRQ.xml", OTAHotelAvailNotifRQ.class},
                {"FreeRooms-OTA_HotelAvailNotifRQ-empty.xml", OTAHotelAvailNotifRQ.class},
                {"FreeRooms-OTA_HotelAvailNotifRS-advisory.xml", OTAHotelAvailNotifRS.class},
                {"FreeRooms-OTA_HotelAvailNotifRS-error.xml", OTAHotelAvailNotifRS.class},
                {"FreeRooms-OTA_HotelAvailNotifRS-success.xml", OTAHotelAvailNotifRS.class},
                {"FreeRooms-OTA_HotelAvailNotifRS-warning.xml", OTAHotelAvailNotifRS.class},
                {"GuestRequests-OTA_ReadRQ.xml", OTAReadRQ.class},
                {"GuestRequests-OTA_ReadRQ-ack.xml", OTANotifReportRQ.class},
                {"GuestRequests-OTA_ResRetrieveRS-cancellation.xml", OTAResRetrieveRS.class},
                {"GuestRequests-OTA_ResRetrieveRS-error.xml", OTAResRetrieveRS.class},
                {"GuestRequests-OTA_ResRetrieveRS-reservation.xml", OTAResRetrieveRS.class},
                {"GuestRequests-OTA_ResRetrieveRS-reservation-empty.xml", OTAResRetrieveRS.class},
                {"Inventory-OTA_HotelDescriptiveContentNotifRQ.xml", OTAHotelDescriptiveContentNotifRQ.class},
                {"Inventory-OTA_HotelDescriptiveContentNotifRQ-delete-all.xml", OTAHotelDescriptiveContentNotifRQ.class},
                {"Inventory-OTA_HotelDescriptiveContentNotifRQ-hotelinfo.xml", OTAHotelDescriptiveContentNotifRQ.class},
                {"Inventory-OTA_HotelDescriptiveContentNotifRS-advisory.xml", OTAHotelDescriptiveContentNotifRS.class},
                {"Inventory-OTA_HotelDescriptiveContentNotifRS-error.xml", OTAHotelDescriptiveContentNotifRS.class},
                {"Inventory-OTA_HotelDescriptiveContentNotifRS-success.xml", OTAHotelDescriptiveContentNotifRS.class},
                {"Inventory-OTA_HotelDescriptiveContentNotifRS-warning.xml", OTAHotelDescriptiveContentNotifRS.class},
                {"Inventory-OTA_HotelDescriptiveInfoRQ-basicpullRQ.xml", OTAHotelDescriptiveInfoRQ.class},
                {"Inventory-OTA_HotelDescriptiveInfoRQ-hotelinfo.xml", OTAHotelDescriptiveInfoRQ.class},
                {"Inventory-OTA_HotelDescriptiveInfoRS-basicpullRS.xml", OTAHotelDescriptiveInfoRS.class},
                {"Inventory-OTA_HotelDescriptiveInfoRS-hotelinfo.xml", OTAHotelDescriptiveInfoRS.class},
                {"RatePlans-OTA_HotelRatePlanNotifRQ.xml", OTAHotelRatePlanNotifRQ.class},
                {"RatePlans-OTA_HotelRatePlanNotifRS-advisory.xml", OTAHotelRatePlanNotifRS.class},
                {"RatePlans-OTA_HotelRatePlanNotifRS-error.xml", OTAHotelRatePlanNotifRS.class},
                {"RatePlans-OTA_HotelRatePlanNotifRS-success.xml", OTAHotelRatePlanNotifRS.class},
                {"RatePlans-OTA_HotelRatePlanNotifRS-warning.xml", OTAHotelRatePlanNotifRS.class},
        };
    }

    @Test(dataProvider = "xmlValid")
    public <T> void fullConversion(String xmlFile, Class<T> classToBeBound) throws Exception {
        String filename = "examples/v_2017_10/" + xmlFile;
        Context ctx = this.prepareCtx(filename);

        Key<T> key = Key.key("data key", classToBeBound);
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");

        // XML to object
        XmlRequestMappingMiddleware<T> xmlToObjectMiddleware = this.validatingXmlToObjectMiddleware(key, classToBeBound, schema);
        xmlToObjectMiddleware.handleContext(ctx, () -> {
        });

        // Object to XML
        XmlResponseMappingMiddleware<T> objectToXmlMiddleware = this.validatingObjectToXmlMiddleware(key, classToBeBound, schema);
        objectToXmlMiddleware.handleContext(ctx, () -> {
        });

        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        ByteArrayOutputStream outputXmlStream = (ByteArrayOutputStream) ctx.getOrThrow(ResponseContextKeys.RESPONSE_CONTENT_STREAM);
        String outputXml = outputXmlStream.toString("UTF-8");

        Diff xmlDiff = DiffBuilder.compare(inputXmlStream).withTest(outputXml)
                .checkForSimilar()
                .ignoreWhitespace()
                .ignoreComments()
                .withDifferenceEvaluator(new UnitTestDifferenceEvaluator())
                .build();

        if (xmlDiff.hasDifferences()) {
            //CHECKSTYLE:OFF
            System.err.println(filename + " XML conversion difference found:");
            //CHECKSTYLE:ON
            xmlDiff.getDifferences().forEach(System.err::println);
        }

        assertFalse(xmlDiff.hasDifferences());
    }

    private Context prepareCtx(String xmlFile) {
        Context ctx = new SimpleContext();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(xmlFile);
        ctx.put(RequestContextKey.REQUEST_CONTENT_STREAM, is);

        OutputStream os = new ByteArrayOutputStream();
        ctx.put(ResponseContextKeys.RESPONSE_CONTENT_STREAM, os);
        return ctx;
    }

    private <T> XmlRequestMappingMiddleware<T> validatingXmlToObjectMiddleware(Key<T> key, Class<T> classToBeBound, Schema schema) throws JAXBException {
        XmlToObjectConverter<T> converter = this.validatingXmlToObjectConverter(classToBeBound, schema);
        return new XmlRequestMappingMiddleware<>(converter, key);
    }

    private <T> XmlToObjectConverter<T> validatingXmlToObjectConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBXmlToObjectConverter.Builder<>(classToBeBound).schema(schema).build();
    }

    private <T> XmlResponseMappingMiddleware<T> validatingObjectToXmlMiddleware(Key<T> key, Class<T> classToBeBound, Schema schema) throws JAXBException {
        ObjectToXmlConverter<T> converter = this.validatingObjectToXmlConverter(classToBeBound, schema);
        return new XmlResponseMappingMiddleware<>(converter, key);
    }

    private <T> ObjectToXmlConverter<T> validatingObjectToXmlConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBObjectToXmlConverter.Builder<>(classToBeBound).schema(schema).prettyPrint(true).build();
    }
}
