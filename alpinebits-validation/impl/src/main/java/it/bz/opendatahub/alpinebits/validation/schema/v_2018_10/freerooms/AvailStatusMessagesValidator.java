// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.freerooms;

import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.AvailStatusMessagesContext;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRQ.AvailStatusMessages;

/**
 * Use this validator to validate the AvailStatusMessages in AlpineBits 2018
 * FreeRooms documents.
 *
 * @see AvailStatusMessages
 */
public class AvailStatusMessagesValidator implements Validator<AvailStatusMessages, AvailStatusMessagesContext> {

    public static final String ELEMENT_NAME = Names.AVAIL_STATUS_MESSAGES;

    private static final Validator<AvailStatusMessages, AvailStatusMessagesContext> VALIDATION_DELEGATE =
            new it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms.AvailStatusMessagesValidator();

    @Override
    public void validate(AvailStatusMessages availStatusMessages, AvailStatusMessagesContext ctx, ValidationPath path) {
        // Delegate validation to AlpineBits 2017 implementation,
        // since the validation remains the same
        VALIDATION_DELEGATE.validate(availStatusMessages, ctx, path);
    }

}
