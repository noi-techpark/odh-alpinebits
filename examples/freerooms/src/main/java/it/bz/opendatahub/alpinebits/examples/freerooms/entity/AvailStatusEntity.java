/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.freerooms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

/**
 * This class represents an AlpineBits FreeRooms AvailStatusMessage.
 */
@Entity
@Table(name = "avail_status")
public class AvailStatusEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "freeroom_id")
    private FreeRoomsEntity freeRoom;

    private LocalDate start;
    private LocalDate end;
    private String invTypeCode;
    private String invCode;
    private Integer bookingLimit;
    private Integer bookingThreshold;
    private String bookingLimitMessageType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FreeRoomsEntity getFreeRoom() {
        return freeRoom;
    }

    public void setFreeRoom(FreeRoomsEntity freeRoom) {
        this.freeRoom = freeRoom;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getInvTypeCode() {
        return invTypeCode;
    }

    public void setInvTypeCode(String invTypeCode) {
        this.invTypeCode = invTypeCode;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public Integer getBookingLimit() {
        return bookingLimit;
    }

    public void setBookingLimit(Integer bookingLimit) {
        this.bookingLimit = bookingLimit;
    }

    public Integer getBookingThreshold() {
        return bookingThreshold;
    }

    public void setBookingThreshold(Integer bookingThreshold) {
        this.bookingThreshold = bookingThreshold;
    }

    public String getBookingLimitMessageType() {
        return bookingLimitMessageType;
    }

    public void setBookingLimitMessageType(String bookingLimitMessageType) {
        this.bookingLimitMessageType = bookingLimitMessageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AvailStatusEntity that = (AvailStatusEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AvailStatusEntity{" +
                "id=" + id +
                ", freeRoom=" + (freeRoom != null ? freeRoom.getId() : null) +
                ", start=" + start +
                ", end=" + end +
                ", invTypeCode='" + invTypeCode + '\'' +
                ", invCode='" + invCode + '\'' +
                ", bookingLimit=" + bookingLimit +
                ", bookingThreshold=" + bookingThreshold +
                ", bookingLimitMessageType='" + bookingLimitMessageType + '\'' +
                '}';
    }
}
