/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.freerooms;

import it.bz.opendatahub.alpinebits.mapping.entity.freerooms.UniqueId;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelAvailNotifRQ;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convert between AlpineBits UniqueID
 * and {@link UniqueId} objects.
 */
@Mapper
public interface UniqueIdMapper {

    UniqueId toUniqueId(
            OTAHotelAvailNotifRQ.UniqueID uniqueId,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    @InheritInverseConfiguration
    @Mapping(target = "ID", constant = "1")
    OTAHotelAvailNotifRQ.UniqueID toOTAUniqueId(
            UniqueId uniqueId,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

}
