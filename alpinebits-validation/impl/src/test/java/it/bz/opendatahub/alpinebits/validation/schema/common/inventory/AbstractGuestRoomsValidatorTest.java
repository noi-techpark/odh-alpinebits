// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.common.inventory;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsAction;
import it.bz.opendatahub.alpinebits.common.constants.OTACodeGuestRoomInfo;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.context.inventory.InventoryContext;
import it.bz.opendatahub.alpinebits.xml.schema.ota.FacilityInfoType.GuestRooms;
import it.bz.opendatahub.alpinebits.xml.schema.ota.FacilityInfoType.GuestRooms.GuestRoom;
import it.bz.opendatahub.alpinebits.xml.schema.ota.FacilityInfoType.GuestRooms.GuestRoom.TypeRoom;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MultimediaDescriptionType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.MultimediaDescriptionsType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.TextDescriptionType.Description;
import it.bz.opendatahub.alpinebits.xml.schema.ota.TextItemsType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.TextItemsType.TextItem;
import org.testng.annotations.Test;

import java.math.BigInteger;

/**
 * Abstract tests for {@link GuestRooms} validator.
 */
public abstract class AbstractGuestRoomsValidatorTest {

    protected static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.GUEST_ROOMS);

    protected static final String DEFAULT_GUEST_ROOM_CODE = "XYZ";

    @Test
    public abstract void testValidate_ShouldAccept_WhenGuestRoomsIsNull();

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
        // constprivateruct a new heading GuestRoom for testing purposes.
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

    protected abstract void validateAndAssert(
            GuestRooms data,
            InventoryContext ctx,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    );

    private GuestRoom getValidHeadingGuestRoom(String guestRoomCode) {
        Description description = new Description();
        description.setTextFormat("PlainText");
        description.setLanguage("en");

        TextItem textItem = new TextItem();
        textItem.getDescriptions().add(description);

        TextItemsType textItems = new TextItemsType();
        textItems.getTextItems().add(textItem);

        MultimediaDescriptionType md = new MultimediaDescriptionType();
        md.setInfoCode("25");
        md.setTextItems(textItems);

        MultimediaDescriptionsType mm = new MultimediaDescriptionsType();
        mm.getMultimediaDescriptions().add(md);

        GuestRoom headingGuestRoom = new GuestRoom();
        headingGuestRoom.setCode(guestRoomCode);
        headingGuestRoom.setMinOccupancy(BigInteger.ONE);
        headingGuestRoom.setMaxOccupancy(BigInteger.TEN);
        headingGuestRoom.setMultimediaDescriptions(mm);

        TypeRoom typeRoom = new TypeRoom();
        typeRoom.setStandardOccupancy(BigInteger.ONE);
        typeRoom.setRoomClassificationCode(OTACodeGuestRoomInfo.ACCESSIBLE_ROOMS.getCode());
        headingGuestRoom.getTypeRooms().add(typeRoom);
        return headingGuestRoom;
    }

    private InventoryContext buildInventoryBasicCtx() {
        return this.buildCtx(AlpineBitsAction.INVENTORY_BASIC_PUSH);
    }

    private InventoryContext buildCtx(String action) {
        return new InventoryContext(action);
    }

}