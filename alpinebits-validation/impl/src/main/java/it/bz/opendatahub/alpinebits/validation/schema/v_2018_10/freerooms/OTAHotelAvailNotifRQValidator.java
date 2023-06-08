// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.freerooms;

import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.HotelAvailNotifContext;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRQ;

/**
 * Use this validator to validate the OTAHotelAvailNotifRQ in AlpineBits 2018
 * FreeRooms documents.
 *
 * @see OTAHotelAvailNotifRQ
 */
public class OTAHotelAvailNotifRQValidator implements Validator<OTAHotelAvailNotifRQ, HotelAvailNotifContext> {

    public static final String ELEMENT_NAME = Names.OTA_HOTEL_AVAIL_NOTIF_RQ;

    private static final Validator<OTAHotelAvailNotifRQ, HotelAvailNotifContext> VALIDATION_DELEGATE =
            new it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms.OTAHotelAvailNotifRQValidator();

    @Override
    public void validate(OTAHotelAvailNotifRQ hotelAvailNotifRQ, HotelAvailNotifContext ctx, ValidationPath unused) {
        // Delegate validation to AlpineBits 2017 implementation,
        // since the validation remains the same
        VALIDATION_DELEGATE.validate(hotelAvailNotifRQ, ctx, unused);
    }

}
