/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.inventory;

/**
 * A TextItemDescription is used in an AlpineBits Inventory
 * push/pull request in several places.
 */
public class TextItemDescription {

    private String textFormat;

    private String language;

    private String value;

    public String getTextFormat() {
        return textFormat;
    }

    public void setTextFormat(String textFormat) {
        this.textFormat = textFormat;
    }

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
        return "TextItemDescription{" +
                "textFormat='" + textFormat + '\'' +
                ", language='" + language + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
