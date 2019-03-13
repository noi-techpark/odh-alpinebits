/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.guestrequests.resretrievers;

import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.HotelReservation;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.ReservationStatus;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAResRetrieveRS;
import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Map AlpineBits guest request objects for OTA_ResRetrieveRS
 * requests to business objects and vice versa.
 */
@Mapper(
        uses = {
                CustomerMapper.class,
                GlobalInfoMapper.class,
                RoomStayMapper.class,
        }
)
public interface HotelReservationMapper {

    @Mapping(target = "id", source = "uniqueID.ID")
    @Mapping(target = "customer", source = "resGuests.resGuest.profiles.profileInfo.profile.customer")
    @Mapping(target = "globalInfo", source = "resGlobalInfo")
    @Mapping(target = "roomStays", source = "roomStays.roomStaies")
    @Mapping(target = "created", source = "createDateTime")
    @Mapping(target = "updated", ignore = true)
    HotelReservation toHotelReservation(OTAResRetrieveRS.ReservationsList.HotelReservation hotelReservation);

    @InheritInverseConfiguration
    OTAResRetrieveRS.ReservationsList.HotelReservation toOTAHotelReservation(HotelReservation hotelReservation);

    default ReservationStatus stringToReservationStatus(String s) {
        return ReservationStatus.fromString(s);
    }

    default String reservationStatusToString(ReservationStatus reservationStatus) {
        return reservationStatus == null ? null : reservationStatus.toString();
    }

    @AfterMapping
    default void updateOTAUniqueId(
            @MappingTarget OTAResRetrieveRS.ReservationsList.HotelReservation.UniqueID uniqueId,
            HotelReservation hotelReservation
    ) {
        uniqueId.setID(hotelReservation.getId());
        uniqueId.setType(ReservationStatus.CANCELLED.equals(hotelReservation.getResStatus()) ? "15" : "14");
    }

}
