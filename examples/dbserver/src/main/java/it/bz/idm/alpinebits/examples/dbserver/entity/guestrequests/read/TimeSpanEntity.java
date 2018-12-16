/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.dbserver.entity.guestrequests.read;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The time span for a {@link RoomStayEntity}.
 */
@Entity
@Table(name = "time_span")
public class TimeSpanEntity implements Serializable {

    private static final long serialVersionUID = -3454563768424897743L;

    @Id
    @MapsId
    @OneToOne
    private RoomStayEntity roomStay;

    private LocalDate start;

    private LocalDate end;

    private Integer duration;

    private LocalDate earliestDate;

    private LocalDate latestDate;

    public RoomStayEntity getRoomStay() {
        return roomStay;
    }

    public void setRoomStay(RoomStayEntity roomStay) {
        this.roomStay = roomStay;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getEarliestDate() {
        return earliestDate;
    }

    public void setEarliestDate(LocalDate earliestDate) {
        this.earliestDate = earliestDate;
    }

    public LocalDate getLatestDate() {
        return latestDate;
    }

    public void setLatestDate(LocalDate latestDate) {
        this.latestDate = latestDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeSpanEntity timeSpan = (TimeSpanEntity) o;
        return Objects.equals(roomStay, timeSpan.roomStay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomStay);
    }

    @Override
    public String toString() {
        return "TimeSpanEntity{" +
                "roomStay=" + (roomStay != null ? roomStay.getId() : null) +
                ", start=" + start +
                ", end=" + end +
                ", duration=" + duration +
                ", earliestDate=" + earliestDate +
                ", latestDate=" + latestDate +
                '}';
    }
}
