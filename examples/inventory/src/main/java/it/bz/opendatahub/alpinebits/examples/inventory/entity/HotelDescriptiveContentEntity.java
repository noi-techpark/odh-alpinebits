/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.inventory.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.Objects;

/**
 * The HotelDescriptiveContentEntity for AlpineBits Inventory/Basic and
 * Inventory/HotelInfo requests and responses.
 */
@Entity
@Table(name = "hotel_descriptive_content")
public class HotelDescriptiveContentEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String hotelCode;

    private String hotelName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomCategoryEntity> roomCategories;

    @Transient
    private Object affiliationInfo;

    @Transient
    private Object contactInfos;

    @Transient
    private Object hotelInfo;

    @Transient
    private Object policies;

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

    public List<RoomCategoryEntity> getRoomCategories() {
        return roomCategories;
    }

    public void setRoomCategories(List<RoomCategoryEntity> roomCategories) {
        this.roomCategories = roomCategories;
    }

    public Object getAffiliationInfo() {
        return affiliationInfo;
    }

    public void setAffiliationInfo(Object affiliationInfo) {
        this.affiliationInfo = affiliationInfo;
    }

    public Object getContactInfos() {
        return contactInfos;
    }

    public void setContactInfos(Object contactInfos) {
        this.contactInfos = contactInfos;
    }

    public Object getHotelInfo() {
        return hotelInfo;
    }

    public void setHotelInfo(Object hotelInfo) {
        this.hotelInfo = hotelInfo;
    }

    public Object getPolicies() {
        return policies;
    }

    public void setPolicies(Object policies) {
        this.policies = policies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HotelDescriptiveContentEntity that = (HotelDescriptiveContentEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "HotelDescriptiveContentEntity{" +
                "id=" + id +
                ", hotelCode='" + hotelCode + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", roomCategories=" + roomCategories +
                ", affiliationInfo=" + affiliationInfo +
                ", contactInfos=" + contactInfos +
                ", hotelInfo=" + hotelInfo +
                ", policies=" + policies +
                '}';
    }
}
