/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.inventory.infors;

import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoResponse;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.common.ErrorMapper;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.inventory.HotelDescriptiveContentMapper;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveInfoRS;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convert between AlpineBits {@link OTAHotelDescriptiveContentNotifRQ}
 * (Inventory) and {@link HotelDescriptiveContent} objects.
 */
@Mapper(
        uses = {
                AfterHotelDescriptiveInfoResponseMapping.class,
                ErrorMapper.class,
                HotelDescriptiveContentMapper.class,
        }
)
public interface HotelDescriptiveInfoResponseMapper {

    @Mapping(target = "hotelDescriptiveContent", source = "hotelDescriptiveContents.hotelDescriptiveContent")
    @Mapping(target = "errors", source = "errors.errors")
    HotelDescriptiveInfoResponse toHotelDescriptiveInfoResponse(
            OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    @InheritInverseConfiguration
    @Mapping(target = "version", constant = "8.000")
    @Mapping(target = "timeStamp", ignore = true)
    OTAHotelDescriptiveInfoRS toOTAHotelDescriptiveInfoRS(
            HotelDescriptiveInfoResponse hotelDescriptiveInfoResponse,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

}