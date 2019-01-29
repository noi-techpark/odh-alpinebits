/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.inventory.middleware.configuration;

import it.bz.opendatahub.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.db.middleware.EntityManagerProvidingMiddleware;
import it.bz.opendatahub.alpinebits.examples.inventory.middleware.InventoryPushMiddleware;
import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveContentNotifRequest;
import it.bz.opendatahub.alpinebits.mapping.mapper.InventoryMapperInstances;
import it.bz.opendatahub.alpinebits.mapping.middleware.RequestMappingMiddleware;
import it.bz.opendatahub.alpinebits.mapping.middleware.ResponseMappingMiddleware;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRS;

import javax.xml.bind.JAXBException;
import java.util.Arrays;

/**
 * Utility class to build a {@link InventoryPushMiddleware}.
 */
public final class InventoryPushMiddlewareBuilder {

    private static final Key<OTAHotelDescriptiveContentNotifRQ> OTA_INVENTORY_PUSH_REQUEST
            = Key.key("inventory push request", OTAHotelDescriptiveContentNotifRQ.class);
    private static final Key<OTAHotelDescriptiveContentNotifRS> OTA_INVENTORY_PUSH_RESPONSE
            = Key.key("inventory push response", OTAHotelDescriptiveContentNotifRS.class);

    private static final Key<HotelDescriptiveContentNotifRequest> HOTEL_DESCRIPTIVE_CONTENT_NOTIF_REQUEST_KEY =
            Key.key("mapped inventory push request", HotelDescriptiveContentNotifRequest.class);
    private static final Key<GenericResponse> HOTEL_DESCRIPTIVE_CONTENT_NOTIF_RESPONSE_KEY =
            Key.key("mapped inventory push response", GenericResponse.class);

    private InventoryPushMiddlewareBuilder() {
        // Empty
    }

    public static Middleware buildInventoryPushMiddleware() throws JAXBException {
        return ComposingMiddlewareBuilder.compose(Arrays.asList(
                XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(OTA_INVENTORY_PUSH_REQUEST),
                XmlMiddlewareBuilder.buildObjectToXmlConvertingMiddleware(OTA_INVENTORY_PUSH_RESPONSE),
                InventoryPushMiddlewareBuilder.buildInventoryPushRequestMappingMiddleware(),
                InventoryPushMiddlewareBuilder.buildInventoryPushResponseMappingMiddleware(),
                new EntityManagerProvidingMiddleware(),
                new InventoryPushMiddleware(
                        HOTEL_DESCRIPTIVE_CONTENT_NOTIF_REQUEST_KEY,
                        HOTEL_DESCRIPTIVE_CONTENT_NOTIF_RESPONSE_KEY
                )
        ));
    }

    private static Middleware buildInventoryPushRequestMappingMiddleware() {
        return new RequestMappingMiddleware<>(
                OTA_INVENTORY_PUSH_REQUEST,
                HOTEL_DESCRIPTIVE_CONTENT_NOTIF_REQUEST_KEY,
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_CONTENT_NOTIF_REQUEST_MAPPER::toHotelDescriptiveContentNotifRequest
        );
    }

    private static Middleware buildInventoryPushResponseMappingMiddleware() {
        return new ResponseMappingMiddleware<>(
                HOTEL_DESCRIPTIVE_CONTENT_NOTIF_RESPONSE_KEY,
                OTA_INVENTORY_PUSH_RESPONSE,
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_CONTENT_NOTIF_RESPONSE_MAPPER::toOTAHotelDescriptiveContentNotifRS
        );
    }
}
