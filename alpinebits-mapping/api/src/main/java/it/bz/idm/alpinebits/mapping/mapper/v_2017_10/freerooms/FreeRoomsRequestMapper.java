/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.mapper.v_2017_10.freerooms;

import it.bz.idm.alpinebits.mapping.entity.freerooms.FreeRoomsRequest;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convert between AlpineBits OTAHotelAvailNotifRQ
 * and {@link FreeRoomsRequest} objects.
 */
@Mapper(
        uses = {
                AfterFreeRoomsRequestMapping.class,
                AvailStatusMapper.class,
                UniqueIdMapper.class
        }
)
public interface FreeRoomsRequestMapper {

    @Mapping(target = "hotelCode", source = "availStatusMessages.hotelCode")
    @Mapping(target = "hotelName", source = "availStatusMessages.hotelName")
    @Mapping(target = "uniqueId", source = "uniqueID")
    @Mapping(target = "availStatuses", source = "availStatusMessages.availStatusMessages")
    FreeRoomsRequest toFreeRoomsRequest(OTAHotelAvailNotifRQ otaHotelAvailNotifRQ);

    @InheritInverseConfiguration
    @Mapping(target = "version", constant = "1.002")
    @Mapping(target = "timeStamp", ignore = true)
    OTAHotelAvailNotifRQ toOTAHotelAvailNotifRQ(FreeRoomsRequest freeRoomsRequest);

}
