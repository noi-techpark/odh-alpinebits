/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.guestrequests.resretrievers;

import it.bz.opendatahub.alpinebits.mapping.MappingException;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.PaymentCard;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.RatePlan;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.RoomStay;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.TimeSpan;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAResRetrieveRS;
import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Map AlpineBits room stay objects for OTA_ResRetrieveRS
 * requests to business objects and vice versa.
 */
@Mapper
public interface RoomStayMapper {

    @Mapping(target = "roomTypeCode", source = "roomTypes.roomType.roomTypeCode")
    @Mapping(target = "roomClassificationCode", source = "roomTypes.roomType.roomClassificationCode")
    @Mapping(target = "amountAfterTax", source = "total.amountAfterTax")
    @Mapping(target = "currencyCode", source = "total.currencyCode")
    @Mapping(target = "guestCounts", source = "guestCounts.guestCounts")
    @Mapping(target = "ratePlan", source = "ratePlans.ratePlan")
    @Mapping(target = "timeSpan", source = "timeSpan")
    @Mapping(target = "paymentCard", source = "guarantee.guaranteesAccepted.guaranteeAccepted.paymentCard")
    RoomStay toRoomStay(OTAResRetrieveRS.ReservationsList.HotelReservation.RoomStays.RoomStay roomStay);

    @Mapping(target = "mealPlanIndicator", source = "mealsIncluded.mealPlanIndicator")
    @Mapping(target = "mealPlanCodes", source = "mealsIncluded.mealPlanCodes")
    @Mapping(target = "commissionPercent", source = "commission.percent")
    @Mapping(target = "commissionAmount", source = "commission.commissionPayableAmount.amount")
    @Mapping(target = "commissionCurrencyCode", source = "commission.commissionPayableAmount.currencyCode")
    RatePlan toRatePlan(OTAResRetrieveRS.ReservationsList.HotelReservation.RoomStays
                                .RoomStay.RatePlans.RatePlan ratePlan);

    @Mapping(target = "earliestDate", source = "startDateWindow.earliestDate")
    @Mapping(target = "latestDate", source = "startDateWindow.latestDate")
    @Mapping(target = "duration", ignore = true)
    TimeSpan toTimeSpan(OTAResRetrieveRS.ReservationsList.HotelReservation.RoomStays
                                .RoomStay.TimeSpan timeSpan);

    @Mapping(target = "cardNumberPlain", source = "cardNumber.plainText")
    @Mapping(target = "encryptedValue", source = "cardNumber.encryptedValue")
    @Mapping(target = "encryptionMethod", source = "cardNumber.encryptionMethod")
    PaymentCard toPaymentCard(OTAResRetrieveRS.ReservationsList.HotelReservation.RoomStays
                                      .RoomStay.Guarantee.GuaranteesAccepted.GuaranteeAccepted.PaymentCard paymentCard);

    @AfterMapping
    default void updateTimeSpan(@MappingTarget TimeSpan timeSpan, OTAResRetrieveRS.ReservationsList.HotelReservation.RoomStays.RoomStay.TimeSpan ota) {
        if (ota.getDuration() != null) {
            if (ota.getDuration().length() < 3) {
                throw new MappingException("Could not parse duration " + ota.getDuration(), 400);
            }
            String duration = ota.getDuration();
            String days = duration.substring(1, duration.length() - 1);
            timeSpan.setDuration(Integer.parseInt(days));
        }
    }

    @InheritInverseConfiguration
    OTAResRetrieveRS.ReservationsList.HotelReservation.RoomStays.RoomStay toOTARoomStay(RoomStay roomStay);

    @InheritInverseConfiguration
    OTAResRetrieveRS.ReservationsList.HotelReservation.RoomStays
            .RoomStay.Guarantee.GuaranteesAccepted.GuaranteeAccepted.PaymentCard toOTAPaymentCard(PaymentCard paymentCard);

    @InheritInverseConfiguration
    OTAResRetrieveRS.ReservationsList.HotelReservation.RoomStays.RoomStay.TimeSpan toOTATimeSpan(TimeSpan timeSpan);

    @InheritInverseConfiguration
    OTAResRetrieveRS.ReservationsList.HotelReservation.RoomStays.RoomStay.RatePlans.RatePlan toOTARatePlan(RatePlan ratePlan);

    @AfterMapping
    default void updateOTACommission(@MappingTarget OTAResRetrieveRS.ReservationsList.HotelReservation
            .RoomStays.RoomStay.RatePlans.RatePlan ota, RatePlan ratePlan) {
        if (ratePlan.getCommissionPercent() == null
                && ratePlan.getCommissionAmount() == null
                && ratePlan.getCommissionCurrencyCode() == null) {
            ota.setCommission(null);
        }
        if (ratePlan.getCommissionPercent() != null) {
            ota.getCommission().setCommissionPayableAmount(null);
        }
    }

    @AfterMapping
    default void updateOTATimeSpan(@MappingTarget OTAResRetrieveRS.ReservationsList.HotelReservation.RoomStays.RoomStay.TimeSpan ota, TimeSpan timeSpan) {
        if (timeSpan.getStart() != null || timeSpan.getEnd() != null) {
            ota.setStartDateWindow(null);
        }
        if (timeSpan.getDuration() != null) {
            ota.setDuration("P" + timeSpan.getDuration() + "N");
        }
    }

    @AfterMapping
    default void updateOTARoomStayGuarantee(@MappingTarget OTAResRetrieveRS.ReservationsList.HotelReservation.RoomStays.RoomStay ota, RoomStay roomStay) {
        if (roomStay.getPaymentCard() == null) {
            ota.setGuarantee(null);
        }
    }

    @AfterMapping
    default void updateOTARoomStayRatePlan(@MappingTarget OTAResRetrieveRS.ReservationsList.HotelReservation.RoomStays.RoomStay ota, RoomStay roomStay) {
        if (roomStay.getRatePlan() == null) {
            ota.setRatePlans(null);
        }
    }

    @AfterMapping
    default void updateOTARoomStayTotal(@MappingTarget OTAResRetrieveRS.ReservationsList.HotelReservation.RoomStays.RoomStay ota, RoomStay roomStay) {
        if (roomStay.getAmountAfterTax() == null && roomStay.getCurrencyCode() == null) {
            ota.setTotal(null);
        }
    }
}
