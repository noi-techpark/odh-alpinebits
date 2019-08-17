/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory.common;

import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.ImageItems.ImageItem;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.TextItems.TextItem;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This validation unifies the Description elements of
 * {@link ImageItem} and {@link TextItem} elements to
 * simplify their validation.
 */
public class Description {

    private final String value;
    private final String textFormat;
    private final String language;

    public Description(String value, String textFormat, String language) {
        this.value = value;
        this.textFormat = textFormat;
        this.language = language;
    }

    public static Description fromImageItemDescription(ImageItem.Description iid) {
        return new Description(iid.getValue(), iid.getTextFormat(), iid.getLanguage());
    }

    public static List<Description> fromImageItemDescriptions(List<ImageItem.Description> descriptions) {
        return descriptions.stream()
                .map(Description::fromImageItemDescription)
                .collect(Collectors.toList());
    }

    public static Description fromTextItemDescription(TextItem.Description iid) {
        return new Description(iid.getValue(), iid.getTextFormat(), iid.getLanguage());
    }

    public static List<Description> fromTextItemDescriptions(List<TextItem.Description> descriptions) {
        return descriptions.stream()
                .map(Description::fromTextItemDescription)
                .collect(Collectors.toList());
    }

    public String getValue() {
        return value;
    }

    public String getTextFormat() {
        return textFormat;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Description that = (Description) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(textFormat, that.textFormat) &&
                Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, textFormat, language);
    }

    @Override
    public String toString() {
        return "Description{" +
                "value='" + value + '\'' +
                ", textFormat='" + textFormat + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
