/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.inventory.middleware;

import it.bz.idm.alpinebits.common.exception.AlpineBitsException;
import it.bz.idm.alpinebits.examples.inventory.entity.HotelDescriptiveContentEntity;
import it.bz.idm.alpinebits.examples.inventory.mapper.HotelDescriptiveContentEntityMapperInstances;
import it.bz.idm.alpinebits.mapping.entity.inventory.HotelDescriptiveContent;
import it.bz.idm.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoRequest;
import it.bz.idm.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * This service handles the DB persistence for
 * {@link InventoryPullMiddleware}.
 */
public class InventoryPullService {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryPullService.class);

    private final EntityManager em;

    public InventoryPullService(EntityManager em) {
        this.em = em;
    }

    public HotelDescriptiveInfoResponse readBasic(HotelDescriptiveInfoRequest hotelDescriptiveInfoRequest) {
        HotelDescriptiveInfoResponse response = new HotelDescriptiveInfoResponse();
        response.setSuccess("");

        String hotelCode = hotelDescriptiveInfoRequest.getHotelCode();
        String hotelName = hotelDescriptiveInfoRequest.getHotelName();
        HotelDescriptiveContentEntity hotelDescriptiveContentEntity
                = this.findHotelDescriptiveContentEntities(hotelCode, hotelName);

        if (hotelDescriptiveContentEntity != null) {
            HotelDescriptiveContent hotelDescriptiveContent = HotelDescriptiveContentEntityMapperInstances
                    .HOTEL_DESCRIPTIVE_CONTENT_MAPPER
                    .toHotelDescriptiveContent(hotelDescriptiveContentEntity);
            response.setHotelDescriptiveContent(hotelDescriptiveContent);
        } else {
            HotelDescriptiveContent hotelDescriptiveContent = new HotelDescriptiveContent();
            hotelDescriptiveContent.setHotelCode(hotelCode);
            hotelDescriptiveContent.setHotelName(hotelName);
            response.setHotelDescriptiveContent(hotelDescriptiveContent);
        }

        return response;
    }

    public HotelDescriptiveInfoResponse readHotelInfo(HotelDescriptiveInfoRequest hotelDescriptiveInfoRequest) {
        return this.readBasic(hotelDescriptiveInfoRequest);
    }

    private HotelDescriptiveContentEntity findHotelDescriptiveContentEntities(String hotelCode, String hotelName) {
        List<HotelDescriptiveContentEntity> entities = em.createQuery(
                "select h from HotelDescriptiveContentEntity h " +
                        "where h.hotelCode like :hotelCode " +
                        "and h.hotelName like :hotelName",
                HotelDescriptiveContentEntity.class
        )
                .setParameter("hotelCode", hotelCode)
                .setParameter("hotelName", hotelName)
                .getResultList();

        // If more than one entry was found, something is wrong
        if (entities.size() > 1) {
            throw new AlpineBitsException(
                    "More than one entries found for hotelCode " + hotelCode + " and hotelName " + hotelName,
                    500
            );
        }

        return entities.isEmpty() ? null : entities.get(0);
    }

}
