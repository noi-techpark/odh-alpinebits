// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.utils.ListUtil;
import it.bz.opendatahub.alpinebits.xml.schema.ota.FacilityInfoType.GuestRooms.GuestRoom;

/**
 * This class provides a {@link Validator} for Inventory/Basic
 * {@link GuestRoom}s that follow a heading GuestRoom
 * element in a OTA_HotelDescriptiveContentNotifRQ document.
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
public class GuestRoomBasicFollowingValidator implements Validator<GuestRoom, Void> {

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final TypeRoomValidator typeRoomValidator = new TypeRoomValidator();

    @Override
    public void validate(GuestRoom guestRoom, Void ctx, ValidationPath path) {
        VALIDATOR.expectNotNull(guestRoom, ErrorMessage.EXPECT_GUEST_ROOM_TO_BE_NOT_NULL, path);

        // Note: this condition is also checked by XSD/RNG
        VALIDATOR.expectNotNull(guestRoom.getCode(), ErrorMessage.EXPECT_CODE_TO_BE_NOT_NULL, path.withAttribute(Names.CODE));

        // No attributes (except Code) are allowed
        // on following GuestRoom elements.
        VALIDATOR.expectNull(
                guestRoom.getMinOccupancy(),
                ErrorMessage.EXPECT_MIN_OCCUPANCY_TO_BE_NULL,
                path.withAttribute(Names.MIN_OCCUPANCY)
        );
        VALIDATOR.expectNull(
                guestRoom.getMaxOccupancy(),
                ErrorMessage.EXPECT_MAX_OCCUPANCY_TO_BE_NULL,
                path.withAttribute(Names.MAX_OCCUPANCY)
        );
        VALIDATOR.expectNull(
                guestRoom.getMaxChildOccupancy(),
                ErrorMessage.EXPECT_MAX_CHILD_OCCUPANCY_TO_BE_NULL,
                path.withAttribute(Names.MAX_CHILD_OCCUPANCY)
        );
        VALIDATOR.expectNull(
                guestRoom.getID(),
                ErrorMessage.EXPECT_ID_TO_BE_NULL,
                path.withAttribute(Names.ID)
        );

        // No sub-elements (except TypeRoom) are allowed
        // on following GuestRoom elements.
        VALIDATOR.expectNull(
                guestRoom.getAmenities(),
                ErrorMessage.EXPECT_AMENITIES_TO_BE_NULL,
                path.withElement(Names.AMENITIES)
        );
        VALIDATOR.expectNull(
                guestRoom.getMultimediaDescriptions(),
                ErrorMessage.EXPECT_MULTIMEDIA_DESCRIPTIONS_TO_BE_NULL,
                path.withElement(Names.MULTIMEDIA_DESCRIPTIONS)
        );

        this.typeRoomValidator.validate(ListUtil.extractFirst(guestRoom.getTypeRooms()), false, path);
    }
}
