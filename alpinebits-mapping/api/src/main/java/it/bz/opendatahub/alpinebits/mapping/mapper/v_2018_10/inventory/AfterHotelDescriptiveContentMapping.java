/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.inventory;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.inventory.contentnotifrs.HotelDescriptiveContentNotifResponseMapper;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.AffiliationInfoType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelDescriptiveContentType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelInfoType;
import it.bz.opendatahub.alpinebits.otaextensions.v_2018_10.inventory.AffiliationInfoConverter;
import it.bz.opendatahub.alpinebits.otaextensions.v_2018_10.inventory.HotelInfoConverter;
import it.bz.opendatahub.alpinebits.otaextensions.v_2018_10.inventory.PoliciesConverter;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * The herein declared methods are invoked after
 * {@link HotelDescriptiveContentNotifResponseMapper}
 * has finished to further customize the mapping.
 */
@Mapper
public abstract class AfterHotelDescriptiveContentMapping {

    private AffiliationInfoConverter affiliationInfoConverter = AffiliationInfoConverter.newInstance();
    private HotelInfoConverter hotelInfoConverter = HotelInfoConverter.newInstance();
    private PoliciesConverter policiesConverter = PoliciesConverter.newInstance();

    @AfterMapping
    public void updateHotelDescriptiveContent(
            @MappingTarget HotelDescriptiveContent hotelDescriptiveContent,
            OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent alpineBitsHotelDescriptiveContent,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    ) {
        AffiliationInfoType affiliationInfoType = this.affiliationInfoConverter.toAffiliationInfoType(alpineBitsHotelDescriptiveContent);
        hotelDescriptiveContent.setAffiliationInfo(affiliationInfoType);

        HotelInfoType hotelInfoType = this.hotelInfoConverter.toHotelInfoType(alpineBitsHotelDescriptiveContent);
        hotelDescriptiveContent.setHotelInfo(hotelInfoType);

        HotelDescriptiveContentType.Policies policies = this.policiesConverter.toPolicies(alpineBitsHotelDescriptiveContent);
        hotelDescriptiveContent.setPolicies(policies);
    }

    @AfterMapping
    public void updateOTAHotelDescriptiveContent(
            @MappingTarget OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent alpineBitsHotelDescriptiveContent,
            HotelDescriptiveContent hotelDescriptiveContent,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    ) {
        // For Inventory HotelInfo (action = "OTA_HotelDescriptiveInfo:Info)
        // remove info code from guest room multimedia descriptions
        if (this.isHotelInfoAction(ctx)) {
            this.removeInfoCodeFromGuestRoomMultimedia(alpineBitsHotelDescriptiveContent);
        }

        this.affiliationInfoConverter.applyAffiliationInfo(
                alpineBitsHotelDescriptiveContent,
                hotelDescriptiveContent.getAffiliationInfo()
        );

        this.hotelInfoConverter.applyHotelInfo(
                alpineBitsHotelDescriptiveContent,
                hotelDescriptiveContent.getHotelInfo()
        );

        this.policiesConverter.applyPolicies(
                alpineBitsHotelDescriptiveContent,
                hotelDescriptiveContent.getPolicies()
        );
    }

    private boolean isHotelInfoAction(it.bz.opendatahub.alpinebits.middleware.Context ctx) {
        if (ctx != null && ctx.get(RequestContextKey.REQUEST_ACTION).isPresent()) {
            String action = ctx.getOrThrow(RequestContextKey.REQUEST_ACTION);
            return AlpineBitsAction.INVENTORY_HOTEL_INFO_PULL.equals(action);
        }

        return false;
    }

    private void removeInfoCodeFromGuestRoomMultimedia(
            OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent alpineBitsHotelDescriptiveContent
    ) {
        if (this.hasGuestRooms(alpineBitsHotelDescriptiveContent)) {
            OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms guestRooms =
                    alpineBitsHotelDescriptiveContent.getFacilityInfo().getGuestRooms();

            // Remove InfoCode from Multimedia elements
            guestRooms.getGuestRooms().forEach(guestRoom -> {
                if (guestRoom.getMultimediaDescriptions() != null) {
                    guestRoom.getMultimediaDescriptions().getMultimediaDescriptions().forEach(mm -> mm.setInfoCode(null));
                }
            });
        }
    }

    private boolean hasGuestRooms(
            OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent alpineBitsHotelDescriptiveContent
    ) {
        return alpineBitsHotelDescriptiveContent != null
                && alpineBitsHotelDescriptiveContent.getFacilityInfo() != null
                && alpineBitsHotelDescriptiveContent.getFacilityInfo().getGuestRooms() != null;
    }
}
