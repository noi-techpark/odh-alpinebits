/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.freerooms.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 * The FreeRoomsEntity for AlpineBits FreeRooms requests.
 */
@Entity
@Table(name = "free_rooms")
public class FreeRoomsEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String hotelCode;

    private String hotelName;

    @OneToMany(mappedBy = "freeRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailStatusEntity> availStatuses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<AvailStatusEntity> getAvailStatuses() {
        return availStatuses;
    }

    public void setAvailStatuses(List<AvailStatusEntity> availStatuses) {
        this.availStatuses = availStatuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FreeRoomsEntity that = (FreeRoomsEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FreeRoomsEntity{" +
                "id=" + id +
                ", hotelCode='" + hotelCode + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", availStatuses=" + availStatuses +
                '}';
    }
}
