/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.dbserver.entity.guestrequests.confirmation;

import it.bz.idm.alpinebits.examples.dbserver.entity.guestrequests.read.HotelReservationEntity;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 * Id for {@link ConfirmationEntity}.
 */
@Embeddable
public class ConfirmationEntityId implements Serializable {

    private static final long serialVersionUID = 4531949875718445095L;

    @OneToOne
    private HotelReservationEntity hotelReservation;

    private String client;

    public HotelReservationEntity getHotelReservation() {
        return hotelReservation;
    }

    public void setHotelReservation(HotelReservationEntity hotelReservation) {
        this.hotelReservation = hotelReservation;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConfirmationEntityId that = (ConfirmationEntityId) o;
        return Objects.equals(hotelReservation, that.hotelReservation) &&
                Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelReservation, client);
    }

    @Override
    public String toString() {
        return "ConfirmationEntityId{" +
                "hotelReservation=" + hotelReservation +
                ", client='" + client + '\'' +
                '}';
    }
}
