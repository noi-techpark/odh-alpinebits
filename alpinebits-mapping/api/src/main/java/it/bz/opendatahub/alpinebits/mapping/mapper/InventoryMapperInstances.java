/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper;

import it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.inventory.contentnotifrq.HotelDescriptiveContentNotifRequestMapper;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.inventory.contentnotifrs.HotelDescriptiveContentNotifResponseMapper;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.inventory.inforq.HotelDescriptiveInfoRequestMapper;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.inventory.infors.HotelDescriptiveInfoResponseMapper;
import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoRequest;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRS;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveInfoRQ;
import org.mapstruct.factory.Mappers;

/**
 * This class provides mapper instances for AlpineBits Guest Requests.
 */
public final class InventoryMapperInstances {

    /**
     * Provide a mapper between AlpineBits
     * {@link OTAHotelDescriptiveContentNotifRQ} and
     * {@link HotelDescriptiveContent}.
     */
    public static final HotelDescriptiveContentNotifRequestMapper HOTEL_DESCRIPTIVE_CONTENT_NOTIF_REQUEST_MAPPER
            = Mappers.getMapper(HotelDescriptiveContentNotifRequestMapper.class);

    /**
     * Provide a mapper between AlpineBits
     * {@link OTAHotelDescriptiveContentNotifRS} and
     * {@link GenericResponse}.
     */
    public static final HotelDescriptiveContentNotifResponseMapper HOTEL_DESCRIPTIVE_CONTENT_NOTIF_RESPONSE_MAPPER
            = Mappers.getMapper(HotelDescriptiveContentNotifResponseMapper.class);

    /**
     * Provide a mapper between AlpineBits
     * {@link OTAHotelDescriptiveInfoRQ} and
     * {@link HotelDescriptiveInfoRequest}.
     */
    public static final HotelDescriptiveInfoRequestMapper HOTEL_DESCRIPTIVE_INFO_REQUEST_MAPPER
            = Mappers.getMapper(HotelDescriptiveInfoRequestMapper.class);

    /**
     * Provide a mapper between AlpineBits
     * {@link OTAHotelDescriptiveContentNotifRS} and
     * {@link GenericResponse}.
     */
    public static final HotelDescriptiveInfoResponseMapper HOTEL_DESCRIPTIVE_INFO_RESPONSE_MAPPER
            = Mappers.getMapper(HotelDescriptiveInfoResponseMapper.class);

    private InventoryMapperInstances() {
        // Empty
    }

}
