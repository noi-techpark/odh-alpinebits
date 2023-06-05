// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.utils.response;

import it.bz.opendatahub.alpinebits.common.constants.OTACodeErrorCodes;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Tests for {@link ErrorEntry}.
 */
public class ErrorEntryTest {

    private static final ErrorEntry ERROR_ENTRY_1 = new ErrorEntry("test message", OTACodeErrorCodes.INVALID_HOTEL);
    private static final ErrorEntry ERROR_ENTRY_2 = new ErrorEntry("test message", OTACodeErrorCodes.INVALID_HOTEL);

    @Test
    public void testEquals() {
        assertEquals(ERROR_ENTRY_1, ERROR_ENTRY_2);
    }

    @Test
    public void testHashCode() {
        assertEquals(ERROR_ENTRY_1.hashCode(), ERROR_ENTRY_2.hashCode());
    }
}