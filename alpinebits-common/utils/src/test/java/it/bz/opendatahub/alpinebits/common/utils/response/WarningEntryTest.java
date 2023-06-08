// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.utils.response;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for {@link WarningEntry}.
 */
public class WarningEntryTest {

    private static final WarningEntry WARNING_ENTRY_1 = new WarningEntry("test message", WarningEntry.Type.UNKNOWN);
    private static final WarningEntry WARNING_ENTRY_2 = new WarningEntry("test message", WarningEntry.Type.UNKNOWN);

    @Test
    public void testEquals() {
        assertEquals(WARNING_ENTRY_1, WARNING_ENTRY_2);
    }

    @Test
    public void testHashCode() {
        assertEquals(WARNING_ENTRY_1.hashCode(), WARNING_ENTRY_2.hashCode());
    }
}