/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.mapper.v_2017_10.freerooms;

import it.bz.idm.alpinebits.mapping.entity.freerooms.AvailStatus;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convert between AlpineBits FreeRooms AvailStatusMessage
 * and {@link AvailStatus} objects.
 */
@Mapper
public interface AvailStatusMapper {

    @Mapping(target = "start", source = "statusApplicationControl.start")
    @Mapping(target = "end", source = "statusApplicationControl.end")
    @Mapping(target = "invTypeCode", source = "statusApplicationControl.invTypeCode")
    @Mapping(target = "invCode", source = "statusApplicationControl.invCode")
    AvailStatus toAvailStatus(OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage availStatusMessage);

    @InheritInverseConfiguration
    OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage toOTAAvailStatusMessage(AvailStatus availStatus);

}
