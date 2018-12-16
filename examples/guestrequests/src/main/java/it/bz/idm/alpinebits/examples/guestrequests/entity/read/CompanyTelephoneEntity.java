/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.guestrequests.entity.read;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a {@link CompanyEntity} telephone.
 */
@Entity
@Table(name = "company_telephone")
public class CompanyTelephoneEntity implements Serializable {

    private static final long serialVersionUID = -7452128523181359166L;

    @Id
    @MapsId
    @OneToOne
    private CompanyEntity company;

    private String phoneNumber;

    private String phoneTechType;

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneTechType() {
        return phoneTechType;
    }

    public void setPhoneTechType(String phoneTechType) {
        this.phoneTechType = phoneTechType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyTelephoneEntity that = (CompanyTelephoneEntity) o;
        return Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company);
    }

    @Override
    public String toString() {
        return "CompanyTelephoneEntity{" +
                "company=" + (company != null ? company.getId() : null) +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", phoneTechType='" + phoneTechType + '\'' +
                '}';
    }
}

