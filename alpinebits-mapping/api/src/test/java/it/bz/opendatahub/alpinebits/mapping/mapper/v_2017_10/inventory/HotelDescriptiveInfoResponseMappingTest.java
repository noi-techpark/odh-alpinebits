/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoResponse;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.InventoryMapperInstances;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.impl.SimpleContext;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveInfoRS;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.testng.Assert.*;

/**
 * This class tests the mapper of AlpineBits objects
 * to business objects.
 */
public class HotelDescriptiveInfoResponseMappingTest {

    @DataProvider(name = "xmlValid")
    public static Object[][] xmlFiles() {
        return new Object[][]{
                {"Inventory-OTA_HotelDescriptiveInfoRS-basicpullRS.xml"},
                {"Inventory-OTA_HotelDescriptiveInfoRS-hotelinfo.xml"},
                {"Inventory-OTA_HotelDescriptiveInfoRS-hotelinfo_affiliation_info.xml"},
                {"Inventory-OTA_HotelDescriptiveInfoRS-hotelinfo_contact_info.xml"},
                {"Inventory-OTA_HotelDescriptiveInfoRS-hotelinfo_hotel_info.xml"},
                {"Inventory-OTA_HotelDescriptiveInfoRS-hotelinfo_policies.xml"},
        };
    }

    @Test(dataProvider = "xmlValid")
    public void fullConversion(String xmlFile) throws Exception {
        String filename = "examples/v_2017_10/" + xmlFile;
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTAHotelDescriptiveInfoRS> converter = this.validatingXmlToObjectConverter(
                OTAHotelDescriptiveInfoRS.class, schema);

        OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = converter.toObject(inputXmlStream);

        HotelDescriptiveInfoResponse hotelDescriptiveInfoResponse =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_INFO_RESPONSE_MAPPER
                        .toHotelDescriptiveInfoResponse(otaHotelDescriptiveInfoRS, null);
        assertNotNull(hotelDescriptiveInfoResponse);

        OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS2 =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_INFO_RESPONSE_MAPPER
                        .toOTAHotelDescriptiveInfoRS(hotelDescriptiveInfoResponse, null);
        assertNotNull(otaHotelDescriptiveInfoRS2);

        HotelDescriptiveInfoResponse hotelDescriptiveInfoResponse2 =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_INFO_RESPONSE_MAPPER
                        .toHotelDescriptiveInfoResponse(otaHotelDescriptiveInfoRS2, null);
        assertNotNull(hotelDescriptiveInfoResponse2);

        // Convert HotelDescriptiveContentNotifRequest to string for comparison
        // The strings should be the same, except for instance identity of OTA extensions
        // (e.g. it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.AffiliationInfoType@1d0f7bcf)
        // Therefor, the identity is removed
        String hdcnResponse = hotelDescriptiveInfoResponse.toString().replaceAll("@[0-9a-f]*", "");
        String hdcnResponse2 = hotelDescriptiveInfoResponse2.toString().replaceAll("@[0-9a-f]*", "");

        assertEquals(hdcnResponse2, hdcnResponse);

        ObjectToXmlConverter<OTAHotelDescriptiveInfoRS> toObjectConverter = this.validatingObjectToXmlConverter(
                OTAHotelDescriptiveInfoRS.class, schema);

        toObjectConverter.toXml(otaHotelDescriptiveInfoRS2, new ByteArrayOutputStream());
    }

    @Test
    public void inventoryHotelInfoRemoveMultimediaInfoCode() throws Exception {
        String filename = "examples/v_2017_10/Inventory-OTA_HotelDescriptiveInfoRS-hotelinfo.xml";
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTAHotelDescriptiveInfoRS> converter = this.validatingXmlToObjectConverter(
                OTAHotelDescriptiveInfoRS.class, schema);

        OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = converter.toObject(inputXmlStream);

        HotelDescriptiveInfoResponse hotelDescriptiveInfoResponse =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_INFO_RESPONSE_MAPPER
                        .toHotelDescriptiveInfoResponse(otaHotelDescriptiveInfoRS, null);
        assertNotNull(hotelDescriptiveInfoResponse);

        // Set pictures element, whose MultimediaDescription InfoCode attribute
        // will be set during conversion
        hotelDescriptiveInfoResponse.getHotelDescriptiveContent().getGuestRooms().forEach(guestRoom -> {
            guestRoom.setPictures(guestRoom.getHotelInfoPictures());
        });

        Context ctx = new SimpleContext();
        ctx.put(RequestContextKey.REQUEST_ACTION, AlpineBitsAction.INVENTORY_HOTEL_INFO_PULL);

        OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS2 =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_INFO_RESPONSE_MAPPER
                        .toOTAHotelDescriptiveInfoRS(hotelDescriptiveInfoResponse, ctx);
        assertNotNull(otaHotelDescriptiveInfoRS2);

        // Check that multimedia InfoCode values have been removed
        otaHotelDescriptiveInfoRS2.getHotelDescriptiveContents()
                .getHotelDescriptiveContent()
                .getFacilityInfo()
                .getGuestRooms()
                .getGuestRooms()
                .forEach(guestRoom -> {
                    guestRoom
                            .getMultimediaDescriptions()
                            .getMultimediaDescriptions()
                            .forEach(multimediaDescription -> assertNull(multimediaDescription.getInfoCode()));
                });
    }

    private <T> XmlToObjectConverter<T> validatingXmlToObjectConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBXmlToObjectConverter.Builder<>(classToBeBound).schema(schema).build();
    }

    private <T> ObjectToXmlConverter<T> validatingObjectToXmlConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBObjectToXmlConverter.Builder<>(classToBeBound).schema(schema).build();
    }

}