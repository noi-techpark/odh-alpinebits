/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The time span for a {@link RoomStay}.
 */
public class TimeSpan implements Serializable {

    private LocalDate start;

    private LocalDate end;

    private Integer duration;

    private LocalDate earliestDate;

    private LocalDate latestDate;

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
    public String toString() {
        return "TimeSpan{" +
                "start=" + start +
                ", end=" + end +
                ", duration=" + duration +
                ", earliestDate=" + earliestDate +
                ", latestDate=" + latestDate +
                '}';
    }
}
