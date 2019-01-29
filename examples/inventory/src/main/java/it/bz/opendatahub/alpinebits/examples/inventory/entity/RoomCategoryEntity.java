/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.inventory.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a single GuestRoomEntity in an
 * AlpineBits Inventory push/pull request.
 */
@Entity
@Table(name = "room_category")
public class RoomCategoryEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String guestRoomId;

    private String code;

    private Integer minOccupancy;

    private Integer maxOccupancy;

    private Integer maxChildOccupancy;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomEntity> rooms;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private TypeRoomEntity typeRoom;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomAmenityCodeEntity> roomAmenityCodes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "room_category_longnames_id")
    private List<TextItemDescriptionEntity> longNames;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "room_category_descriptions_id")
    private List<TextItemDescriptionEntity> descriptions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "room_category_pictures_id")
    private List<ImageItemEntity> pictures;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "room_category_hotelinfopictures_id")
    private List<ImageItemEntity> hotelInfoPictures;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuestRoomId() {
        return guestRoomId;
    }

    public void setGuestRoomId(String guestRoomId) {
        this.guestRoomId = guestRoomId;
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

    public List<RoomEntity> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }

    public TypeRoomEntity getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(TypeRoomEntity typeRoom) {
        this.typeRoom = typeRoom;
    }

    public List<RoomAmenityCodeEntity> getRoomAmenityCodes() {
        return roomAmenityCodes;
    }

    public void setRoomAmenityCodes(List<RoomAmenityCodeEntity> roomAmenityCodes) {
        this.roomAmenityCodes = roomAmenityCodes;
    }

    public List<TextItemDescriptionEntity> getLongNames() {
        return longNames;
    }

    public void setLongNames(List<TextItemDescriptionEntity> longNames) {
        this.longNames = longNames;
    }

    public List<TextItemDescriptionEntity> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<TextItemDescriptionEntity> descriptions) {
        this.descriptions = descriptions;
    }

    public List<ImageItemEntity> getPictures() {
        return pictures;
    }

    public void setPictures(List<ImageItemEntity> pictures) {
        this.pictures = pictures;
    }

    public List<ImageItemEntity> getHotelInfoPictures() {
        return hotelInfoPictures;
    }

    public void setHotelInfoPictures(List<ImageItemEntity> hotelInfoPictures) {
        this.hotelInfoPictures = hotelInfoPictures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoomCategoryEntity that = (RoomCategoryEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "RoomCategoryEntity{" +
                "id=" + id +
                ", guestRoomId='" + guestRoomId + '\'' +
                ", code='" + code + '\'' +
                ", minOccupancy=" + minOccupancy +
                ", maxOccupancy=" + maxOccupancy +
                ", maxChildOccupancy=" + maxChildOccupancy +
                ", rooms=" + rooms +
                ", typeRoom=" + typeRoom +
                ", roomAmenityCodes=" + roomAmenityCodes +
                ", longNames=" + longNames +
                ", descriptions=" + descriptions +
                ", pictures=" + pictures +
                ", hotelInfoPictures=" + hotelInfoPictures +
                '}';
    }
}
