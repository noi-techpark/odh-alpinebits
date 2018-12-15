/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers;

import java.util.List;

/**
 * Provides room stay information for a {@link HotelReservation}.
 */
public class RoomStay {

    private String roomTypeCode;

    private Integer roomClassificationCode;

    private Double amountAfterTax;

    private String currencyCode;

    private List<GuestCount> guestCounts;

    private RatePlan ratePlan;

    private TimeSpan timeSpan;

    private PaymentCard paymentCard;

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

    public List<GuestCount> getGuestCounts() {
        return guestCounts;
    }

    public void setGuestCounts(List<GuestCount> guestCounts) {
        this.guestCounts = guestCounts;
    }

    public RatePlan getRatePlan() {
        return ratePlan;
    }

    public void setRatePlan(RatePlan ratePlan) {
        this.ratePlan = ratePlan;
    }

    public TimeSpan getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(TimeSpan timeSpan) {
        this.timeSpan = timeSpan;
    }

    public PaymentCard getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(PaymentCard paymentCard) {
        this.paymentCard = paymentCard;
    }

    @Override
    public String toString() {
        return "RoomStay{" +
                "roomTypeCode='" + roomTypeCode + '\'' +
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
