/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers;

/**
 * The base class for telephones, as used by
 * {@link CompanyTelephone} and {@link CustomerTelephone}.
 */
public class BaseTelephone {

    protected String phoneNumber;

    protected String phoneTechType;

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
    public String toString() {
        return "BaseTelephone{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", phoneTechType='" + phoneTechType + '\'' +
                '}';
    }
}
