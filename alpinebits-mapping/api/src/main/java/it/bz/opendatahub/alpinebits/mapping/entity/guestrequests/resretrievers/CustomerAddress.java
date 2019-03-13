/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers;

/**
 * This class represents a {@link Customer} address.
 */
public class CustomerAddress extends BaseAddress {

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "CustomerAddress{" +
                "addressLine='" + addressLine + '\'' +
                ", cityName='" + cityName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", countryNameCode='" + countryNameCode + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
