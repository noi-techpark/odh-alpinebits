/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.guestrequests.entity.read;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * This class provides global information for a {@link HotelReservationEntity}.
 * <p>
 * This includes the company (e.g. hotel), comments, penalty description
 * and additional unique identifiers (see {@link HotelReservationIdEntity}).
 * <p>
 * Each GlobalInfoEntity instance belongs to exactly one {@link HotelReservationEntity}
 */
@Entity
@Table(name = "global_info")
public class GlobalInfoEntity implements Serializable {

    private static final long serialVersionUID = -5853619172202280294L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private HotelReservationEntity hotelReservation;

    private String customerComment;

    private String penaltyDescription;

    @OneToOne(cascade = CascadeType.ALL)
    private CompanyEntity company;

    @OneToMany(mappedBy = "globalInfo", cascade = CascadeType.ALL)
    private List<HotelReservationIdEntity> hotelReservationIds;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<TranslationEntity> includedServices;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HotelReservationEntity getHotelReservation() {
        return hotelReservation;
    }

    public void setHotelReservation(HotelReservationEntity hotelReservation) {
        this.hotelReservation = hotelReservation;
    }

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

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public List<HotelReservationIdEntity> getHotelReservationIds() {
        return hotelReservationIds;
    }

    public void setHotelReservationIds(List<HotelReservationIdEntity> hotelReservationIds) {
        this.hotelReservationIds = hotelReservationIds;
    }

    public List<TranslationEntity> getIncludedServices() {
        return includedServices;
    }

    public void setIncludedServices(List<TranslationEntity> includedServices) {
        this.includedServices = includedServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GlobalInfoEntity that = (GlobalInfoEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GlobalInfoEntity{" +
                "id=" + id +
                ", hotelReservation=" + hotelReservation +
                ", customerComment='" + customerComment + '\'' +
                ", penaltyDescription='" + penaltyDescription + '\'' +
                ", company=" + company +
                ", hotelReservationIds=" + hotelReservationIds +
                ", includedServices=" + includedServices +
                '}';
    }
}
