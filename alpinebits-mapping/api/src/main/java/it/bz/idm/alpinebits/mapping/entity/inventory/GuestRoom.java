/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.inventory;

import java.util.List;

/**
 * This class represents a single GuestRoom in an
 * AlpineBits Inventory push/pull request.
 */
public class GuestRoom {

    private String id;

    private String code;

    private Integer minOccupancy;

    private Integer maxOccupancy;

    private Integer maxChildOccupancy;

    private TypeRoom typeRoom;

    private List<Integer> roomAmenityCodes;

    private List<TextItemDescription> longNames;

    private List<TextItemDescription> descriptions;

    private List<ImageItem> pictures;

    private List<ImageItem> hotelInfoPictures;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMinOccupancy() {
        return minOccupancy;
    }

    public void setMinOccupancy(Integer minOccupancy) {
        this.minOccupancy = minOccupancy;
    }

    public Integer getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(Integer maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public Integer getMaxChildOccupancy() {
        return maxChildOccupancy;
    }

    public void setMaxChildOccupancy(Integer maxChildOccupancy) {
        this.maxChildOccupancy = maxChildOccupancy;
    }

    public TypeRoom getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(TypeRoom typeRoom) {
        this.typeRoom = typeRoom;
    }

    public List<Integer> getRoomAmenityCodes() {
        return roomAmenityCodes;
    }

    public void setRoomAmenityCodes(List<Integer> roomAmenityCodes) {
        this.roomAmenityCodes = roomAmenityCodes;
    }

    public List<TextItemDescription> getLongNames() {
        return longNames;
    }

    public void setLongNames(List<TextItemDescription> longNames) {
        this.longNames = longNames;
    }

    public List<TextItemDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<TextItemDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public List<ImageItem> getPictures() {
        return pictures;
    }

    public void setPictures(List<ImageItem> pictures) {
        this.pictures = pictures;
    }

    public List<ImageItem> getHotelInfoPictures() {
        return hotelInfoPictures;
    }

    public void setHotelInfoPictures(List<ImageItem> hotelInfoPictures) {
        this.hotelInfoPictures = hotelInfoPictures;
    }

    @Override
    public String toString() {
        return "GuestRoom{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", minOccupancy=" + minOccupancy +
                ", maxOccupancy=" + maxOccupancy +
                ", maxChildOccupancy=" + maxChildOccupancy +
                ", typeRoom=" + typeRoom +
                ", roomAmenityCode=" + roomAmenityCodes +
                ", longName=" + longNames +
                ", descriptions=" + descriptions +
                ", pictures=" + pictures +
                ", hotelInfoPictures=" + hotelInfoPictures +
                '}';
    }
}
