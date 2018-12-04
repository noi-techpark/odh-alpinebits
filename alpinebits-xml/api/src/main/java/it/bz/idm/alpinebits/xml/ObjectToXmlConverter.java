/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.xml;

import java.io.OutputStream;

/**
 * This interfaces defines methods for object-to-XML conversions.
 *
 * @param <T> the object type
 */
public interface ObjectToXmlConverter<T> {

    /**
     * Try to convert <code>objectToConvert</code> to an XML and write
     * that XML to the given {@link OutputStream}.
     *
     * @param objectToConvert this object will be converted to XML
     * @param os              an {@link OutputStream} where the converted XML
     *                        will be written to
     * @throws XmlConversionException if the conversion was not successful
     */

    void toXml(T objectToConvert, OutputStream os);

}
