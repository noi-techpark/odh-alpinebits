/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.guestrequests.notifreportrs;

import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.notifreportrs.GuestRequestsConfirmationResponse;
import it.bz.opendatahub.alpinebits.mapping.utils.CollectionUtils;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTANotifReportRS;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;

/**
 * Map AlpineBits {@link OTANotifReportRS} objects to
 * {@link GuestRequestsConfirmationResponse} objects
 * and vice versa.
 */
@Mapper
public interface GuestRequestsConfirmationResponseMapper {

    @Mapping(target = "errors", source = "errors.errors")
    GuestRequestsConfirmationResponse toHotelReservationConfirmationResponse(
            OTANotifReportRS otaNotifReportRS,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    @AfterMapping
    default void checkAndSetLists(
            @MappingTarget GuestRequestsConfirmationResponse readResult,
            OTANotifReportRS ota,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    ) {
        if (readResult.getErrors() == null) {
            readResult.setErrors(new ArrayList<>());
        }
    }

    @InheritInverseConfiguration
    @Mapping(target = "version", constant = "1.000")
    @Mapping(target = "timeStamp", ignore = true)
    OTANotifReportRS toOTANotifReportRS(
            GuestRequestsConfirmationResponse guestRequestsConfirmationResponse,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    @AfterMapping
    default void checkAndRemoveOTAParents(
            @MappingTarget OTANotifReportRS ota,
            GuestRequestsConfirmationResponse response,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    ) {
        if (ota.getErrors() == null || CollectionUtils.isNullOrEmpty(ota.getErrors().getErrors())) {
            ota.setErrors(null);
        }
    }
}
