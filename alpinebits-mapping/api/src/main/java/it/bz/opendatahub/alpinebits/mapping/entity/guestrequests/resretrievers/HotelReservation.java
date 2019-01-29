/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * This class represents a hotel reservation.
 * <p>
 * It provides information about the current reservation,
 * like customer, company and room stays.
 */
public class HotelReservation {

    private String id;

    private ReservationStatus resStatus;

    private Customer customer;

    private GlobalInfo globalInfo;

    private List<RoomStay> roomStays;

    private ZonedDateTime created;

    private ZonedDateTime updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReservationStatus getResStatus() {
        return resStatus;
    }

    public void setResStatus(ReservationStatus resStatus) {
        this.resStatus = resStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public GlobalInfo getGlobalInfo() {
        return globalInfo;
    }

    public void setGlobalInfo(GlobalInfo globalInfo) {
        this.globalInfo = globalInfo;
    }

    public List<RoomStay> getRoomStays() {
        return roomStays;
    }

    public void setRoomStays(List<RoomStay> roomStays) {
        this.roomStays = roomStays;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "HotelReservation{" +
                "id='" + id + '\'' +
                ", resStatus=" + resStatus +
                ", customer=" + customer +
                ", globalInfo=" + globalInfo +
                ", roomStays=" + roomStays +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
