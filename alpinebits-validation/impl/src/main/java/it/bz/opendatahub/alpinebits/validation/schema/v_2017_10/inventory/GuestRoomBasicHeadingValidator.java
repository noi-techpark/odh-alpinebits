// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.context.inventory.InventoryContext;
import it.bz.opendatahub.alpinebits.validation.utils.ListUtil;
import it.bz.opendatahub.alpinebits.xml.schema.ota.FacilityInfoType.GuestRooms.GuestRoom;

import java.math.BigInteger;

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

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final TypeRoomValidator typeRoomValidator = new TypeRoomValidator();
    private final AmenitiesValidator amenitiesValidator = new AmenitiesValidator();
    private final MultimediaDescriptionsBasicValidator multimediaDescriptionsBasicValidator = new MultimediaDescriptionsBasicValidator();

    @Override
    public void validate(GuestRoom guestRoom, InventoryContext ctx, ValidationPath path) {
        VALIDATOR.expectNotNull(guestRoom, ErrorMessage.EXPECT_GUEST_ROOM_TO_BE_NOT_NULL, path);
        VALIDATOR.expectNotNull(ctx, ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL);

        // Note: this condition is also checked by XSD/RNG
        VALIDATOR.expectNotNull(guestRoom.getCode(), ErrorMessage.EXPECT_CODE_TO_BE_NOT_NULL, path.withElement(Names.CODE));

        this.validateOccupancy(guestRoom, path);

        this.typeRoomValidator.validate(ListUtil.extractFirst(guestRoom.getTypeRooms()), true, path.withElement(TypeRoomValidator.ELEMENT_NAME));
        this.amenitiesValidator.validate(guestRoom.getAmenities(), null, path.withElement(AmenitiesValidator.ELEMENT_NAME));

        if (AlpineBitsAction.INVENTORY_BASIC_PUSH.equals(ctx.getAction())) {
            this.multimediaDescriptionsBasicValidator.validate(
                    guestRoom.getMultimediaDescriptions(),
                    ctx,
                    path.withElement(MultimediaDescriptionsBasicValidator.ELEMENT_NAME)
            );
        }
    }


    private void validateOccupancy(GuestRoom guestRoom, ValidationPath path) {
        VALIDATOR.expectNotNull(
                guestRoom.getMinOccupancy(),
                ErrorMessage.EXPECT_MIN_OCCUPANCY_TO_BE_NOT_NULL, path.withAttribute(Names.MIN_OCCUPANCY)
        );
        VALIDATOR.expectNotNull(
                guestRoom.getMaxOccupancy(),
                ErrorMessage.EXPECT_MAX_OCCUPANCY_TO_BE_NOT_NULL,
                path.withAttribute(Names.MAX_OCCUPANCY)
        );

        // If MaxChildOccupancy is set, then the following must hold:
        // 0 <= MaxChildOccupancy <= MaxOccupancy (Note: "<=" means lesser/equal)
        if (guestRoom.getMaxChildOccupancy() != null) {
            BigInteger maxOccupancy = guestRoom.getMaxOccupancy();
            BigInteger maxChildOccupancy = guestRoom.getMaxChildOccupancy();

            VALIDATOR.expectArg0LesserOrEqualThanArg1(
                    maxChildOccupancy,
                    maxOccupancy,
                    ErrorMessage.EXPECT_MAX_CHILD_OCCUPANCY_TO_BE_LESSER_OR_EQUAL_THAN_MAX_OCCUPANCY,
                    path.withAttribute(String.format("%s/%s", Names.MAX_OCCUPANCY, Names.MAX_CHILD_OCCUPANCY))
            );

            // Note: this condition is also checked by XSD/RNG
            VALIDATOR.expectArg0LesserOrEqualThanArg1(
                    BigInteger.ZERO,
                    maxChildOccupancy,
                    ErrorMessage.EXPECT_ZERO_TO_BE_LESSER_OR_EQUAL_THAN_MAX_CHILD_OCCUPANCY,
                    path.withAttribute(Names.MAX_CHILD_OCCUPANCY)
            );
        }
    }

}
