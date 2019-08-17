/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.constants.OTACodeGuestRoomInfo;
import it.bz.opendatahub.alpinebits.validation.ContextWithAction;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.TextItems;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.TextItems.TextItem;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.MultimediaDescriptions.MultimediaDescription.TextItems.TextItem.Description;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.TypeRoom;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link GuestRoomsValidator}.
 */
public class GuestRoomsValidatorTest {

    private static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.GUEST_ROOMS);

    private static final String DEFAULT_GUEST_ROOM_CODE = "XYZ";

    @Test
    public void testValidate_ShouldAccept_WhenGuestRoomsIsNull() {
        GuestRoomsValidator validator = new GuestRoomsValidator();
        validator.validate(null, null, null);
    }

    @Test
    public void testValidate_ShouldThrow_WhenGuestRoomCodeIsNull() {
        GuestRoom guestRoom = new GuestRoom();

        GuestRooms guestRooms = new GuestRooms();
        guestRooms.getGuestRooms().add(guestRoom);

        this.validateAndAssert(
                guestRooms,
                this.buildInventoryBasicCtx(),
                NullValidationException.class,
                ErrorMessage.EXPECT_CODE_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenFollowingGuestRoomCodeNotEqualToHeadingGuestRoom() {
        GuestRoom headingGuestRoom = this.getValidHeadingGuestRoom(DEFAULT_GUEST_ROOM_CODE);

        GuestRoom followingGuestRoom = new GuestRoom();
        // A different GuestRoom Code means that the GuestRoom is
        // expected to be a heading GuestRoom. We intentionally
        // construct a new heading GuestRoom for testing purposes.
        followingGuestRoom.setCode(DEFAULT_GUEST_ROOM_CODE + "1");

        GuestRooms guestRooms = new GuestRooms();
        guestRooms.getGuestRooms().add(headingGuestRoom);
        guestRooms.getGuestRooms().add(followingGuestRoom);

        this.validateAndAssert(
                guestRooms,
                this.buildInventoryBasicCtx(),
                NullValidationException.class,
                ErrorMessage.EXPECT_MIN_OCCUPANCY_TO_BE_NOT_NULL
        );
    }

    private void validateAndAssert(
            GuestRooms data,
            ContextWithAction ctx,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    ) {
        GuestRoomsValidator validator = new GuestRoomsValidator();

        // CHECKSTYLE:OFF
        Exception e = expectThrows(
                exceptionClass,
                () -> validator.validate(data, ctx, VALIDATION_PATH)
        );
        // CHECKSTYLE:ON
        assertEquals(e.getMessage().substring(0, errorMessage.length()), errorMessage);
    }

    private GuestRoom getValidHeadingGuestRoom(String guestRoomCode) {
        Description description = new Description();
        description.setTextFormat("PlainText");
        description.setLanguage("en");

        TextItem textItem = new TextItem();
        textItem.getDescriptions().add(description);

        TextItems textItems = new TextItems();
        textItems.setTextItem(textItem);

        MultimediaDescription md = new MultimediaDescription();
        md.setInfoCode(BigInteger.valueOf(25));
        md.setTextItems(textItems);

        MultimediaDescriptions mm = new MultimediaDescriptions();
        mm.getMultimediaDescriptions().add(md);

        GuestRoom headingGuestRoom = new GuestRoom();
        headingGuestRoom.setCode(guestRoomCode);
        headingGuestRoom.setMinOccupancy(BigInteger.ONE);
        headingGuestRoom.setMaxOccupancy(BigInteger.TEN);
        headingGuestRoom.setMultimediaDescriptions(mm);

        TypeRoom typeRoom = new TypeRoom();
        typeRoom.setStandardOccupancy(BigInteger.ONE);
        typeRoom.setRoomClassificationCode(BigInteger.valueOf(OTACodeGuestRoomInfo.ACCESSIBLE_ROOMS.getCode()));
        headingGuestRoom.setTypeRoom(typeRoom);
        return headingGuestRoom;
    }

    private ContextWithAction buildInventoryBasicCtx() {
        return this.buildCtx(AlpineBitsAction.INVENTORY_BASIC_PUSH);
    }

    private ContextWithAction buildCtx(String action) {
        return new ContextWithAction(action);
    }

}