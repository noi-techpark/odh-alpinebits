/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.inventory.mapper;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.examples.inventory.entity.HotelDescriptiveContentEntity;
import it.bz.opendatahub.alpinebits.examples.inventory.entity.RoomAmenityCodeEntity;
import it.bz.opendatahub.alpinebits.examples.inventory.entity.RoomCategoryEntity;
import it.bz.opendatahub.alpinebits.examples.inventory.entity.RoomEntity;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.GuestRoom;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.TypeRoom;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.inventory.contentnotifrs.HotelDescriptiveContentNotifResponseMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The herein declared methods are invoked after
 * {@link HotelDescriptiveContentNotifResponseMapper}
 * has finished to further customize the mapping.
 */
@Mapper
public abstract class AfterHotelDescriptiveContentEntityMapping {

    private final RoomCategoryEntityMapper roomCategoryEntityMapper = Mappers.getMapper(RoomCategoryEntityMapper.class);

    @AfterMapping
    public void updateRoomCategories(
            @MappingTarget HotelDescriptiveContentEntity hotelDescriptiveContentEntity,
            HotelDescriptiveContent hotelDescriptiveContent
    ) {
        hotelDescriptiveContentEntity.setRoomCategories(new ArrayList<>());

        RoomCategoryEntity currentRoomCategoryEntity = null;
        for (GuestRoom guestRoom : hotelDescriptiveContent.getGuestRooms()) {
            if (isHeadingGuestRoom(guestRoom)) {
                currentRoomCategoryEntity = this.roomCategoryEntityMapper.toRoomCategoryEntity(guestRoom);
                hotelDescriptiveContentEntity.getRoomCategories().add(currentRoomCategoryEntity);

                currentRoomCategoryEntity.setRooms(new ArrayList<>());
                currentRoomCategoryEntity.setRoomAmenityCodes(this.mapRoomAmenityCodeEntities(guestRoom));
            } else {
                if (currentRoomCategoryEntity == null) {
                    throw new AlpineBitsException("No room category found for room " + guestRoom, 500);
                }
                RoomEntity roomEntity = new RoomEntity();
                roomEntity.setRoomId(guestRoom.getTypeRoom().getRoomId());
                currentRoomCategoryEntity.getRooms().add(roomEntity);
            }
        }
    }


    @AfterMapping
    public void updateGuestRooms(
            @MappingTarget HotelDescriptiveContent hotelDescriptiveContent,
            HotelDescriptiveContentEntity hotelDescriptiveContentEntity

    ) {
        List<GuestRoom> guestRooms = new ArrayList<>();

        if (hotelDescriptiveContentEntity.getRoomCategories() != null) {
            for (RoomCategoryEntity roomCategoryEntity : hotelDescriptiveContentEntity.getRoomCategories()) {
                GuestRoom headingGuestRoom = this.roomCategoryEntityMapper.toGuestRoom(roomCategoryEntity);
                headingGuestRoom.setRoomAmenityCodes(this.mapRoomAmenityCodes(roomCategoryEntity));

                guestRooms.add(headingGuestRoom);

                if (roomCategoryEntity.getRooms() != null) {
                    for (RoomEntity roomEntity : roomCategoryEntity.getRooms()) {
                        TypeRoom typeRoom = new TypeRoom();
                        typeRoom.setRoomId(roomEntity.getRoomId());

                        GuestRoom followingGuestRoom = new GuestRoom();
                        followingGuestRoom.setCode(roomCategoryEntity.getCode());
                        followingGuestRoom.setTypeRoom(typeRoom);

                        guestRooms.add(followingGuestRoom);
                    }
                }
            }
        }

        hotelDescriptiveContent.setGuestRooms(guestRooms);
    }

    private boolean isHeadingGuestRoom(GuestRoom guestRoom) {
        return guestRoom.getCode() != null && guestRoom.getMinOccupancy() != null && guestRoom.getMaxOccupancy() != null;
    }

    private List<RoomAmenityCodeEntity> mapRoomAmenityCodeEntities(GuestRoom guestRoom) {
        return guestRoom.getRoomAmenityCodes()
                .stream()
                .map(code -> {
                    RoomAmenityCodeEntity roomAmenityCodeEntity = new RoomAmenityCodeEntity();
                    roomAmenityCodeEntity.setCode(code);
                    return roomAmenityCodeEntity;
                })
                .collect(Collectors.toList());
    }

    private List<Integer> mapRoomAmenityCodes(RoomCategoryEntity roomCategoryEntity) {
        return roomCategoryEntity.getRoomAmenityCodes()
                .stream()
                .map(code -> code.getCode())
                .collect(Collectors.toList());
    }

}
