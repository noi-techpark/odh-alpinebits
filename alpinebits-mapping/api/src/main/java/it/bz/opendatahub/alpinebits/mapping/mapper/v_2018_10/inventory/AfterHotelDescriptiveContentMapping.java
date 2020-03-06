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
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfoRootType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfosType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelDescriptiveContentType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelInfoType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.URLsType;
import it.bz.opendatahub.alpinebits.otaextensions.v_2018_10.inventory.AffiliationInfoConverter;
import it.bz.opendatahub.alpinebits.otaextensions.v_2018_10.inventory.HotelInfoConverter;
import it.bz.opendatahub.alpinebits.otaextensions.v_2018_10.inventory.PoliciesConverter;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos.ContactInfo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos.ContactInfo.URLs.URL;

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
            HotelDescriptiveContents.HotelDescriptiveContent alpineBitsHotelDescriptiveContent,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    ) {
        AffiliationInfoType affiliationInfoType = this.affiliationInfoConverter.toAffiliationInfoType(alpineBitsHotelDescriptiveContent);
        hotelDescriptiveContent.setAffiliationInfo(affiliationInfoType);

        HotelInfoType hotelInfoType = this.hotelInfoConverter.toHotelInfoType(alpineBitsHotelDescriptiveContent);
        hotelDescriptiveContent.setHotelInfo(hotelInfoType);

        HotelDescriptiveContentType.Policies policies = this.policiesConverter.toPolicies(alpineBitsHotelDescriptiveContent);
        hotelDescriptiveContent.setPolicies(policies);

        toMappedContactInfos(alpineBitsHotelDescriptiveContent).ifPresent(hotelDescriptiveContent::setContactInfos);
    }

    @AfterMapping
    public void updateOTAHotelDescriptiveContent(
            @MappingTarget HotelDescriptiveContents.HotelDescriptiveContent alpineBitsHotelDescriptiveContent,
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

        toAlpineBitsContactInfos(hotelDescriptiveContent.getContactInfos()).ifPresent(alpineBitsHotelDescriptiveContent::setContactInfos);
    }

    private boolean isHotelInfoAction(it.bz.opendatahub.alpinebits.middleware.Context ctx) {
        if (ctx != null && ctx.get(RequestContextKey.REQUEST_ACTION).isPresent()) {
            String action = ctx.getOrThrow(RequestContextKey.REQUEST_ACTION);
            return AlpineBitsAction.INVENTORY_HOTEL_INFO_PULL.equals(action);
        }

        return false;
    }

    private void removeInfoCodeFromGuestRoomMultimedia(
            HotelDescriptiveContents.HotelDescriptiveContent alpineBitsHotelDescriptiveContent
    ) {
        if (this.hasGuestRooms(alpineBitsHotelDescriptiveContent)) {
            HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms guestRooms =
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
            HotelDescriptiveContents.HotelDescriptiveContent alpineBitsHotelDescriptiveContent
    ) {
        return alpineBitsHotelDescriptiveContent != null
                && alpineBitsHotelDescriptiveContent.getFacilityInfo() != null
                && alpineBitsHotelDescriptiveContent.getFacilityInfo().getGuestRooms() != null;
    }

    private Optional<ContactInfos> toAlpineBitsContactInfos(ContactInfosType contactInfosType) {
        if (contactInfosType != null && !contactInfosType.getContactInfos().isEmpty()) {
            ContactInfoRootType contactInfoRootType = contactInfosType.getContactInfos().get(0);
            if (contactInfoRootType.getURLs() != null && !contactInfoRootType.getURLs().getURLS().isEmpty()) {
                List<URL> urls = contactInfoRootType.getURLs().getURLS().stream()
                        .map(url -> {
                            URL alpineBitsUrl = new URL();
                            alpineBitsUrl.setValue(url.getValue());
                            alpineBitsUrl.setID(url.getID());
                            return alpineBitsUrl;
                        })
                        .collect(Collectors.toList());

                ContactInfo.URLs contactInfoUrls = new ContactInfo.URLs();
                contactInfoUrls.getURLS().addAll(urls);

                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setURLs(contactInfoUrls);
                contactInfo.setLocation(contactInfoRootType.getLocation());

                ContactInfos contactInfos = new ContactInfos();
                contactInfos.setContactInfo(contactInfo);

                return Optional.of(contactInfos);
            }
        }

        return Optional.empty();
    }

    private Optional<ContactInfosType> toMappedContactInfos(HotelDescriptiveContents.HotelDescriptiveContent hotelDescriptiveContent) {
        if (hotelDescriptiveContent != null
                && hotelDescriptiveContent.getContactInfos() != null
                && hotelDescriptiveContent.getContactInfos().getContactInfo() != null
                && hotelDescriptiveContent.getContactInfos().getContactInfo().getURLs() != null
                && hotelDescriptiveContent.getContactInfos().getContactInfo().getURLs().getURLS() != null
        ) {
            List<URLsType.URL> urls = hotelDescriptiveContent.getContactInfos().getContactInfo().getURLs().getURLS().stream()
                    .map(alpineBitsUrl -> {
                        URLsType.URL url = new URLsType.URL();
                        url.setValue(alpineBitsUrl.getValue());
                        url.setID(alpineBitsUrl.getID());
                        return url;
                    })
                    .collect(Collectors.toList());

            URLsType urlsType = new URLsType();
            urlsType.getURLS().addAll(urls);

            ContactInfoRootType contactInfoRootType = new ContactInfoRootType();
            contactInfoRootType.setURLs(urlsType);
            contactInfoRootType.setLocation(hotelDescriptiveContent.getContactInfos().getContactInfo().getLocation());

            ContactInfosType contactInfosType = new ContactInfosType();
            contactInfosType.getContactInfos().add(contactInfoRootType);

            return Optional.of(contactInfosType);
        }

        return Optional.empty();
    }

}
