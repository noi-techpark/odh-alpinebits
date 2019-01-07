/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.inventory.mapper;

import it.bz.idm.alpinebits.examples.inventory.entity.HotelDescriptiveContentEntity;
import it.bz.idm.alpinebits.examples.inventory.entity.RoomCategoryEntity;
import it.bz.idm.alpinebits.mapping.entity.inventory.GuestRoom;
import it.bz.idm.alpinebits.mapping.entity.inventory.HotelDescriptiveContent;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Map {@link HotelDescriptiveContent} to {@link HotelDescriptiveContentEntity}
 * and vice versa.
 */
@Mapper(
        uses = {
                AfterRoomCategoryEntityMapping.class,
                ImageItemEntityMapper.class,
                TextItemDescriptionEntityMapper.class
        }
)
public interface RoomCategoryEntityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "typeRoom.id", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "roomAmenityCodes", ignore = true)
    @Mapping(target = "guestRoomId", source = "id")
    RoomCategoryEntity toRoomCategoryEntity(GuestRoom guestRoom);

    @InheritInverseConfiguration
    @Mapping(target = "roomAmenityCodes", ignore = true)
    GuestRoom toGuestRoom(RoomCategoryEntity roomCategoryEntity);
}
