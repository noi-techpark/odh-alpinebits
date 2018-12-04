/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.xml.xmladapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * XML adapter to convert xs:dateTime values to {@link ZonedDateTime}.
 */
public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {
    @Override
    public ZonedDateTime unmarshal(String v) {
        return ZonedDateTime.parse(v, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    @Override
    public String marshal(ZonedDateTime v) {
        return v != null ? v.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) : null;
    }
}
