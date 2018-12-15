/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers;

/**
 * This class represents the company in an AlpineBits GuestRequest
 * read request (OTA_ResRetrieveRS).
 */
public class Company {

    private String name;

    private String code;

    private String codeContext;

    private CompanyEmail email;

    private CompanyAddress address;

    private CompanyTelephone telephone;

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

    public CompanyEmail getEmail() {
        return email;
    }

    public void setEmail(CompanyEmail email) {
        this.email = email;
    }

    public CompanyAddress getAddress() {
        return address;
    }

    public void setAddress(CompanyAddress address) {
        this.address = address;
    }

    public CompanyTelephone getTelephone() {
        return telephone;
    }

    public void setTelephone(CompanyTelephone telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", codeContext='" + codeContext + '\'' +
                ", email=" + email +
                ", address=" + address +
                ", telephone=" + telephone +
                '}';
    }
}
