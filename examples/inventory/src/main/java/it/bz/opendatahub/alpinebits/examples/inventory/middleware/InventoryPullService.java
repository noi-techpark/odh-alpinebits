// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.inventory.middleware;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRQ.HotelDescriptiveInfos.HotelDescriptiveInfo;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelDescriptiveInfoRS.HotelDescriptiveContents.HotelDescriptiveContent;

import javax.xml.validation.Schema;
import java.io.InputStream;

/**
 * This service handles the DB persistence for
 * {@link InventoryPullMiddleware}.
 */
public class InventoryPullService {

    private final XmlToObjectConverter<OTAHotelDescriptiveInfoRS> converter;

    public InventoryPullService() {
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        converter = new JAXBXmlToObjectConverter.Builder<>(OTAHotelDescriptiveInfoRS.class).schema(schema).build();
    }

    /**
     * Read a Inventory/Basic pull response and return it.
     *
     * @param otaHotelDescriptiveInfoRQ Read the response for this request.
     * @return A {@link OTAHotelDescriptiveInfoRS} instance as response.
     */
    public OTAHotelDescriptiveInfoRS readInventoryBasic(OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ) {
        return buildResult(otaHotelDescriptiveInfoRQ, "inventory-basic-pull-sample.xml");
    }

    /**
     * Read a Inventory/HotelInfo pull response and return it.
     *
     * @param otaHotelDescriptiveInfoRQ Read the response for this request.
     * @return A {@link OTAHotelDescriptiveInfoRS} instance as response.
     */
    public OTAHotelDescriptiveInfoRS readInventoryHotelInfo(OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ) {
        return buildResult(otaHotelDescriptiveInfoRQ, "inventory-hotelinfo-pull-sample.xml");
    }

    @SuppressWarnings("checkstyle:IllegalCatch")
    private OTAHotelDescriptiveInfoRS buildResult(OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ, String resource) {
        try {
            // Convert from XML
            InputStream is = InventoryPullService.class.getClassLoader().getResourceAsStream(resource);
            OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = converter.toObject(is);

            // Fix HotelCode/HotelName values
            HotelDescriptiveInfo hotelDescriptiveInfo = otaHotelDescriptiveInfoRQ.getHotelDescriptiveInfos().getHotelDescriptiveInfos().get(0);
            String hotelName = hotelDescriptiveInfo.getHotelName();
            String hotelCode = hotelDescriptiveInfo.getHotelCode();

            HotelDescriptiveContent hotelDescriptiveContent = otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContents().get(0);
            hotelDescriptiveContent.setHotelName(hotelName);
            hotelDescriptiveContent.setHotelCode(hotelCode);

            // Return success
            return otaHotelDescriptiveInfoRS;
        } catch (Exception e) {
            throw new AlpineBitsException(e.getMessage(), 500);
        }
    }
}
