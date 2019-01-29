/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.inventory;

/**
 * The TypeRoom in an AlpineBits Inventory push/pull
 * request contains additional data for a
 * {@link GuestRoom}.
 */
public class TypeRoom {

    private Integer standardOccupancy;

    private Integer roomClassificationCode;

    private Integer size;

    private String roomId;

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
    public String toString() {
        return "TypeRoom{" +
                "standardOccupancy=" + standardOccupancy +
                ", roomClassificationCode=" + roomClassificationCode +
                ", size=" + size +
                ", roomId='" + roomId + '\'' +
                '}';
    }
}
