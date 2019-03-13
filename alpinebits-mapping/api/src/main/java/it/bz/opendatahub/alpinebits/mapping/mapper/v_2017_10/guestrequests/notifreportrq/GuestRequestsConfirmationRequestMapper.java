/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.guestrequests.notifreportrq;

import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.notifreportrq.Acknowledge;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.notifreportrq.GuestRequestsConfirmationRequest;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.notifreportrq.Refusal;
import it.bz.opendatahub.alpinebits.mapping.utils.CollectionUtils;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTANotifReportRQ;
import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;

/**
 * Map AlpineBits {@link OTANotifReportRQ} objects to
 * {@link GuestRequestsConfirmationRequest} objects
 * and vice versa.
 */
@Mapper
public interface GuestRequestsConfirmationRequestMapper {

    @Mapping(target = "acknowledges", source = "notifDetails.hotelNotifReport.hotelReservations.hotelReservations")
    @Mapping(target = "refusals", source = "warnings.warnings")
    GuestRequestsConfirmationRequest toHotelReservationConfirmationRequest(OTANotifReportRQ otaNotifReportRQ);

    @Mapping(target = "recordId", source = "uniqueID.ID")
    @Mapping(target = "type", source = "uniqueID.type")
    Acknowledge toAcknowledge(OTANotifReportRQ.NotifDetails.HotelNotifReport.HotelReservations.HotelReservation hotelReservation);

    @Mapping(target = "recordId", source = "recordID")
    Refusal toRefusal(OTANotifReportRQ.Warnings.Warning warning);

    @AfterMapping
    default void checkAndSetLists(@MappingTarget GuestRequestsConfirmationRequest request, OTANotifReportRQ ota) {
        if (request.getAcknowledges() == null) {
            request.setAcknowledges(new ArrayList<>());
        }
        if (request.getRefusals() == null) {
            request.setRefusals(new ArrayList<>());
        }
    }

    @InheritInverseConfiguration
    @Mapping(target = "version", constant = "1.000")
    @Mapping(target = "timeStamp", ignore = true)
    OTANotifReportRQ toOTANotifReportRQ(GuestRequestsConfirmationRequest guestRequestsConfirmationRequest);

    @InheritInverseConfiguration
    OTANotifReportRQ.NotifDetails.HotelNotifReport.HotelReservations.HotelReservation toOTAAcknowledgeReservation(Acknowledge acknowledge);

    @InheritInverseConfiguration
    OTANotifReportRQ.Warnings.Warning toOTAWarning(Refusal refusal);

    @AfterMapping
    default void checkAndRemoveOTAParents(@MappingTarget OTANotifReportRQ ota, GuestRequestsConfirmationRequest request) {
        if (ota.getWarnings() == null || CollectionUtils.isNullOrEmpty(ota.getWarnings().getWarnings())) {
            ota.setWarnings(null);
        }
        if (ota.getNotifDetails() == null
                || ota.getNotifDetails().getHotelNotifReport() == null
                || ota.getNotifDetails().getHotelNotifReport().getHotelReservations() == null
                || CollectionUtils.isNullOrEmpty(ota.getNotifDetails().getHotelNotifReport().getHotelReservations().getHotelReservations())) {
            ota.setNotifDetails(null);
        }
    }
}
