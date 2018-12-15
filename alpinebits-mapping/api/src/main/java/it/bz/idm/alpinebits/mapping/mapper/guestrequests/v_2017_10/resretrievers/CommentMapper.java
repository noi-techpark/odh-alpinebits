/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.mapper.guestrequests.v_2017_10.resretrievers;

import it.bz.idm.alpinebits.mapping.entity.guestrequests.resretrievers.Translation;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAResRetrieveRS;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Map AlpineBits comment objects for OTA_ResRetrieveRS
 * requests to business objects and vice versa.
 */
@Mapper
public interface CommentMapper {

    default String toCustomerComment(OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo.Comments comments) {
        if (comments == null) {
            return null;
        }
        Optional<OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo.Comments.Comment> customerComment = comments.getComments()
                .stream()
                .filter(comment -> "customer comment".equals(comment.getName()))
                .findAny();
        return customerComment
                .map(OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo.Comments.Comment::getText)
                .orElse(null);
    }

    default List<Translation> toIncludedServices(OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo.Comments comments) {
        if (comments == null) {
            return new ArrayList<>();
        }
        return comments.getComments()
                .stream()
                .filter(comment -> "included services".equals(comment.getName()))
                .map(comment -> comment.getListItems()
                        .stream()
                        .map(listItem -> {
                            Translation translation = new Translation();
                            translation.setLanguage(listItem.getLanguage());
                            translation.setValue(listItem.getValue());
                            return translation;
                        })
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
