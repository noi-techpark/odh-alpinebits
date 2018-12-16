/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.guestrequests.mapper;

import org.mapstruct.factory.Mappers;

/**
 * This class provides mapper instances for
 * Guest Request entities.
 */
public final class GuestRequestsEntityMapperInstances {

    public static final GuestRequestsEntityMapper HOTEL_RESERVATION_MAPPER = Mappers.getMapper(GuestRequestsEntityMapper.class);

    private GuestRequestsEntityMapperInstances() {
        // Empty
    }

}
