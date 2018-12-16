/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.dbserver.entity.guestrequests.read;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class contains various unique and non unique
 * identifiers that the trading partners associate with a given reservation.
 * <p>
 * Note, that this class does not represent any identifier for
 * {@link HotelReservationEntity}. Instead, it provides additional information
 * (e.g. Online campaign information) for a HotelReservationEntity.
 */
@Entity
@Table(name = "hotel_reservation_id")
public class HotelReservationIdEntity implements Serializable {

    private static final long serialVersionUID = 3022498989735537222L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private GlobalInfoEntity globalInfo;

    private int resIdType;

    private String resIdValue;

    private String resIdSource;

    private String resIdSourceContext;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GlobalInfoEntity getGlobalInfo() {
        return globalInfo;
    }

    public void setGlobalInfo(GlobalInfoEntity globalInfo) {
        this.globalInfo = globalInfo;
    }

    public int getResIdType() {
        return resIdType;
    }

    public void setResIdType(int resIdType) {
        this.resIdType = resIdType;
    }

    public String getResIdValue() {
        return resIdValue;
    }

    public void setResIdValue(String resIdValue) {
        this.resIdValue = resIdValue;
    }

    public String getResIdSource() {
        return resIdSource;
    }

    public void setResIdSource(String resIdSource) {
        this.resIdSource = resIdSource;
    }

    public String getResIdSourceContext() {
        return resIdSourceContext;
    }

    public void setResIdSourceContext(String resIdSourceContext) {
        this.resIdSourceContext = resIdSourceContext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HotelReservationIdEntity that = (HotelReservationIdEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "HotelReservationIdEntity{" +
                "id=" + id +
                ", globalInfo=" + this.globalInfoToString(globalInfo) +
                ", resIdType=" + resIdType +
                ", resIdValue='" + resIdValue + '\'' +
                ", resIdSource='" + resIdSource + '\'' +
                ", resIdSourceContext='" + resIdSourceContext + '\'' +
                '}';
    }

    private String globalInfoToString(GlobalInfoEntity globalInfo) {
        if (globalInfo == null) {
            return null;
        }
        if (globalInfo.getHotelReservation() == null) {
            return null;
        }
        return "" + globalInfo.getHotelReservation().getId();
    }
}

