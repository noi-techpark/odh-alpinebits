/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.guestrequests.resretrievers;

import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.GuestRequestsReadResponse;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.common.WarningMapper;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAResRetrieveRS;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Map AlpineBits {@link OTAResRetrieveRS} objects to
 * {@link GuestRequestsReadResponse} objects and vice versa.
 */
@Mapper(
        uses = {
                WarningMapper.class,
                HotelReservationMapper.class,
        }
)
public interface GuestRequestsReadResponseMapper {

    @Mapping(target = "errors", source = "errors.errors")
    @Mapping(target = "hotelReservations", source = "reservationsList.hotelReservations")
    @Mapping(target = "warnings", source = "warnings.warnings")
    GuestRequestsReadResponse toHotelReservationReadResult(
            OTAResRetrieveRS otaResRetrieveRS,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    @AfterMapping
    default void checkAndSetLists(
            @MappingTarget GuestRequestsReadResponse readResult,
            OTAResRetrieveRS ota,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    ) {
        ListInitializer.checkAndSetLists(readResult);
    }

    @InheritInverseConfiguration
    @Mapping(target = "version", constant = "7.000")
    @Mapping(target = "timeStamp", ignore = true)
    OTAResRetrieveRS toOTAResRetrieveRS(
            GuestRequestsReadResponse readResult,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    @AfterMapping
    default void checkAndRemoveOTAParents(
            @MappingTarget OTAResRetrieveRS ota,
            GuestRequestsReadResponse readResult,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    ) {
        ListParentCleaner.checkAndRemoveOTAParents(ota);
    }

    @AfterMapping
    default void removeOTAReservationListOnError(
            @MappingTarget OTAResRetrieveRS ota,
            GuestRequestsReadResponse readResult,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    ) {
        if (ota.getErrors() != null) {
            ota.setReservationsList(null);
        }

        if (ota.getWarnings() != null
                && ota.getWarnings().getWarnings() != null
                && ota.getWarnings().getWarnings().isEmpty()
        ) {
            ota.setWarnings(null);
        }
    }

}
