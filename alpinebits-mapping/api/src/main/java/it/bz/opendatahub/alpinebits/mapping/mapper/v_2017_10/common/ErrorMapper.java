/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.common;

import it.bz.opendatahub.alpinebits.mapping.entity.Error;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRS;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveInfoRS;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTANotifReportRS;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAResRetrieveRS;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convert between AlpineBits Error
 * (GuestRequestsFreeRooms/Inventory) and {@link Error} objects.
 */
@Mapper
public interface ErrorMapper {

    @Mapping(target = "status", ignore = true)
    Error toError(OTAHotelDescriptiveInfoRS.Errors.Error error);

    @Mapping(target = "status", ignore = true)
    Error toError(OTAHotelAvailNotifRS.Errors.Error error);

    @Mapping(target = "status", ignore = true)
    Error toError(OTAResRetrieveRS.Errors.Error error);

    @Mapping(target = "status", ignore = true)
    Error toError(OTANotifReportRS.Errors.Error error);

}
