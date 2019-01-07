/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.inventory.mapper;

import it.bz.idm.alpinebits.examples.inventory.entity.TextItemDescriptionEntity;
import it.bz.idm.alpinebits.mapping.entity.inventory.TextItemDescription;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Map {@link TextItemDescription} to {@link TextItemDescriptionEntity}
 * and vice versa.
 */
@Mapper
public interface TextItemDescriptionEntityMapper {

    @Mapping(target = "id", ignore = true)
    TextItemDescriptionEntity toTextItemDescriptionEntity(TextItemDescription textItemDescription);

    @InheritInverseConfiguration
    TextItemDescription toTextItemDescription(TextItemDescriptionEntity textItemDescriptionEntity);
}
