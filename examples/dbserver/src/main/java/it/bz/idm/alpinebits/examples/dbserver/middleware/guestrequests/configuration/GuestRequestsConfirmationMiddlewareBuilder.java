/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.dbserver.middleware.guestrequests.configuration;

import it.bz.idm.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.idm.alpinebits.db.middleware.EntityManagerProvidingMiddleware;
import it.bz.idm.alpinebits.examples.dbserver.middleware.guestrequests.GuestRequestsConfirmationMiddleware;
import it.bz.idm.alpinebits.mapping.entity.guestrequests.notifreportrq.GuestRequestsConfirmationRequest;
import it.bz.idm.alpinebits.mapping.entity.guestrequests.notifreportrs.GuestRequestsConfirmationResponse;
import it.bz.idm.alpinebits.mapping.mapper.guestrequests.GuestRequestsMapperInstances;
import it.bz.idm.alpinebits.mapping.middleware.RequestMappingMiddleware;
import it.bz.idm.alpinebits.mapping.middleware.ResponseMappingMiddleware;
import it.bz.idm.alpinebits.middleware.Key;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTANotifReportRQ;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTANotifReportRS;

import javax.xml.bind.JAXBException;
import java.util.Arrays;

/**
 * Utility class to build a {@link GuestRequestsConfirmationMiddleware}.
 */
public final class GuestRequestsConfirmationMiddlewareBuilder {

    private static final Key<OTANotifReportRQ> OTA_NOTIF_REPORT_RQ_KEY = Key.key("confirm requests", OTANotifReportRQ.class);
    private static final Key<OTANotifReportRS> OTA_NOTIF_REPORT_RS_KEY = Key.key("confirm response", OTANotifReportRS.class);

    private static final Key<GuestRequestsConfirmationRequest> HOTEL_RESERVATION_CONFIRMATION_REQUEST_KEY =
            Key.key("hotel reservation read request", GuestRequestsConfirmationRequest.class);
    private static final Key<GuestRequestsConfirmationResponse> HOTEL_RESERVATION_CONFIRMATION_RESPONSE_KEY =
            Key.key("hotel reservation read response", GuestRequestsConfirmationResponse.class);

    private GuestRequestsConfirmationMiddlewareBuilder() {
        // Empty
    }

    public static Middleware buildHotelReservationConfirmationMiddleware() throws JAXBException {
        return ComposingMiddlewareBuilder.compose(Arrays.asList(
                XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(OTA_NOTIF_REPORT_RQ_KEY),
                XmlMiddlewareBuilder.buildObjectToXmlConvertingMiddleware(OTA_NOTIF_REPORT_RS_KEY),
                GuestRequestsConfirmationMiddlewareBuilder.buildHotelReservationConfirmationRequestMappingMiddleware(),
                GuestRequestsConfirmationMiddlewareBuilder.buildHotelReservationConfirmationResponseMappingMiddleware(),
                new EntityManagerProvidingMiddleware(),
                new GuestRequestsConfirmationMiddleware(
                        HOTEL_RESERVATION_CONFIRMATION_REQUEST_KEY,
                        HOTEL_RESERVATION_CONFIRMATION_RESPONSE_KEY
                )
        ));
    }

    private static Middleware buildHotelReservationConfirmationRequestMappingMiddleware() {
        return new RequestMappingMiddleware<>(
                OTA_NOTIF_REPORT_RQ_KEY,
                HOTEL_RESERVATION_CONFIRMATION_REQUEST_KEY,
                GuestRequestsMapperInstances.HOTEL_RESERVATION_CONFIRMATION_REQUEST_MAPPER::toHotelReservationConfirmationRequest
        );
    }

    private static Middleware buildHotelReservationConfirmationResponseMappingMiddleware() {
        return new ResponseMappingMiddleware<>(
                HOTEL_RESERVATION_CONFIRMATION_RESPONSE_KEY,
                OTA_NOTIF_REPORT_RS_KEY,
                GuestRequestsMapperInstances.HOTEL_RESERVATION_CONFIRMATION_RESPONSE_MAPPER::toOTANotifReportRS
        );
    }
}
