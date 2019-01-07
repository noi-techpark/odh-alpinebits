/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.inventory.mapper;

import org.mapstruct.factory.Mappers;

/**
 * This class provides mapper instances for
 * HotelDescriptiveContent entities.
 */
public final class HotelDescriptiveContentEntityMapperInstances {

    public static final HotelDescriptiveContentEntityMapper HOTEL_DESCRIPTIVE_CONTENT_MAPPER
            = Mappers.getMapper(HotelDescriptiveContentEntityMapper.class);

    private HotelDescriptiveContentEntityMapperInstances() {
        // Empty
    }

}
