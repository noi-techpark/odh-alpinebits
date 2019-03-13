/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.guestrequests.entity.read;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * This class represents a hotel reservation.
 * <p>
 * It provides information about the current reservation,
 * like customer, company and room stays.
 */
@Entity
@Table(name = "hotel_reservation")
public class HotelReservationEntity implements Serializable {

    private static final long serialVersionUID = 6010645219037901245L;

    @Id
    private String id;

    private String resStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private CustomerEntity customer;

    @OneToOne(mappedBy = "hotelReservation", cascade = CascadeType.ALL)
    private GlobalInfoEntity globalInfo;

    @OneToMany(mappedBy = "hotelReservation", cascade = CascadeType.ALL)
    private List<RoomStayEntity> roomStays;

    private ZonedDateTime created;

    private ZonedDateTime updated;

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        created = ZonedDateTime.now();
        updated = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = ZonedDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public GlobalInfoEntity getGlobalInfo() {
        return globalInfo;
    }

    public void setGlobalInfo(GlobalInfoEntity globalInfo) {
        this.globalInfo = globalInfo;
    }

    public List<RoomStayEntity> getRoomStays() {
        return roomStays;
    }

    public void setRoomStays(List<RoomStayEntity> roomStays) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HotelReservationEntity that = (HotelReservationEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "HotelReservationEntity{" +
                "id=" + id +
                ", resStatus='" + resStatus + '\'' +
                ", customer=" + customer +
                ", globalInfo=" + globalInfo +
                ", roomStays=" + roomStays +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}