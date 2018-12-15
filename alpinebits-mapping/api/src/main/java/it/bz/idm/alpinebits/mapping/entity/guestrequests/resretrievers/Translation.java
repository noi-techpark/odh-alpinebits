/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers;

/**
 * Item translation.
 */
public class Translation {

    private String language;

    private String value;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "language='" + language + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
