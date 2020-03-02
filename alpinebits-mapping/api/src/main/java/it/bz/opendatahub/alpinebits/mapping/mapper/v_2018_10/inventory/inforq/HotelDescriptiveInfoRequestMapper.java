/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.inventory.inforq;

import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoRequest;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveInfoRQ;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convert between AlpineBits {@link OTAHotelDescriptiveInfoRQ}
 * (Inventory) and {@link HotelDescriptiveInfoRequest} objects.
 */
@Mapper
public interface HotelDescriptiveInfoRequestMapper {

    @Mapping(target = "hotelCode", source = "hotelDescriptiveInfos.hotelDescriptiveInfo.hotelCode")
    @Mapping(target = "hotelName", source = "hotelDescriptiveInfos.hotelDescriptiveInfo.hotelName")
    HotelDescriptiveInfoRequest toHotelDescriptiveInfoRequest(
            OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    @InheritInverseConfiguration
    @Mapping(target = "version", constant = "3.000")
    @Mapping(target = "timeStamp", ignore = true)
    OTAHotelDescriptiveInfoRQ toOTAHotelDescriptiveInfoRQ(
            HotelDescriptiveInfoRequest hotelDescriptiveInfoRequest,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

}