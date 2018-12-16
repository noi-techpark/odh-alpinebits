/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.guestrequests.entity.read;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents the company in an AlpineBits GuestRequest
 * for reservation data (OTA_ResRetrieveRS).
 */
@Entity
@Table(name = "company")
public class CompanyEntity implements Serializable {

    private static final long serialVersionUID = -4060936684525049867L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String code;

    private String codeContext;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
    private CompanyEmailEntity email;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
    private CompanyAddressEntity address;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
    private CompanyTelephoneEntity telephone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeContext() {
        return codeContext;
    }

    public void setCodeContext(String codeContext) {
        this.codeContext = codeContext;
    }

    public CompanyEmailEntity getEmail() {
        return email;
    }

    public void setEmail(CompanyEmailEntity email) {
        this.email = email;
    }

    public CompanyAddressEntity getAddress() {
        return address;
    }

    public void setAddress(CompanyAddressEntity address) {
        this.address = address;
    }

    public CompanyTelephoneEntity getTelephone() {
        return telephone;
    }

    public void setTelephone(CompanyTelephoneEntity telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyEntity company = (CompanyEntity) o;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", codeContext='" + codeContext + '\'' +
                ", email=" + email +
                ", address=" + address +
                ", telephone=" + telephone +
                '}';
    }
}
