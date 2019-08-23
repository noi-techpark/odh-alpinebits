/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.validation.context.inventory.InventoryContext;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom;

/**
 * Validate OTAHotelDescriptiveContentNotifRQ-&gt;HotelDescriptiveContents
 * -&gt;HotelDescriptiveContent-&gt;FacilityInfo-&gt;GuestRooms elements.
 */
public class GuestRoomsValidator implements Validator<GuestRooms, InventoryContext> {

    public static final String ELEMENT_NAME = Names.GUEST_ROOMS;

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final GuestRoomBasicHeadingValidator guestRoomBasicHeadingValidator = new GuestRoomBasicHeadingValidator();
    private final GuestRoomBasicFollowingValidator guestRoomBasicFollowingValidator = new GuestRoomBasicFollowingValidator();
    private final GuestRoomHotelInfoValidator guestRoomHotelInfoValidator = new GuestRoomHotelInfoValidator();

    @Override
    public void validate(GuestRooms guestRooms, InventoryContext ctx, ValidationPath path) {
        // It is valid for GuestRooms to be empty.
        if (guestRooms == null) {
            return;
        }

        VALIDATOR.expectNotNull(ctx, ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL);

        // Store the current heading guest room code in case of Inventory/Basic action
        String headingGuestRoomCode = null;

        // Validate all GuestRoom elements
        for (int i = 0; i < guestRooms.getGuestRooms().size(); i++) {
            GuestRoom guestRoom = guestRooms.getGuestRooms().get(i);

            ValidationPath indexedPath = path.withElement(Names.GUEST_ROOM).withIndex(i);

            // Code is mandatory for all guest rooms (heading and following)
            // Note: this condition is also checked by XSD/RNG
            VALIDATOR.expectNotNull(guestRoom.getCode(), ErrorMessage.EXPECT_CODE_TO_BE_NOT_NULL, indexedPath.withAttribute(Names.CODE));

            if (AlpineBitsAction.INVENTORY_BASIC_PUSH.equals(ctx.getAction())) {
                if (this.isHeadingGuestRoom(guestRoom, headingGuestRoomCode)) {
                    // A heading GuestRoom sets the code, that
                    // following GuestRoom elements must match
                    headingGuestRoomCode = guestRoom.getCode();

                    this.guestRoomBasicHeadingValidator.validate(guestRoom, ctx, indexedPath);
                } else {
                    this.guestRoomBasicFollowingValidator.validate(guestRoom, null, indexedPath);
                }
            } else if (AlpineBitsAction.INVENTORY_HOTEL_INFO_PUSH.equals(ctx.getAction())) {
                this.guestRoomHotelInfoValidator.validate(guestRoom, null, indexedPath);
            }
        }
    }

    private boolean isHeadingGuestRoom(GuestRoom guestRoom, String headingGuestRoomCode) {
        // Check if the current GuestRoom Code matches the heading GuestRoom code.
        // If it matches, the current GuestRoom is a following GuestRoom. If it
        // doesn't match, the current GuestRoom is a heading GuestRoom.
        return !guestRoom.getCode().equals(headingGuestRoomCode);
    }

}
