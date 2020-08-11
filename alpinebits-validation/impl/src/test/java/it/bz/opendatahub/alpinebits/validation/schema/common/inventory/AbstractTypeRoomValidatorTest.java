/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.common.inventory;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.NotNullValidationException;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.xml.schema.ota.FacilityInfoType.GuestRooms.GuestRoom.TypeRoom;
import org.testng.annotations.Test;

import java.math.BigInteger;

/**
 * Abstract tests for {@link TypeRoom} validator.
 */
public abstract class AbstractTypeRoomValidatorTest {

    protected static final String DEFAULT_ROOM_ID = "XYZ";

    protected static final ValidationPath VALIDATION_PATH = SimpleValidationPath.fromPath(Names.TYPE_ROOM);

    @Test
    public void testValidate_ShouldThrow_WhenTypeRoomIsNull() {
        this.validateAndAssert(
                null,
                null,
                NullValidationException.class,
                ErrorMessage.EXPECT_TYPE_ROOM_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_ShouldThrow_WhenContextIsNull() {
        TypeRoom typeRoom = new TypeRoom();

        this.validateAndAssert(
                typeRoom,
                null,
                NullValidationException.class,
                ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_GivenHeadingGuestRoom_ShouldThrow_WhenStandardOccupancyIsNull() {
        TypeRoom typeRoom = new TypeRoom();

        this.validateAndAssert(
                typeRoom,
                true,
                NullValidationException.class,
                ErrorMessage.EXPECT_STANDARD_OCCUPANCY_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_GivenHeadingGuestRoom_ShouldThrow_WhenRoomClassificationCodeIsNull() {
        TypeRoom typeRoom = new TypeRoom();
        typeRoom.setStandardOccupancy(BigInteger.ONE);

        this.validateAndAssert(
                typeRoom,
                true,
                NullValidationException.class,
                ErrorMessage.EXPECT_ROOM_CLASSIFICATION_CODE_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_GivenHeadingGuestRoom_ShouldThrow_WhenRoomClassificationCodeIsLesserThanOne() {
        String code = "0";

        TypeRoom typeRoom = new TypeRoom();
        typeRoom.setStandardOccupancy(BigInteger.ONE);
        typeRoom.setRoomClassificationCode(code);

        String errorMessage = String.format(ErrorMessage.EXPECT_INFO_CODE_TO_BE_DEFINED, code);
        this.validateAndAssert(
                typeRoom,
                true,
                ValidationException.class,
                errorMessage
        );
    }

    @Test
    public void testValidate_GivenHeadingGuestRoom_ShouldThrow_WhenRoomClassificationCodeIsLesserGreaterThanGreatestGRI() {
        String code = "84";
        TypeRoom typeRoom = new TypeRoom();
        typeRoom.setStandardOccupancy(BigInteger.ONE);
        typeRoom.setRoomClassificationCode(code);

        String errorMessage = String.format(ErrorMessage.EXPECT_INFO_CODE_TO_BE_DEFINED, code);
        this.validateAndAssert(
                typeRoom,
                true,
                ValidationException.class,
                errorMessage
        );
    }

    @Test
    public void testValidate_GivenFollowingGuestRoom_ShouldThrow_WhenRoomIdIsNull() {
        TypeRoom typeRoom = new TypeRoom();

        this.validateAndAssert(
                typeRoom,
                false,
                NullValidationException.class,
                ErrorMessage.EXPECT_ROOM_ID_TO_BE_NOT_NULL
        );
    }

    @Test
    public void testValidate_GivenFollowingGuestRoom_ShouldThrow_WhenRoomClassificationCodeIsNotNull() {
        TypeRoom typeRoom = new TypeRoom();
        typeRoom.setRoomID(DEFAULT_ROOM_ID);
        typeRoom.setRoomClassificationCode("1");

        this.validateAndAssert(
                typeRoom,
                false,
                NotNullValidationException.class,
                ErrorMessage.EXPECT_ROOM_CLASSIFICATION_CODE_TO_BE_NULL
        );
    }

    @Test
    public void testValidate_GivenFollowingGuestRoom_ShouldThrow_WhenSizeIsNotNull() {
        TypeRoom typeRoom = new TypeRoom();
        typeRoom.setRoomID(DEFAULT_ROOM_ID);
        typeRoom.setSize(BigInteger.ONE);

        this.validateAndAssert(
                typeRoom,
                false,
                NotNullValidationException.class,
                ErrorMessage.EXPECT_SIZE_TO_BE_NULL
        );
    }

    @Test
    public void testValidate_GivenFollowingGuestRoom_ShouldThrow_WhenStandardOccupancyIsNotNull() {
        TypeRoom typeRoom = new TypeRoom();
        typeRoom.setRoomID(DEFAULT_ROOM_ID);
        typeRoom.setStandardOccupancy(BigInteger.ONE);

        this.validateAndAssert(
                typeRoom,
                false,
                NotNullValidationException.class,
                ErrorMessage.EXPECT_STANDARD_OCCUPANCY_TO_BE_NULL
        );
    }

    protected abstract void validateAndAssert(
            TypeRoom data,
            Boolean ctx,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    );
}