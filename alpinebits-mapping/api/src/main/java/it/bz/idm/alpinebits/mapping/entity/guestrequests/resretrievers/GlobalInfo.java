/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers;

import java.util.List;

/**
 * This class represents the GlobalInfo in an AlpineBits GuestRequest
 * read request (OTA_ResRetrieveRS).
 * <p>
 * It provides global information for a {@link HotelReservation}.
 * <p>
 * This includes the company (e.g. hotel), comments, penalty description
 * and additional unique identifiers (see {@link HotelReservationId}).
 * <p>
 * Each GlobalInfo instance belongs to exactly one {@link HotelReservation}
 */
public class GlobalInfo {

    private String customerComment;

    private String penaltyDescription;

    private Company company;

    private List<HotelReservationId> hotelReservationIds;

    private List<Translation> includedServices;

    public String getCustomerComment() {
        return customerComment;
    }

    public void setCustomerComment(String customerComment) {
        this.customerComment = customerComment;
    }

    public String getPenaltyDescription() {
        return penaltyDescription;
    }

    public void setPenaltyDescription(String penaltyDescription) {
        this.penaltyDescription = penaltyDescription;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<HotelReservationId> getHotelReservationIds() {
        return hotelReservationIds;
    }

    public void setHotelReservationIds(List<HotelReservationId> hotelReservationIds) {
        this.hotelReservationIds = hotelReservationIds;
    }

    public List<Translation> getIncludedServices() {
        return includedServices;
    }

    public void setIncludedServices(List<Translation> includedServices) {
        this.includedServices = includedServices;
    }

    @Override
    public String toString() {
        return "GlobalInfo{" +
                "customerComment='" + customerComment + '\'' +
                ", penaltyDescription='" + penaltyDescription + '\'' +
                ", company=" + company +
                ", hotelReservationIds=" + hotelReservationIds +
                ", includedServices=" + includedServices +
                '}';
    }
}
