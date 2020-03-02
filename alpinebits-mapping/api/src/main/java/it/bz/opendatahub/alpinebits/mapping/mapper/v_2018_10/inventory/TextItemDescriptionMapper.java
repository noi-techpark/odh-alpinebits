/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.inventory;

import it.bz.opendatahub.alpinebits.mapping.entity.inventory.TextItemDescription;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Convert between AlpineBits Description
 * (Inventory) and {@link TextItemDescription} objects.
 */
@Mapper
public interface TextItemDescriptionMapper {

    TextItemDescription toTextItemDescription(
            OTAHotelDescriptiveContentNotifRQ
                    .HotelDescriptiveContents
                    .HotelDescriptiveContent
                    .FacilityInfo
                    .GuestRooms
                    .GuestRoom
                    .MultimediaDescriptions
                    .MultimediaDescription
                    .TextItems
                    .TextItem
                    .Description description,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    @InheritInverseConfiguration
    OTAHotelDescriptiveContentNotifRQ
            .HotelDescriptiveContents
            .HotelDescriptiveContent
            .FacilityInfo
            .GuestRooms
            .GuestRoom
            .MultimediaDescriptions
            .MultimediaDescription
            .TextItems
            .TextItem
            .Description toOTATextItemDescription(
            TextItemDescription textItemDescription,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

}
