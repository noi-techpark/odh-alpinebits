/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.guestrequests.entity.confirmation;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * This entity is used to persist confirmation messages.
 */
@Entity
@Table(name = "confirmation")
public class ConfirmationEntity {

    @EmbeddedId
    private ConfirmationEntityId id;

    private Boolean acknowledged;

    private Integer refusalType;

    private Integer refusalCode;

    private String refusalContent;

    public ConfirmationEntityId getId() {
        return id;
    }

    public void setId(ConfirmationEntityId id) {
        this.id = id;
    }

    public Boolean getAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(Boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    public Integer getRefusalType() {
        return refusalType;
    }

    public void setRefusalType(Integer refusalType) {
        this.refusalType = refusalType;
    }

    public Integer getRefusalCode() {
        return refusalCode;
    }

    public void setRefusalCode(Integer refusalCode) {
        this.refusalCode = refusalCode;
    }

    public String getRefusalContent() {
        return refusalContent;
    }

    public void setRefusalContent(String refusalMessage) {
        this.refusalContent = refusalMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConfirmationEntity that = (ConfirmationEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ConfirmationEntity{" +
                "id=" + id +
                ", acknowledged=" + acknowledged +
                ", refusalType='" + refusalType + '\'' +
                ", refusalCode='" + refusalCode + '\'' +
                ", refusalMessage='" + refusalContent + '\'' +
                '}';
    }
}
