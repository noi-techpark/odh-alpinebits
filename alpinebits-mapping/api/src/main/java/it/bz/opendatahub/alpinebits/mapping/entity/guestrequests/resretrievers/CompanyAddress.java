/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers;

/**
 * This class represents a {@link Company} address.
 */
public class CompanyAddress extends BaseAddress {

    @Override
    public String toString() {
        return "CompanyAddress{" +
                "addressLine='" + addressLine + '\'' +
                ", cityName='" + cityName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", countryNameCode='" + countryNameCode + '\'' +
                '}';
    }

}
