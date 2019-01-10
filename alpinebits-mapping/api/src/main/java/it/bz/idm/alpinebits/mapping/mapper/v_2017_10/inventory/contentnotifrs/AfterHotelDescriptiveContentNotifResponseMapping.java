/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.mapper.v_2017_10.inventory.contentnotifrs;

import it.bz.idm.alpinebits.mapping.entity.GenericResponse;
import it.bz.idm.alpinebits.mapping.utils.CollectionUtils;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRS;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;

/**
 * The herein declared methods are invoked after
 * {@link HotelDescriptiveContentNotifResponseMapper}
 * has finished to further customize the mapping.
 */
@Mapper
public interface AfterHotelDescriptiveContentNotifResponseMapping {

    @AfterMapping
    default void updateOTAHotelDescriptiveContentNotifRS(
            @MappingTarget OTAHotelDescriptiveContentNotifRS ota,
            GenericResponse genericResponse
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
            OTAHotelDescriptiveContentNotifRS ota

    ) {
        if (genericResponse.getErrors() == null) {
            genericResponse.setErrors(new ArrayList<>());
        }
        if (genericResponse.getWarnings() == null) {
            genericResponse.setWarnings(new ArrayList<>());
        }
    }
}
