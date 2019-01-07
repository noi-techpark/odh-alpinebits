/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Test cases for {@link Warning} class.
 */
public class WarningTest {

    @Test
    public void testWithoutRecordId() {
        Integer type = 1;
        String content = "some text";

        Warning warning = Warning.withoutRecordId(type, content);

        assertEquals(warning.getType(), type);
        assertEquals(warning.getContent(), content);
        assertNull(warning.getRecordId());
    }
}