/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.mapper.guestrequests.v_2017_10.readrq;

import it.bz.idm.alpinebits.mapping.entity.guestrequests.readrq.GuestRequestsReadRequest;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAReadRQ;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Map AlpineBits {@link OTAReadRQ} objects to
 * {@link GuestRequestsReadRequest} objects
 * and vice versa.
 */
@Mapper
public interface GuestRequestsReadRequestMapper {

    @Mapping(target = "hotelCode", source = "readRequests.hotelReadRequest.hotelCode")
    @Mapping(target = "hotelName", source = "readRequests.hotelReadRequest.hotelName")
    @Mapping(target = "start", source = "readRequests.hotelReadRequest.selectionCriteria.start")
    GuestRequestsReadRequest toRequestResult(OTAReadRQ otaReadRQ);

    @InheritInverseConfiguration
    @Mapping(target = "version", constant = "1.001")
    @Mapping(target = "timeStamp", ignore = true)
    OTAReadRQ toOTAReadRQ(GuestRequestsReadRequest guestRequestsReadRequest);

}
