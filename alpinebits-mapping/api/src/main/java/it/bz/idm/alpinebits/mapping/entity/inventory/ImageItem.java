/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.inventory;

import java.util.List;

/**
 * A TextItemDescription is used in an AlpineBits Inventory
 * push/pull request for multimedia description.
 */
public class ImageItem {

    private Integer category;

    private String url;

    private String copyrightNotice;

    private List<TextItemDescription> descriptions;

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

    public List<TextItemDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<TextItemDescription> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public String toString() {
        return "ImageItem{" +
                "category=" + category +
                ", url='" + url + '\'' +
                ", copyrightNotice='" + copyrightNotice + '\'' +
                ", descriptions=" + descriptions +
                '}';
    }
}
