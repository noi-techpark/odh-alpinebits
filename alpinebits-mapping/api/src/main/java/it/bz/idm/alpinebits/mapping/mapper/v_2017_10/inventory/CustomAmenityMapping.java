/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.mapper.v_2017_10.inventory;

import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ;
import org.mapstruct.Mapper;

import java.math.BigInteger;

/**
 * This class implements dedicated mapping strategies to convert
 * between AlpineBits Amenity (Inventory) and simple
 * {@link Integer} objects.
 */
@Mapper(
        uses = {
                AfterGuestRoomMapping.class,
                ImageItemMapper.class,
                TextItemDescriptionMapper.class,
                TypeRoomMapper.class,
        }
)
public interface CustomAmenityMapping {

    default Integer toRoomAmenityCode(OTAHotelDescriptiveContentNotifRQ
                                              .HotelDescriptiveContents
                                              .HotelDescriptiveContent
                                              .FacilityInfo.GuestRooms
                                              .GuestRoom
                                              .Amenities
                                              .Amenity amenity) {
        return amenity.getRoomAmenityCode().intValue();
    }

    default OTAHotelDescriptiveContentNotifRQ
            .HotelDescriptiveContents
            .HotelDescriptiveContent
            .FacilityInfo
            .GuestRooms
            .GuestRoom
            .Amenities
            .Amenity toOTAAmenity(Integer amenityCode) {
        OTAHotelDescriptiveContentNotifRQ
                .HotelDescriptiveContents
                .HotelDescriptiveContent
                .FacilityInfo
                .GuestRooms
                .GuestRoom
                .Amenities
                .Amenity amenity = new OTAHotelDescriptiveContentNotifRQ
                .HotelDescriptiveContents
                .HotelDescriptiveContent
                .FacilityInfo
                .GuestRooms
                .GuestRoom
                .Amenities
                .Amenity();
        amenity.setRoomAmenityCode(BigInteger.valueOf(amenityCode));
        return amenity;
    }

}
