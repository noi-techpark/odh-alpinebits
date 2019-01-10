/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.freerooms.mapper;

import org.mapstruct.factory.Mappers;

/**
 * This class provides mapper instances for
 * FreeRooms entities.
 */
public final class FreeRoomsEntityMapperInstances {

    public static final FreeRoomsEntityMapper FREE_ROOMS_ENTITY_MAPPER
            = Mappers.getMapper(FreeRoomsEntityMapper.class);

    private FreeRoomsEntityMapperInstances() {
        // Empty
    }

}
