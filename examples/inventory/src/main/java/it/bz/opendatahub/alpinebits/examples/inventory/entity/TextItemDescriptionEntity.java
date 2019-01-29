/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.inventory.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * A TextItemDescriptionEntity is used in an AlpineBits Inventory
 * push/pull request in several places.
 */
@Entity
@Table(name = "text_item_description")
public class TextItemDescriptionEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String textFormat;

    private String language;

    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TextItemDescriptionEntity that = (TextItemDescriptionEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TextItemDescriptionEntity{" +
                "id=" + id +
                ", textFormat='" + textFormat + '\'' +
                ", language='" + language + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
