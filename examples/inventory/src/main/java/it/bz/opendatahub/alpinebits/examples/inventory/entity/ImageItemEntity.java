/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.inventory.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * A TextItemDescriptionEntity is used in an AlpineBits Inventory
 * push/pull request for multimedia description.
 */
@Entity
@Table(name = "image_item")
public class ImageItemEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Integer category;

    private String url;

    private String copyrightNotice;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TextItemDescriptionEntity> descriptions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCopyrightNotice() {
        return copyrightNotice;
    }

    public void setCopyrightNotice(String copyrightNotice) {
        this.copyrightNotice = copyrightNotice;
    }

    public List<TextItemDescriptionEntity> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<TextItemDescriptionEntity> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public String toString() {
        return "ImageItemEntity{" +
                "category=" + category +
                ", url='" + url + '\'' +
                ", copyrightNotice='" + copyrightNotice + '\'' +
                ", descriptions=" + descriptions +
                '}';
    }
}
