/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.dbserver.entity.guestrequests.read;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a {@link CompanyEntity} address.
 */
@Entity
@Table(name = "company_address")
public class CompanyAddressEntity implements Serializable {

    private static final long serialVersionUID = 2898655418052730746L;

    @Id
    @MapsId
    @OneToOne
    private CompanyEntity company;

    private String addressLine;

    private String cityName;

    private String postalCode;

    private String countryNameCode;

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryNameCode() {
        return countryNameCode;
    }

    public void setCountryNameCode(String countryNameCode) {
        this.countryNameCode = countryNameCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyAddressEntity that = (CompanyAddressEntity) o;
        return Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company);
    }

    @Override
    public String toString() {
        return "CompanyAddressEntity{" +
                "company=" + (company != null ? company.getId() : null) +
                ", addressLine='" + addressLine + '\'' +
                ", cityName='" + cityName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", countryNameCode='" + countryNameCode + '\'' +
                '}';
    }
}
