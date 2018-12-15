/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers;

/**
 * This class represents a {@link Customer} telephone number.
 */
public class CustomerTelephone extends BaseTelephone {

    @Override
    public String toString() {
        return "CustomerTelephone{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", phoneTechType='" + phoneTechType + '\'' +
                '}';
    }

}
