/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.freerooms;

import it.bz.opendatahub.alpinebits.mapping.entity.freerooms.AvailStatus;
import it.bz.opendatahub.alpinebits.mapping.entity.freerooms.FreeRoomsRequest;
import it.bz.opendatahub.alpinebits.mapping.utils.CollectionUtils;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRQ;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * The herein declared methods are invoked after
 * {@link FreeRoomsResponseMapper}
 * has finished to further customize the mapping.
 */
@Mapper
public abstract class AfterFreeRoomsRequestMapping {

    @AfterMapping
    public void updateOTAHotelDescriptiveContentNotifRS(
            @MappingTarget OTAHotelAvailNotifRQ ota,
            FreeRoomsRequest freeRoomsRequest
    ) {
        if (ota.getAvailStatusMessages() != null
            && CollectionUtils.isNullOrEmpty(ota.getAvailStatusMessages().getAvailStatusMessages())
        ) {
            ota.getAvailStatusMessages().getAvailStatusMessages().add(new OTAHotelAvailNotifRQ.AvailStatusMessages.AvailStatusMessage());
        }
    }

    @AfterMapping
    public void updateHotelDescriptiveContentNotifResponse(
            @MappingTarget FreeRoomsRequest freeRoomsRequest,
            OTAHotelAvailNotifRQ ota
    ) {
        if (freeRoomsRequest.getAvailStatuses() != null
                && freeRoomsRequest.getAvailStatuses().size() == 1
                && this.isEmptyAvailStatus(freeRoomsRequest.getAvailStatuses().get(0))
        ) {
            freeRoomsRequest.getAvailStatuses().clear();
        }
    }

    private boolean isEmptyAvailStatus(AvailStatus availStatus) {
        return availStatus.getBookingLimit() == null
                && availStatus.getBookingThreshold() == null
                && availStatus.getBookingLimitMessageType() == null
                && availStatus.getStart() == null
                && availStatus.getEnd() == null
                && availStatus.getInvTypeCode() == null
                && availStatus.getInvCode() == null;
    }
}
