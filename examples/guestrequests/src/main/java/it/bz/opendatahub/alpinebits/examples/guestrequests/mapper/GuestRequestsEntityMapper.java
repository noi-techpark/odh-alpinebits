/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.guestrequests.mapper;

import it.bz.opendatahub.alpinebits.examples.guestrequests.entity.read.CompanyEntity;
import it.bz.opendatahub.alpinebits.examples.guestrequests.entity.read.CustomerEntity;
import it.bz.opendatahub.alpinebits.examples.guestrequests.entity.read.CustomerTelephoneEntity;
import it.bz.opendatahub.alpinebits.examples.guestrequests.entity.read.GlobalInfoEntity;
import it.bz.opendatahub.alpinebits.examples.guestrequests.entity.read.GuestCountEntity;
import it.bz.opendatahub.alpinebits.examples.guestrequests.entity.read.HotelReservationEntity;
import it.bz.opendatahub.alpinebits.examples.guestrequests.entity.read.HotelReservationIdEntity;
import it.bz.opendatahub.alpinebits.examples.guestrequests.entity.read.RoomStayEntity;
import it.bz.opendatahub.alpinebits.examples.guestrequests.entity.read.TranslationEntity;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.Company;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.Customer;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.CustomerTelephone;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.Gender;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.GlobalInfo;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.GuestCount;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.HotelReservation;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.HotelReservationId;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.ReservationStatus;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.RoomStay;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.Translation;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Map {@link HotelReservation} to {@link HotelReservationEntity}
 * and vice versa.
 */
@Mapper
public interface GuestRequestsEntityMapper {

    @Mapping(target = "customer.email.customer", ignore = true)
    @Mapping(target = "customer.address.customer", ignore = true)
    @Mapping(target = "customer.id", ignore = true)
    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "globalInfo.id", ignore = true)
    @Mapping(target = "globalInfo.company.email.company", ignore = true)
    @Mapping(target = "globalInfo.company.address.company", ignore = true)
    @Mapping(target = "globalInfo.company.telephone.company", ignore = true)
    @Mapping(target = "globalInfo.company.id", ignore = true)
    @Mapping(target = "globalInfo.company", source = "globalInfo.company")
    @Mapping(target = "globalInfo.hotelReservation", ignore = true)
    HotelReservationEntity toHotelReservationEntity(HotelReservation hotelReservation);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "id", ignore = true)
    CustomerTelephoneEntity toCustomerTelephoneEntity(CustomerTelephone customerTelephone);

    @Mapping(target = "globalInfo", ignore = true)
    @Mapping(target = "id", ignore = true)
    HotelReservationIdEntity toHotelReservationIdEntity(HotelReservationId hotelReservationId);

    @Mapping(target = "id", ignore = true)
    TranslationEntity toTranslationEntity(Translation translation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotelReservation", ignore = true)
    @Mapping(target = "timeSpan.roomStay", ignore = true)
    @Mapping(target = "paymentCard.id", ignore = true)
    RoomStayEntity toRoomStayEntity(RoomStay roomStay);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roomStay", ignore = true)
    GuestCountEntity toGuestCountEntity(GuestCount guestCount);

    HotelReservation toHotelReservation(HotelReservationEntity hotelReservationEntity);

    default ReservationStatus stringToReservationStatus(String s) {
        return ReservationStatus.fromString(s);
    }

    default String reservationStatusToString(ReservationStatus reservationStatus) {
        return reservationStatus == null ? null : reservationStatus.toString();
    }

    default Gender stringToGender(String s) {
        return Gender.fromString(s);
    }

    default String genderToString(Gender gender) {
        return gender == null ? null : gender.toString();
    }

    @AfterMapping
    default void updateCompanyDependencies(@MappingTarget CompanyEntity company, Company pojo) {
        if (company.getAddress() != null) {
            company.getAddress().setCompany(company);
        }
        if (company.getEmail() != null) {
            company.getEmail().setCompany(company);
        }
        if (company.getTelephone() != null) {
            company.getTelephone().setCompany(company);
        }
    }

    @AfterMapping
    default void updateCustomerDependencies(@MappingTarget CustomerEntity customer, Customer pojo) {
        if (customer.getAddress() != null) {
            customer.getAddress().setCustomer(customer);
        }
        if (customer.getEmail() != null) {
            customer.getEmail().setCustomer(customer);
        }
        if (customer.getTelephones() != null) {
            customer.getTelephones().forEach(customerTelephone -> customerTelephone.setCustomer(customer));
        }
    }

    @AfterMapping
    default void updateGlobalInfoDependencies(@MappingTarget GlobalInfoEntity globalInfo, GlobalInfo pojo) {
        if (globalInfo.getHotelReservationIds() != null) {
            globalInfo.getHotelReservationIds().forEach(hotelReservationId -> hotelReservationId.setGlobalInfo(globalInfo));
        }
    }

    @AfterMapping
    default void updateHotelReservationDependencies(@MappingTarget HotelReservationEntity hotelReservation, HotelReservation pojo) {
        if (hotelReservation.getGlobalInfo() != null) {
            hotelReservation.getGlobalInfo().setHotelReservation(hotelReservation);
        }
        if (hotelReservation.getRoomStays() != null) {
            hotelReservation.getRoomStays().forEach(roomStay -> roomStay.setHotelReservation(hotelReservation));
        }
    }

    @AfterMapping
    default void updateRoomStayDependencies(@MappingTarget RoomStayEntity roomStay, RoomStay pojo) {
        if (roomStay.getTimeSpan() != null) {
            roomStay.getTimeSpan().setRoomStay(roomStay);
        }
        if (roomStay.getGuestCounts() != null) {
            roomStay.getGuestCounts().forEach(guestCount -> guestCount.setRoomStay(roomStay));
        }
    }
}
