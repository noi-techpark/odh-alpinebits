/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.guestrequests.entity.read;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a {@link CompanyEntity} email.
 */
@Entity
@Table(name = "company_email")
public class CompanyEmailEntity implements Serializable {

    private static final long serialVersionUID = -6709588768995601690L;

    @Id
    @MapsId
    @OneToOne
    private CompanyEntity company;

    private String email;

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyEmailEntity that = (CompanyEmailEntity) o;
        return Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company);
    }

    @Override
    public String toString() {
        return "CompanyEmailEntity{" +
                "company=" + (company != null ? company.getId() : null) +
                ", email='" + email + '\'' +
                '}';
    }
}
