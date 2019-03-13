/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.freerooms.mapper;

import it.bz.opendatahub.alpinebits.examples.freerooms.entity.AvailStatusEntity;
import it.bz.opendatahub.alpinebits.mapping.entity.freerooms.AvailStatus;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Map {@link AvailStatus} to {@link AvailStatusEntity}
 * and vice versa.
 */
@Mapper
public interface AvailStatusEntityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "freeRoom", ignore = true)
    AvailStatusEntity toAvailStatusEntity(AvailStatus availStatus);

    @InheritInverseConfiguration
    AvailStatus toAvailStatus(AvailStatusEntity availStatusEntity);

}
