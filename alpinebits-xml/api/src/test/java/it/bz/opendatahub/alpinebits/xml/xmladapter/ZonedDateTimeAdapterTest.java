/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.xml.xmladapter;

import org.testng.annotations.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Test cases for {@link ZonedDateTimeAdapter} class.
 */
public class ZonedDateTimeAdapterTest {

    @Test
    public void testUnmarshal() {
        ZonedDateTime dateTime = ZonedDateTime.now();
        ZonedDateTimeAdapter zonedDateTimeAdapter = new ZonedDateTimeAdapter();
        ZonedDateTime unmarshalledLocalDateTime = zonedDateTimeAdapter.unmarshal(dateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        assertEquals(unmarshalledLocalDateTime, dateTime);
    }

    @Test
    public void testMarshal() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        ZonedDateTimeAdapter zonedDateTimeAdapter = new ZonedDateTimeAdapter();
        String dateTime = zonedDateTimeAdapter.marshal(zonedDateTime);
        assertEquals(dateTime, zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @Test
    public void testMarshal_ZonedDateTimeIsNull() {
        ZonedDateTimeAdapter zonedDateTimeAdapter = new ZonedDateTimeAdapter();
        String dateTime = zonedDateTimeAdapter.marshal(null);
        assertNull(dateTime);
    }
}