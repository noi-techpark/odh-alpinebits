/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers;

/**
 * The base class for addresses, as used by
 * {@link CompanyAddress} and {@link CustomerAddress}.
 */
public class BaseAddress {

    protected String addressLine;

    protected String cityName;

    protected String postalCode;

    protected String countryNameCode;

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
    public String toString() {
        return "BaseAddress{" +
                "addressLine='" + addressLine + '\'' +
                ", cityName='" + cityName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", countryNameCode='" + countryNameCode + '\'' +
                '}';
    }
}
