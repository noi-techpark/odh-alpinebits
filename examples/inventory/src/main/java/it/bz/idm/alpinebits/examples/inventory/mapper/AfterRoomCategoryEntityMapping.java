/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.inventory.mapper;

import it.bz.idm.alpinebits.examples.inventory.entity.RoomCategoryEntity;
import it.bz.idm.alpinebits.mapping.entity.inventory.GuestRoom;
import it.bz.idm.alpinebits.mapping.mapper.v_2017_10.inventory.contentnotifrs.HotelDescriptiveContentNotifResponseMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;

/**
 * The herein declared methods are invoked after
 * {@link HotelDescriptiveContentNotifResponseMapper}
 * has finished to further customize the mapping.
 */
@Mapper
public abstract class AfterRoomCategoryEntityMapping {

    @AfterMapping
    public void updateRoomCategories(@MappingTarget RoomCategoryEntity roomCategoryEntity, GuestRoom guestRoom) {
        if (roomCategoryEntity.getRooms() == null) {
            roomCategoryEntity.setRooms(new ArrayList<>());
        }
        if (roomCategoryEntity.getRoomAmenityCodes() == null) {
            roomCategoryEntity.setRoomAmenityCodes(new ArrayList<>());
        }
        if (roomCategoryEntity.getLongNames() == null) {
            roomCategoryEntity.setLongNames(new ArrayList<>());
        }
        if (roomCategoryEntity.getDescriptions() == null) {
            roomCategoryEntity.setDescriptions(new ArrayList<>());
        }
        if (roomCategoryEntity.getPictures() == null) {
            roomCategoryEntity.setPictures(new ArrayList<>());
        }
        if (roomCategoryEntity.getHotelInfoPictures() == null) {
            roomCategoryEntity.setHotelInfoPictures(new ArrayList<>());
        }
    }

}
