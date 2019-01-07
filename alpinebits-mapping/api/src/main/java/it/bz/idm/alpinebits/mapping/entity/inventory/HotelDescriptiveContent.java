/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.inventory;

import java.util.List;

/**
 * The HotelDescriptiveContent for AlpineBits Inventory/Basic and
 * Inventory/HotelInfo requests and responses.
 */
public class HotelDescriptiveContent {

    private String hotelCode;

    private String hotelName;

    private List<GuestRoom> guestRooms;

    private Object affiliationInfo;

    private Object contactInfos;

    private Object hotelInfo;

    private Object policies;

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

    public List<GuestRoom> getGuestRooms() {
        return guestRooms;
    }

    public void setGuestRooms(List<GuestRoom> guestRooms) {
        this.guestRooms = guestRooms;
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
    public String toString() {
        return "HotelDescriptiveContent{" +
                "hotelCode='" + hotelCode + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", guestRooms=" + guestRooms +
                ", affiliationInfo=" + affiliationInfo +
                ", contactInfos=" + contactInfos +
                ", hotelInfo=" + hotelInfo +
                ", policies=" + policies +
                '}';
    }
}
