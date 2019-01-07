/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.mapper.v_2017_10.inventory.infors;

import it.bz.idm.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoResponse;
import it.bz.idm.alpinebits.mapping.utils.CollectionUtils;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveInfoRS;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;

/**
 * The herein declared methods are invoked after
 * {@link HotelDescriptiveInfoResponseMapper}
 * has finished to further customize the mapping.
 */
@Mapper
public interface AfterHotelDescriptiveInfoResponseMapping {

    @AfterMapping
    default void updateOTAHotelDescriptiveInfoRS(
            @MappingTarget OTAHotelDescriptiveInfoRS ota,
            HotelDescriptiveInfoResponse hotelDescriptiveInfoResponse
    ) {
        if (ota.getErrors() != null && CollectionUtils.isNullOrEmpty(ota.getErrors().getErrors())) {
            ota.setErrors(null);
        }
    }

    @AfterMapping
    default void updateHotelDescriptiveInfoResponse(
            @MappingTarget HotelDescriptiveInfoResponse hotelDescriptiveInfoResponse,
            OTAHotelDescriptiveInfoRS ota

    ) {
        if (hotelDescriptiveInfoResponse.getErrors() == null) {
            hotelDescriptiveInfoResponse.setErrors(new ArrayList<>());
        }
    }
}
