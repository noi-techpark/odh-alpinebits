/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.guestrequests.resretrievers;

import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.GuestRequestsReadResponse;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAResRetrieveRS;
import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Map AlpineBits {@link OTAResRetrieveRS} objects to
 * {@link GuestRequestsReadResponse} objects and vice versa.
 */
@Mapper(uses = HotelReservationMapper.class)
public interface GuestRequestsReadResponseMapper {

    @Mapping(target = "errors", source = "errors.errors")
    @Mapping(target = "hotelReservations", source = "reservationsList.hotelReservations")
    GuestRequestsReadResponse toHotelReservationReadResult(OTAResRetrieveRS otaResRetrieveRS);

    @AfterMapping
    default void checkAndSetLists(@MappingTarget GuestRequestsReadResponse readResult, OTAResRetrieveRS ota) {
        ListInitializer.checkAndSetLists(readResult);
    }

    @InheritInverseConfiguration
    @Mapping(target = "version", constant = "7.000")
    @Mapping(target = "timeStamp", ignore = true)
    OTAResRetrieveRS toOTAResRetrieveRS(GuestRequestsReadResponse readResult);

    @AfterMapping
    default void checkAndRemoveOTAParents(@MappingTarget OTAResRetrieveRS ota, GuestRequestsReadResponse readResult) {
        ListParentCleaner.checkAndRemoveOTAParents(ota);
    }

    @AfterMapping
    default void removeOTAReservationListOnError(@MappingTarget OTAResRetrieveRS ota, GuestRequestsReadResponse readResult) {
        if (ota.getErrors() != null) {
            ota.setReservationsList(null);
        }
    }

}
