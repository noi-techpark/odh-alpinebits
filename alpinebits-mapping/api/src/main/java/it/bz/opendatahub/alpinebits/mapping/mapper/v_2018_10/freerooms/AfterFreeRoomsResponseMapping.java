/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.freerooms;

import it.bz.opendatahub.alpinebits.mapping.entity.GenericResponse;
import it.bz.opendatahub.alpinebits.mapping.utils.CollectionUtils;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelAvailNotifRS;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;

/**
 * The herein declared methods are invoked after
 * {@link FreeRoomsResponseMapper}
 * has finished to further customize the mapping.
 */
@Mapper
public interface AfterFreeRoomsResponseMapping {

    @AfterMapping
    default void updateOTAHotelDescriptiveContentNotifRS(
            @MappingTarget OTAHotelAvailNotifRS ota,
            GenericResponse genericResponse,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    ) {
        if (ota.getErrors() != null && CollectionUtils.isNullOrEmpty(ota.getErrors().getErrors())) {
            ota.setErrors(null);
        }
        if (ota.getWarnings() != null && CollectionUtils.isNullOrEmpty(ota.getWarnings().getWarnings())) {
            ota.setWarnings(null);
        }
    }

    @AfterMapping
    default void updateHotelDescriptiveContentNotifResponse(
            @MappingTarget GenericResponse genericResponse,
            OTAHotelAvailNotifRS ota,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    ) {
        if (genericResponse.getErrors() == null) {
            genericResponse.setErrors(new ArrayList<>());
        }
        if (genericResponse.getWarnings() == null) {
            genericResponse.setWarnings(new ArrayList<>());
        }
    }
}
