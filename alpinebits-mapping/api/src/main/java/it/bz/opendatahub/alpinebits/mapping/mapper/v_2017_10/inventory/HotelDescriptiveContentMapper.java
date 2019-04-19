/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convert between AlpineBits HotelDescriptiveContent
 * (Inventory) and {@link HotelDescriptiveContent} objects.
 */
@Mapper(uses = {GuestRoomMapper.class, AfterHotelDescriptiveContentMapping.class})
public interface HotelDescriptiveContentMapper {

    @Mapping(target = "guestRooms", source = "facilityInfo.guestRooms.guestRooms")
    @Mapping(target = "affiliationInfo", ignore = true)
    @Mapping(target = "contactInfos", ignore = true)
    @Mapping(target = "hotelInfo", ignore = true)
    @Mapping(target = "policies", ignore = true)
    HotelDescriptiveContent toHotelDescriptiveContent(
            OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent hotelDescriptiveContent,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    @InheritInverseConfiguration
    @Mapping(target = "affiliationInfo", ignore = true)
    @Mapping(target = "contactInfos", ignore = true)
    @Mapping(target = "hotelInfo", ignore = true)
    @Mapping(target = "policies", ignore = true)
    @Mapping(target = "hotelCityCode", ignore = true)
    OTAHotelDescriptiveContentNotifRQ
            .HotelDescriptiveContents
            .HotelDescriptiveContent toOTAHotelDescriptiveContent(
            HotelDescriptiveContent hotelDescriptiveContent,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );


}
