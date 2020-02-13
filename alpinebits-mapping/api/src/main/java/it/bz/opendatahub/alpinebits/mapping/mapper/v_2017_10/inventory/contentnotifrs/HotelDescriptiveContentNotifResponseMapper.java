/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.inventory.contentnotifrs;

import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.common.ErrorMapper;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.common.WarningMapper;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRS;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convert between AlpineBits {@link OTAHotelDescriptiveContentNotifRS}
 * (Inventory) and {@link GenericResponse} objects.
 */
@Mapper(
        uses = {
                AfterHotelDescriptiveContentNotifResponseMapping.class,
                ErrorMapper.class,
                WarningMapper.class,
        }
)
public interface HotelDescriptiveContentNotifResponseMapper {

    @Mapping(target = "errors", source = "errors.errors")
    @Mapping(target = "warnings", source = "warnings.warnings")
    GenericResponse toGenericResponse(
            OTAHotelDescriptiveContentNotifRS otaHotelDescriptiveContentNotifRS,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    @InheritInverseConfiguration
    @Mapping(target = "version", constant = "3.000")
    @Mapping(target = "timeStamp", ignore = true)
    OTAHotelDescriptiveContentNotifRS toOTAHotelDescriptiveContentNotifRS(
            GenericResponse genericResponse,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

}