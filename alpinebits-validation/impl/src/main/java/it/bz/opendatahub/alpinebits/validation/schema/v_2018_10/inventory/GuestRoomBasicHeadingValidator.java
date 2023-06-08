// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.inventory;

import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.context.inventory.InventoryContext;
import it.bz.opendatahub.alpinebits.xml.schema.ota.FacilityInfoType.GuestRooms.GuestRoom;

/**
 * This class provides a {@link Validator} for heading
 * Inventory/Basic {@link GuestRoom}s in a
 * OTA_HotelDescriptiveContentNotifRQ document.
 * <p>
 * There are two distinct kinds of GuestRoom elements:
 * <ul>
 *     <li>
 *         the heading one is used to define a room category
 *         and its basic description
 *     </li>
 *     <li>
 *         the following ones list specific rooms for each
 *         category
 *     </li>
 * </ul>
 * <p>
 */
public class GuestRoomBasicHeadingValidator implements Validator<GuestRoom, InventoryContext> {

    private static final Validator<GuestRoom, InventoryContext> VALIDATION_DELEGATE =
            new it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory.GuestRoomBasicHeadingValidator();

    @Override
    public void validate(GuestRoom guestRoom, InventoryContext ctx, ValidationPath path) {
        // Delegate validation to AlpineBits 2017 implementation,
        // since the validation remains the same
        VALIDATION_DELEGATE.validate(guestRoom, ctx, path);
    }

}
