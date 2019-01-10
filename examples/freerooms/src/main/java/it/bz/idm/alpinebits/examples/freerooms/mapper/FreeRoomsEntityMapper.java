/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.freerooms.mapper;

import it.bz.idm.alpinebits.examples.freerooms.entity.FreeRoomsEntity;
import it.bz.idm.alpinebits.mapping.entity.freerooms.FreeRoomsRequest;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Map {@link FreeRoomsRequest} to {@link FreeRoomsEntity}
 * and vice versa.
 */
@Mapper(uses = AvailStatusEntityMapper.class)
public interface FreeRoomsEntityMapper {

    @Mapping(target = "id", ignore = true)
    FreeRoomsEntity toFreeRoomsEntity(FreeRoomsRequest freeRoomsRequest);

    @InheritInverseConfiguration
    @Mapping(target = "uniqueId", ignore = true)
    FreeRoomsRequest toFreeRoomsRequest(FreeRoomsEntity freeRoomsEntity);
}
