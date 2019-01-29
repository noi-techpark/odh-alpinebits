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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the company in an AlpineBits GuestRequest
 * for reservation data (OTA_ResRetrieveRS).
 */
@Entity
@Table(name = "customer")
public class CustomerEntity implements Serializable {

    private static final long serialVersionUID = 3521862492481643844L;

    @Id
    @GeneratedValue
    private Long id;

    private String gender;

    private LocalDate birthDate;

    private String language;

    private String namePrefix;

    private String givenName;

    private String surname;

    private String nameTitle;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private CustomerEmailEntity email;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private CustomerAddressEntity address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerTelephoneEntity> telephones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNameTitle() {
        return nameTitle;
    }

    public void setNameTitle(String nameTitle) {
        this.nameTitle = nameTitle;
    }

    public CustomerEmailEntity getEmail() {
        return email;
    }

    public void setEmail(CustomerEmailEntity email) {
        this.email = email;
    }

    public CustomerAddressEntity getAddress() {
        return address;
    }

    public void setAddress(CustomerAddressEntity address) {
        this.address = address;
    }

    public List<CustomerTelephoneEntity> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<CustomerTelephoneEntity> telephones) {
        this.telephones = telephones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerEntity customer = (CustomerEntity) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", language='" + language + '\'' +
                ", namePrefix='" + namePrefix + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surname='" + surname + '\'' +
                ", nameTitle='" + nameTitle + '\'' +
                ", email=" + email +
                ", address=" + address +
                ", telephones=" + telephones +
                '}';
    }
}