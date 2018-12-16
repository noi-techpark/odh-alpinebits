/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.guestrequests.entity.read;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Provides room stay information for a {@link HotelReservationEntity}.
 */
@Entity
@Table(name = "room_stay")
public class RoomStayEntity implements Serializable {

    private static final long serialVersionUID = -2740206940816365392L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private HotelReservationEntity hotelReservation;

    private String roomTypeCode;

    private Integer roomClassificationCode;

    private Double amountAfterTax;

    private String currencyCode;

    @OneToMany(mappedBy = "roomStay", cascade = CascadeType.ALL)
    private List<GuestCountEntity> guestCounts;

    @OneToOne(cascade = CascadeType.ALL)
    private RatePlanEntity ratePlan;

    @OneToOne(mappedBy = "roomStay", cascade = CascadeType.ALL)
    private TimeSpanEntity timeSpan;

    @OneToOne(cascade = CascadeType.ALL)
    private PaymentCardEntity paymentCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HotelReservationEntity getHotelReservation() {
        return hotelReservation;
    }

    public void setHotelReservation(HotelReservationEntity hotelReservation) {
        this.hotelReservation = hotelReservation;
    }

    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    public Integer getRoomClassificationCode() {
        return roomClassificationCode;
    }

    public void setRoomClassificationCode(Integer roomClassificationCode) {
        this.roomClassificationCode = roomClassificationCode;
    }

    public Double getAmountAfterTax() {
        return amountAfterTax;
    }

    public void setAmountAfterTax(Double amountAfterTax) {
        this.amountAfterTax = amountAfterTax;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<GuestCountEntity> getGuestCounts() {
        return guestCounts;
    }

    public void setGuestCounts(List<GuestCountEntity> guestCounts) {
        this.guestCounts = guestCounts;
    }

    public RatePlanEntity getRatePlan() {
        return ratePlan;
    }

    public void setRatePlan(RatePlanEntity ratePlan) {
        this.ratePlan = ratePlan;
    }

    public TimeSpanEntity getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(TimeSpanEntity timeSpan) {
        this.timeSpan = timeSpan;
    }

    public PaymentCardEntity getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(PaymentCardEntity paymentCard) {
        this.paymentCard = paymentCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoomStayEntity roomStay = (RoomStayEntity) o;
        return Objects.equals(id, roomStay.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "RoomStayEntity{" +
                "id=" + id +
                ", hotelReservation=" + (hotelReservation != null ? hotelReservation.getId() : null) +
                ", roomTypeCode='" + roomTypeCode + '\'' +
                ", roomClassificationCode=" + roomClassificationCode +
                ", amountAfterTax=" + amountAfterTax +
                ", currencyCode='" + currencyCode + '\'' +
                ", guestCounts=" + guestCounts +
                ", ratePlan=" + ratePlan +
                ", timeSpan=" + timeSpan +
                ", paymentCard=" + paymentCard +
                '}';
    }
}
