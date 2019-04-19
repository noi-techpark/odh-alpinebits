/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.inventory;

import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.AffiliationInfoType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfosType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelDescriptiveContentType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelInfoType;

import java.util.List;

/**
 * The HotelDescriptiveContent for AlpineBits Inventory/Basic and
 * Inventory/HotelInfo requests and responses.
 */
public class HotelDescriptiveContent {

    private String hotelCode;

    private String hotelName;

    private List<GuestRoom> guestRooms;

    private AffiliationInfoType affiliationInfo;

    private ContactInfosType contactInfos;

    private HotelInfoType hotelInfo;

    private HotelDescriptiveContentType.Policies policies;

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

    public AffiliationInfoType getAffiliationInfo() {
        return affiliationInfo;
    }

    public void setAffiliationInfo(AffiliationInfoType affiliationInfo) {
        this.affiliationInfo = affiliationInfo;
    }

    public ContactInfosType getContactInfos() {
        return contactInfos;
    }

    public void setContactInfos(ContactInfosType contactInfos) {
        this.contactInfos = contactInfos;
    }

    public HotelInfoType getHotelInfo() {
        return hotelInfo;
    }

    public void setHotelInfo(HotelInfoType hotelInfo) {
        this.hotelInfo = hotelInfo;
    }

    public HotelDescriptiveContentType.Policies getPolicies() {
        return policies;
    }

    public void setPolicies(HotelDescriptiveContentType.Policies policies) {
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
