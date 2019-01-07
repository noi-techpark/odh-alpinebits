/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.mapper;

import it.bz.idm.alpinebits.mapping.mapper.v_2017_10.freerooms.FreeRoomsRequestMapper;
import it.bz.idm.alpinebits.mapping.mapper.v_2017_10.freerooms.FreeRoomsResponseMapper;
import org.mapstruct.factory.Mappers;

/**
 * This class provides mapper instances for AlpineBits Guest Requests.
 */
public final class FreeRoomsMapperInstances {

    /**
     * Provide a mapper between AlpineBits
     * {@link it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ} and
     * {@link it.bz.idm.alpinebits.mapping.entity.freerooms.FreeRoomsRequest}.
     */
    public static final FreeRoomsRequestMapper FREE_ROOMS_REQUEST_MAPPER = Mappers.getMapper(FreeRoomsRequestMapper.class);

    /**
     * Provide a mapper between AlpineBits
     * {@link it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRS} and
     * {@link it.bz.idm.alpinebits.mapping.entity.freerooms.FreeRoomsResponse}.
     */
    public static final FreeRoomsResponseMapper FREE_ROOMS_RESPONSE_MAPPER = Mappers.getMapper(FreeRoomsResponseMapper.class);

    private FreeRoomsMapperInstances() {
        // Empty
    }

}
