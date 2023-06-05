// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.freerooms;

import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.AvailStatusMessagesContext;
import it.bz.opendatahub.alpinebits.validation.schema.common.freerooms.AbstractAvailStatusMessagesValidatorTest;
import it.bz.opendatahub.alpinebits.xml.schema.ota.AvailStatusMessageType;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRQ.AvailStatusMessages;
import it.bz.opendatahub.alpinebits.xml.schema.ota.StatusApplicationControlType;

import java.math.BigInteger;
import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

/**
 * Tests for {@link AvailStatusMessagesValidator}.
 */
public class AvailStatusMessagesValidatorTest extends AbstractAvailStatusMessagesValidatorTest {

    protected void validateAndAssert(
            AvailStatusMessages data,
            AvailStatusMessagesContext ctx,
            Class<? extends ValidationException> exceptionClass,
            String errorMessage
    ) {
        AvailStatusMessagesValidator validator = new AvailStatusMessagesValidator();

        // CHECKSTYLE:OFF
        Exception e = expectThrows(
                exceptionClass,
                () -> validator.validate(data, ctx, VALIDATION_PATH)
        );
        // CHECKSTYLE:ON
        assertEquals(e.getMessage().substring(0, errorMessage.length()), errorMessage);
    }

    protected AvailStatusMessages buildValidAvailStatusMessages(AvailStatusMessageType... messages) {
        AvailStatusMessages availStatusMessages = new AvailStatusMessages();
        availStatusMessages.setHotelCode(DEFAULT_HOTEL_CODE);

        if (messages != null) {
            availStatusMessages.getAvailStatusMessages().addAll(Arrays.asList(messages));
        }

        return availStatusMessages;
    }

    protected AvailStatusMessageType buildValidRoomCategoryAvailStatusMessage() {
        StatusApplicationControlType statusApplicationControl = this.buildValidStatusApplicationControl();
        AvailStatusMessageType availStatusMessage = new AvailStatusMessageType();
        availStatusMessage.setStatusApplicationControl(statusApplicationControl);
        availStatusMessage.setBookingLimit(BigInteger.ONE);
        availStatusMessage.setBookingLimitMessageType(DEFAULT_BOOKING_LIMIT_MESSAGE_TYPE);
        return availStatusMessage;
    }

    private StatusApplicationControlType buildValidStatusApplicationControl() {
        StatusApplicationControlType statusApplicationControl = new StatusApplicationControlType();
        statusApplicationControl.setStart(STATUS_APPLICATION_CONTROL_START);
        statusApplicationControl.setEnd(STATUS_APPLICATION_CONTROL_END);
        statusApplicationControl.setInvTypeCode(DEFAULT_INV_TYPE_CODE);
        return statusApplicationControl;
    }
}