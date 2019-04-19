/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.common;

import it.bz.opendatahub.alpinebits.mapping.entity.Warning;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRS;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convert between AlpineBits Warning
 * (FreeRooms/Inventory) and {@link Warning} objects.
 */
@Mapper
public interface WarningMapper {

    @Mapping(target = "recordId", source = "recordID")
    Warning toWarning(
            OTAHotelAvailNotifRS.Warnings.Warning warning,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    @InheritInverseConfiguration
    OTAHotelAvailNotifRS.Warnings.Warning toOTAWarning(
            Warning warning,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

}
