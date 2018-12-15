/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents the company in an AlpineBits GuestRequest
 * read request (OTA_ResRetrieveRS).
 */
public class Customer {

    private Gender gender;

    private LocalDate birthDate;

    private String language;

    private String namePrefix;

    private String givenName;

    private String surname;

    private String nameTitle;

    private CustomerEmail email;

    private CustomerAddress address;

    private List<CustomerTelephone> telephones;

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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

    public CustomerEmail getEmail() {
        return email;
    }

    public void setEmail(CustomerEmail email) {
        this.email = email;
    }

    public CustomerAddress getAddress() {
        return address;
    }

    public void setAddress(CustomerAddress address) {
        this.address = address;
    }

    public List<CustomerTelephone> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<CustomerTelephone> telephones) {
        this.telephones = telephones;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "gender=" + gender +
                ", birthDate=" + birthDate +
                ", language=" + language +
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
