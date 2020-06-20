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
 * Use this validator to validate the GuestRoom in AlpineBits 2017
 * Inventory documents.
 *
 * @see GuestRoom
 */
public class GuestRoomHotelInfoValidator implements Validator<GuestRoom, Void> {

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final MultimediaDescriptionsHotelInfoValidator multimediaDescriptionsHotelInfoValidator =
            new MultimediaDescriptionsHotelInfoValidator();

    @Override
    public void validate(GuestRoom guestRoom, Void ctx, ValidationPath path) {
        VALIDATOR.expectNotNull(guestRoom, ErrorMessage.EXPECT_GUEST_ROOM_TO_BE_NOT_NULL, path);

        // Note: this condition is also checked by XSD/RNG
        VALIDATOR.expectNotNull(guestRoom.getCode(), ErrorMessage.EXPECT_CODE_TO_BE_NOT_NULL, path.withAttribute(Names.CODE));

        // No attributes (except Code) are allowed
        // on Inventory/HotelInfo GuestRoom elements.
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

        // No sub-elements (except MultimediaDescriptions) are allowed
        // on Inventory/HotelInfo GuestRoom elements.
        VALIDATOR.expectNull(
                guestRoom.getAmenities(),
                ErrorMessage.EXPECT_AMENITIES_TO_BE_NULL,
                path.withElement(Names.AMENITIES)
        );
        VALIDATOR.expectNull(
                ListUtil.extractFirst(guestRoom.getTypeRooms()),
                ErrorMessage.EXPECT_TYPE_ROOM_TO_BE_NULL,
                path.withElement(Names.TYPE_ROOM)
        );

        this.multimediaDescriptionsHotelInfoValidator.validate(
                guestRoom.getMultimediaDescriptions(),
                null,
                path.withElement(MultimediaDescriptionsHotelInfoValidator.ELEMENT_NAME)
        );
    }

}
