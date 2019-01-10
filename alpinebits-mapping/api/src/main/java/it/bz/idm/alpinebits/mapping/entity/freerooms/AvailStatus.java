/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.freerooms;

import java.time.LocalDate;

/**
 * This class represents an AlpineBits FreeRooms AvailStatusMessage.
 */
public class AvailStatus {

    private LocalDate start;
    private LocalDate end;
    private String invTypeCode;
    private String invCode;
    private Integer bookingLimit;
    private Integer bookingThreshold;
    private String bookingLimitMessageType;

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
    public String toString() {
        return "AvailStatus{" +
                "start=" + start +
                ", end=" + end +
                ", invTypeCode='" + invTypeCode + '\'' +
                ", invCode='" + invCode + '\'' +
                ", bookingLimit=" + bookingLimit +
                ", bookingThreshold=" + bookingThreshold +
                ", bookingLimitMessageType='" + bookingLimitMessageType + '\'' +
                '}';
    }
}
