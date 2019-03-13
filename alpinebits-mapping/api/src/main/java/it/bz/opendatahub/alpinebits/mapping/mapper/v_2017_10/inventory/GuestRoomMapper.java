/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.mapping.entity.inventory.GuestRoom;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convert between AlpineBits GuestRoom
 * (Inventory) and {@link GuestRoom} objects.
 */
@Mapper(
        uses = {
                CustomAmenityMapping.class,
                AfterGuestRoomMapping.class,
                ImageItemMapper.class,
                TextItemDescriptionMapper.class,
                TypeRoomMapper.class,
        }
)
public interface GuestRoomMapper {

    @Mapping(target = "id", source = "ID")
    @Mapping(target = "roomAmenityCodes", source = "amenities.amenities")
    @Mapping(target = "longNames", ignore = true)
    @Mapping(target = "descriptions", ignore = true)
    @Mapping(target = "pictures", ignore = true)
    @Mapping(target = "hotelInfoPictures", ignore = true)
    GuestRoom toGuestRoom(OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom guestRoom);

    @InheritInverseConfiguration
    @Mapping(target = "multimediaDescriptions", ignore = true)
    OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent
            .FacilityInfo.GuestRooms.GuestRoom toOTAGuestRoom(GuestRoom guestRoom);

}
