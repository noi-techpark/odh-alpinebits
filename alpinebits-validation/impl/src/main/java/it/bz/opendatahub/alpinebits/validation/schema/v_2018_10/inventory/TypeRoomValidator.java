/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.inventory;

import it.bz.opendatahub.alpinebits.common.constants.OTACodeGuestRoomInfo;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.TypeRoom;

/**
 * Validate OTAHotelDescriptiveContentNotifRQ-&gt;HotelDescriptiveContents
 * -&gt;HotelDescriptiveContent-&gt;FacilityInfo-&gt;GuestRooms-&gt;GuestRoom-&gt;TypeRoom
 * elements.
 */
public class TypeRoomValidator implements Validator<TypeRoom, Boolean> {

    public static final String ELEMENT_NAME = Names.TYPE_ROOM;

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    @Override
    public void validate(TypeRoom typeRoom, Boolean isHeadingGuestRoom, ValidationPath path) {
        VALIDATOR.expectNotNull(typeRoom, ErrorMessage.EXPECT_TYPE_ROOM_TO_BE_NOT_NULL, path);
        VALIDATOR.expectNotNull(isHeadingGuestRoom, ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL);

        if (isHeadingGuestRoom) {
            VALIDATOR.expectNotNull(
                    typeRoom.getStandardOccupancy(),
                    ErrorMessage.EXPECT_STANDARD_OCCUPANCY_TO_BE_NOT_NULL,
                    path.withAttribute(Names.STANDARD_OCCUPANCY)
            );
            VALIDATOR.expectNotNull(
                    typeRoom.getRoomClassificationCode(),
                    ErrorMessage.EXPECT_ROOM_CLASSIFICATION_CODE_TO_BE_NOT_NULL,
                    path.withAttribute(Names.ROOM_CLASSIFICATION_CODE)
            );
            this.validateGuestRoomInfoCode(typeRoom.getRoomClassificationCode().intValue(), path);
        } else {
            VALIDATOR.expectNotNull(
                    typeRoom.getRoomID(),
                    ErrorMessage.EXPECT_ROOM_ID_TO_BE_NOT_NULL,
                    path.withAttribute(Names.ROOM_ID)
            );
            VALIDATOR.expectNull(
                    typeRoom.getRoomClassificationCode(),
                    ErrorMessage.EXPECT_ROOM_CLASSIFICATION_CODE_TO_BE_NULL,
                    path.withAttribute(Names.ROOM_CLASSIFICATION_CODE)
            );
            VALIDATOR.expectNull(
                    typeRoom.getSize(),
                    ErrorMessage.EXPECT_SIZE_TO_BE_NULL,
                    path.withAttribute(Names.SIZE)
            );
            VALIDATOR.expectNull(
                    typeRoom.getStandardOccupancy(),
                    ErrorMessage.EXPECT_STANDARD_OCCUPANCY_TO_BE_NULL,
                    path.withAttribute(Names.STANDARD_OCCUPANCY)
            );
        }
    }

    private void validateGuestRoomInfoCode(int code, ValidationPath path) {
        if (!OTACodeGuestRoomInfo.isCodeDefined(code)) {
            String message = String.format(ErrorMessage.EXPECT_INFO_CODE_TO_BE_DEFINED, code);
            VALIDATOR.throwValidationException(message, path.withAttribute(Names.ROOM_CLASSIFICATION_CODE));
        }
    }

}
