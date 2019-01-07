/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.mapper.v_2017_10.freerooms;

import it.bz.idm.alpinebits.mapping.entity.freerooms.FreeRoomsResponse;
import it.bz.idm.alpinebits.mapping.utils.CollectionUtils;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRS;
import org.mapstruct.AfterMapping;
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
            FreeRoomsResponse freeRoomsResponse
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
            @MappingTarget FreeRoomsResponse freeRoomsResponse,
            OTAHotelAvailNotifRS ota
    ) {
        if (freeRoomsResponse.getErrors() == null) {
            freeRoomsResponse.setErrors(new ArrayList<>());
        }
        if (freeRoomsResponse.getWarnings() == null) {
            freeRoomsResponse.setWarnings(new ArrayList<>());
        }
    }
}
