/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2017_10.guestrequests.resretrievers;

import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.GlobalInfo;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.Translation;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAResRetrieveRS;
import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigInteger;

/**
 * Map AlpineBits global info objects for OTA_ResRetrieveRS
 * requests to business objects and vice versa.
 */
@Mapper(
        uses = {
                CommentMapper.class,
                CompanyMapper.class,
                HotelReservationIdMapper.class
        }
)
public interface GlobalInfoMapper {

    @Mapping(target = "penaltyDescription", source = "cancelPenalties.cancelPenalty.penaltyDescription.text")
    @Mapping(target = "customerComment", source = "comments")
    @Mapping(target = "hotelReservationIds", source = "hotelReservationIDs.hotelReservationIDs")
    @Mapping(target = "includedServices", source = "comments")
    @Mapping(target = "company", source = "resGlobalInfo.profiles.profileInfo.profile.companyInfo")
    GlobalInfo toGlobalInfo(OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo resGlobalInfo);

    @InheritInverseConfiguration
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "profiles.profileInfo.profile.profileType", constant = "4")
    @Mapping(target = "basicPropertyInfo", expression = "java(new String())")
    OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo toOTAGlobalInfo(GlobalInfo globalInfo);

    @AfterMapping
    default void updateOTAResGlobalInfoComments(@MappingTarget OTAResRetrieveRS.ReservationsList.HotelReservation
            .ResGlobalInfo resGlobalInfo, GlobalInfo globalInfo) {
        OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo.Comments comments =
                new OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo.Comments();

        if (globalInfo.getCustomerComment() != null) {
            OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo.Comments.Comment comment =
                    new OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo.Comments.Comment();
            comment.setText(globalInfo.getCustomerComment());
            comment.setName("customer comment");
            comments.getComments().add(comment);
        }

        if (globalInfo.getIncludedServices() != null && !globalInfo.getIncludedServices().isEmpty()) {
            OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo.Comments.Comment comment =
                    new OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo.Comments.Comment();
            comment.setName("included services");

            int i = 1;
            for (Translation translation : globalInfo.getIncludedServices()) {
                OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo.Comments.Comment.ListItem listItem =
                        new OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo.Comments.Comment.ListItem();
                listItem.setLanguage(translation.getLanguage());
                listItem.setValue(translation.getValue());
                listItem.setListItem(BigInteger.valueOf(i++));
                comment.getListItems().add(listItem);
            }

            comments.getComments().add(comment);
        }

        if (comments.getComments().isEmpty()) {
            resGlobalInfo.setComments(null);
        } else {
            resGlobalInfo.setComments(comments);
        }
    }

    @AfterMapping
    default void updateOTAResGlobalInfoPenalty(@MappingTarget OTAResRetrieveRS.ReservationsList.HotelReservation
            .ResGlobalInfo resGlobalInfo, GlobalInfo globalInfo) {
        if (globalInfo.getPenaltyDescription() == null) {
            resGlobalInfo.setCancelPenalties(null);
        }
    }

    @AfterMapping
    default void updateOTAResGlobalInfoProfile(@MappingTarget OTAResRetrieveRS.ReservationsList.HotelReservation
            .ResGlobalInfo resGlobalInfo, GlobalInfo globalInfo) {
        if (globalInfo.getCompany() == null) {
            resGlobalInfo.setProfiles(null);
        }
    }
}
