/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.freerooms.middleware.configuration;

import it.bz.opendatahub.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.db.middleware.EntityManagerProvidingMiddleware;
import it.bz.opendatahub.alpinebits.examples.freerooms.middleware.FreeRoomsMiddleware;
import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebits.mapping.entity.freerooms.FreeRoomsRequest;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.FreeRoomsMapperInstances;
import it.bz.opendatahub.alpinebits.mapping.middleware.RequestMappingMiddleware;
import it.bz.opendatahub.alpinebits.mapping.middleware.ResponseMappingMiddleware;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRS;

import javax.xml.bind.JAXBException;
import java.util.Arrays;

/**
 * Utility class to build a {@link FreeRoomsMiddleware}.
 */
public final class FreeRoomsMiddlewareBuilder {

    private static final Key<OTAHotelAvailNotifRQ> OTA_FREE_ROOMS_REQUEST
            = Key.key("free rooms request", OTAHotelAvailNotifRQ.class);
    private static final Key<OTAHotelAvailNotifRS> OTA_FREE_ROOMS_RESPONSE
            = Key.key("free rooms response", OTAHotelAvailNotifRS.class);

    private static final Key<FreeRoomsRequest> FREE_ROOMS_REQUEST_KEY =
            Key.key("mapped free rooms request", FreeRoomsRequest.class);
    private static final Key<GenericResponse> FREE_ROOMS_RESPONSE_KEY =
            Key.key("mapped free rooms response", GenericResponse.class);

    private FreeRoomsMiddlewareBuilder() {
        // Empty
    }

    public static Middleware buildFreeRoomsMiddleware() throws JAXBException {
        return ComposingMiddlewareBuilder.compose(Arrays.asList(
                XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(OTA_FREE_ROOMS_REQUEST),
                XmlMiddlewareBuilder.buildObjectToXmlConvertingMiddleware(OTA_FREE_ROOMS_RESPONSE),
                FreeRoomsMiddlewareBuilder.buildFreeRoomsRequestMappingMiddleware(),
                FreeRoomsMiddlewareBuilder.buildFreeRoomsResponseMappingMiddleware(),
                new EntityManagerProvidingMiddleware(),
                new FreeRoomsMiddleware(
                        FREE_ROOMS_REQUEST_KEY,
                        FREE_ROOMS_RESPONSE_KEY
                )
        ));
    }

    private static Middleware buildFreeRoomsRequestMappingMiddleware() {
        return new RequestMappingMiddleware<>(
                OTA_FREE_ROOMS_REQUEST,
                FREE_ROOMS_REQUEST_KEY,
                FreeRoomsMapperInstances.FREE_ROOMS_REQUEST_MAPPER::toFreeRoomsRequest
        );
    }

    private static Middleware buildFreeRoomsResponseMappingMiddleware() {
        return new ResponseMappingMiddleware<>(
                FREE_ROOMS_RESPONSE_KEY,
                OTA_FREE_ROOMS_RESPONSE,
                FreeRoomsMapperInstances.FREE_ROOMS_RESPONSE_MAPPER::toOTAHotelAvailNotifRS
        );
    }
}
