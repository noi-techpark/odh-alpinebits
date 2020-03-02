/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.inventory.infors;

import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoResponse;
import it.bz.opendatahub.alpinebits.mapping.utils.CollectionUtils;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveInfoRS;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
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
            HotelDescriptiveInfoResponse hotelDescriptiveInfoResponse,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    ) {
        if (ota.getErrors() != null && CollectionUtils.isNullOrEmpty(ota.getErrors().getErrors())) {
            ota.setErrors(null);
        }
    }

    @AfterMapping
    default void updateHotelDescriptiveInfoResponse(
            @MappingTarget HotelDescriptiveInfoResponse hotelDescriptiveInfoResponse,
            OTAHotelDescriptiveInfoRS ota,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    ) {
        if (hotelDescriptiveInfoResponse.getErrors() == null) {
            hotelDescriptiveInfoResponse.setErrors(new ArrayList<>());
        }
    }
}
