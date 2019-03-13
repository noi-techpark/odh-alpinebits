/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.inventory.mapper;

import it.bz.opendatahub.alpinebits.examples.inventory.entity.ImageItemEntity;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.ImageItem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Map {@link ImageItem} to {@link ImageItemEntity}
 * and vice versa.
 */
@Mapper(uses = {TextItemDescriptionEntityMapper.class})
public interface ImageItemEntityMapper {

    @Mapping(target = "id", ignore = true)
    ImageItemEntity toImageItemEntity(ImageItem imageItem);

    @InheritInverseConfiguration
    ImageItem toImageItem(ImageItemEntity imageItemEntity);
}
