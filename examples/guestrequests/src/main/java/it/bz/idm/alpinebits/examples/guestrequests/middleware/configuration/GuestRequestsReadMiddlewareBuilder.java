/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.guestrequests.middleware.configuration;

import it.bz.idm.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.idm.alpinebits.db.middleware.EntityManagerProvidingMiddleware;
import it.bz.idm.alpinebits.examples.guestrequests.middleware.GuestRequestsReadMiddleware;
import it.bz.idm.alpinebits.mapping.entity.guestrequests.readrq.GuestRequestsReadRequest;
import it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers.GuestRequestsReadResponse;
import it.bz.idm.alpinebits.mapping.mapper.GuestRequestsMapperInstances;
import it.bz.idm.alpinebits.mapping.middleware.RequestMappingMiddleware;
import it.bz.idm.alpinebits.mapping.middleware.ResponseMappingMiddleware;
import it.bz.idm.alpinebits.middleware.Key;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAReadRQ;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAResRetrieveRS;

import javax.xml.bind.JAXBException;
import java.util.Arrays;

/**
 * Utility class to build a {@link GuestRequestsReadMiddleware}.
 */
public final class GuestRequestsReadMiddlewareBuilder {

    private static final Key<OTAReadRQ> OTA_READ_RQ_KEY = Key.key("read requests", OTAReadRQ.class);
    private static final Key<OTAResRetrieveRS> OTA_RES_RETRIEVE_RS_KEY = Key.key("read response", OTAResRetrieveRS.class);

    private static final Key<GuestRequestsReadRequest> HOTEL_RESERVATION_READ_REQUEST_KEY =
            Key.key("guest requests read request", GuestRequestsReadRequest.class);
    private static final Key<GuestRequestsReadResponse> HOTEL_RESERVATION_READ_RESPONSE_KEY =
            Key.key("guest requests read response", GuestRequestsReadResponse.class);

    private GuestRequestsReadMiddlewareBuilder() {
        // Empty
    }

    public static Middleware buildHotelReservationReadMiddleware() throws JAXBException {
        return ComposingMiddlewareBuilder.compose(Arrays.asList(
                XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(OTA_READ_RQ_KEY),
                XmlMiddlewareBuilder.buildObjectToXmlConvertingMiddleware(OTA_RES_RETRIEVE_RS_KEY),
                GuestRequestsReadMiddlewareBuilder.buildHotelReservationReadRequestMappingMiddleware(),
                GuestRequestsReadMiddlewareBuilder.buildHotelReservationReadResponseMappingMiddleware(),
                new EntityManagerProvidingMiddleware(),
                new GuestRequestsReadMiddleware(HOTEL_RESERVATION_READ_RESPONSE_KEY)
        ));
    }

    private static Middleware buildHotelReservationReadRequestMappingMiddleware() {
        return new RequestMappingMiddleware<>(
                OTA_READ_RQ_KEY,
                HOTEL_RESERVATION_READ_REQUEST_KEY,
                GuestRequestsMapperInstances.HOTEL_RESERVATION_READ_REQUEST_MAPPER::toRequestResult
        );
    }

    private static Middleware buildHotelReservationReadResponseMappingMiddleware() {
        return new ResponseMappingMiddleware<>(
                HOTEL_RESERVATION_READ_RESPONSE_KEY,
                OTA_RES_RETRIEVE_RS_KEY,
                GuestRequestsMapperInstances.HOTEL_RESERVATION_READ_RESPONSE_MAPPER::toOTAResRetrieveRS
        );
    }
}
