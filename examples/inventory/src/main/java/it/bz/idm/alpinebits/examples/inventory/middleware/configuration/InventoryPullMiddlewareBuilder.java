/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.inventory.middleware.configuration;

import it.bz.idm.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.idm.alpinebits.db.middleware.EntityManagerProvidingMiddleware;
import it.bz.idm.alpinebits.examples.inventory.middleware.InventoryPullMiddleware;
import it.bz.idm.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoRequest;
import it.bz.idm.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoResponse;
import it.bz.idm.alpinebits.mapping.mapper.InventoryMapperInstances;
import it.bz.idm.alpinebits.mapping.middleware.RequestMappingMiddleware;
import it.bz.idm.alpinebits.mapping.middleware.ResponseMappingMiddleware;
import it.bz.idm.alpinebits.middleware.Key;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveInfoRQ;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveInfoRS;

import javax.xml.bind.JAXBException;
import java.util.Arrays;

/**
 * Utility class to build a {@link InventoryPullMiddleware}.
 */
public final class InventoryPullMiddlewareBuilder {

    private static final Key<OTAHotelDescriptiveInfoRQ> OTA_INVENTORY_PULL_REQUEST
            = Key.key("inventory pull request", OTAHotelDescriptiveInfoRQ.class);
    private static final Key<OTAHotelDescriptiveInfoRS> OTA_INVENTORY_PULL_RESPONSE
            = Key.key("inventory pull response", OTAHotelDescriptiveInfoRS.class);

    private static final Key<HotelDescriptiveInfoRequest> HOTEL_DESCRIPTIVE_INFO_REQUEST_KEY =
            Key.key("mapped inventory pull request", HotelDescriptiveInfoRequest.class);
    private static final Key<HotelDescriptiveInfoResponse> HOTEL_DESCRIPTIVE_INFO_RESPONSE_KEY =
            Key.key("mapped inventory pull response", HotelDescriptiveInfoResponse.class);

    private InventoryPullMiddlewareBuilder() {
        // Empty
    }

    public static Middleware buildInventoryPullMiddleware() throws JAXBException {
        return ComposingMiddlewareBuilder.compose(Arrays.asList(
                XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(OTA_INVENTORY_PULL_REQUEST),
                XmlMiddlewareBuilder.buildObjectToXmlConvertingMiddleware(OTA_INVENTORY_PULL_RESPONSE),
                InventoryPullMiddlewareBuilder.buildInventoryPullRequestMappingMiddleware(),
                InventoryPullMiddlewareBuilder.buildInventoryPullResponseMappingMiddleware(),
                new EntityManagerProvidingMiddleware(),
                new InventoryPullMiddleware(
                        HOTEL_DESCRIPTIVE_INFO_REQUEST_KEY,
                        HOTEL_DESCRIPTIVE_INFO_RESPONSE_KEY
                )
        ));
    }

    private static Middleware buildInventoryPullRequestMappingMiddleware() {
        return new RequestMappingMiddleware<>(
                OTA_INVENTORY_PULL_REQUEST,
                HOTEL_DESCRIPTIVE_INFO_REQUEST_KEY,
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_INFO_REQUEST_MAPPER::toHotelDescriptiveInfoRequest
        );
    }

    private static Middleware buildInventoryPullResponseMappingMiddleware() {
        return new ResponseMappingMiddleware<>(
                HOTEL_DESCRIPTIVE_INFO_RESPONSE_KEY,
                OTA_INVENTORY_PULL_RESPONSE,
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_INFO_RESPONSE_MAPPER::toOTAHotelDescriptiveInfoRS
        );
    }
}
