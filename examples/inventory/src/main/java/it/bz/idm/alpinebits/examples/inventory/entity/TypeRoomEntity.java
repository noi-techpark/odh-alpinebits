/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.inventory.entity;

import it.bz.idm.alpinebits.mapping.entity.inventory.GuestRoom;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * The TypeRoomEntity in an AlpineBits Inventory push/pull
 * request contains additional data for a
 * {@link GuestRoom}.
 */
@Entity
@Table(name = "image_item")
public class TypeRoomEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Integer standardOccupancy;

    private Integer roomClassificationCode;

    private Integer size;

    private String roomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStandardOccupancy() {
        return standardOccupancy;
    }

    public void setStandardOccupancy(Integer standardOccupancy) {
        this.standardOccupancy = standardOccupancy;
    }

    public Integer getRoomClassificationCode() {
        return roomClassificationCode;
    }

    public void setRoomClassificationCode(Integer roomClassificationCode) {
        this.roomClassificationCode = roomClassificationCode;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TypeRoomEntity that = (TypeRoomEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TypeRoomEntity{" +
                "id=" + id +
                ", standardOccupancy=" + standardOccupancy +
                ", roomClassificationCode=" + roomClassificationCode +
                ", size=" + size +
                ", roomId='" + roomId + '\'' +
                '}';
    }
}
