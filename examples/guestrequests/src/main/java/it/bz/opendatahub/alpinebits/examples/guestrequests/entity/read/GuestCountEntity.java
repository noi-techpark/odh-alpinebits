/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.guestrequests.entity.read;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Guest count for a {@link RoomStayEntity}, that defines the number
 * of guests for a given age.
 */
@Entity
@Table(name = "guest_count")
public class GuestCountEntity implements Serializable {

    private static final long serialVersionUID = 5575101625948216898L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private RoomStayEntity roomStay;

    private Integer count;

    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomStayEntity getRoomStay() {
        return roomStay;
    }

    public void setRoomStay(RoomStayEntity roomStay) {
        this.roomStay = roomStay;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GuestCountEntity that = (GuestCountEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GuestCountEntity{" +
                "id=" + id +
                "roomStay=" + (roomStay != null ? roomStay.getId() : null) +
                ", count=" + count +
                ", age=" + age +
                '}';
    }
}
