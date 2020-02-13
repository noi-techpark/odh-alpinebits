/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10;

import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.notifreportrq.GuestRequestsConfirmationRequest;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.notifreportrs.GuestRequestsConfirmationResponse;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.readrq.GuestRequestsReadRequest;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.GuestRequestsReadResponse;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.guestrequests.notifreportrq.GuestRequestsConfirmationRequestMapper;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.guestrequests.notifreportrs.GuestRequestsConfirmationResponseMapper;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.guestrequests.readrq.GuestRequestsReadRequestMapper;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.guestrequests.resretrievers.GuestRequestsReadResponseMapper;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTANotifReportRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTANotifReportRS;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAReadRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAResRetrieveRS;
import org.mapstruct.factory.Mappers;

/**
 * This class provides mapper instances for AlpineBits Guest Requests.
 */
public final class GuestRequestsMapperInstances {

    /**
     * Provide a mapper between AlpineBits
     * {@link OTAResRetrieveRS} and
     * {@link GuestRequestsReadResponse}.
     */
    public static final GuestRequestsReadResponseMapper HOTEL_RESERVATION_READ_RESPONSE_MAPPER
            = Mappers.getMapper(GuestRequestsReadResponseMapper.class);

    /**
     * Provide a mapper between AlpineBits
     * {@link OTAReadRQ} and
     * {@link GuestRequestsReadRequest}.
     */
    public static final GuestRequestsReadRequestMapper HOTEL_RESERVATION_READ_REQUEST_MAPPER
            = Mappers.getMapper(GuestRequestsReadRequestMapper.class);

    /**
     * Provide a mapper between AlpineBits
     * {@link OTANotifReportRQ} and
     * {@link GuestRequestsConfirmationRequest}.
     */
    public static final GuestRequestsConfirmationRequestMapper HOTEL_RESERVATION_CONFIRMATION_REQUEST_MAPPER
            = Mappers.getMapper(GuestRequestsConfirmationRequestMapper.class);

    /**
     * Provide a mapper between AlpineBits
     * {@link OTANotifReportRS} and
     * {@link GuestRequestsConfirmationResponse}.
     */
    public static final GuestRequestsConfirmationResponseMapper HOTEL_RESERVATION_CONFIRMATION_RESPONSE_MAPPER
            = Mappers.getMapper(GuestRequestsConfirmationResponseMapper.class);

    private GuestRequestsMapperInstances() {
        // Empty
    }

}
