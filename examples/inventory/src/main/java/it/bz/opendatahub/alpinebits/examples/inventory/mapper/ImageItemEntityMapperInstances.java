/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.inventory.mapper;

import org.mapstruct.factory.Mappers;

/**
 * This class provides mapper instances for
 * ImageItem entities.
 */
public final class ImageItemEntityMapperInstances {

    public static final ImageItemEntityMapper IMAGE_ITEM_ENTITY_MAPPER
            = Mappers.getMapper(ImageItemEntityMapper.class);

    private ImageItemEntityMapperInstances() {
        // Empty
    }

}
