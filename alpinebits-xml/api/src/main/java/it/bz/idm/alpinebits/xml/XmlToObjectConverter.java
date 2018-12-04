/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.xml;

import java.io.InputStream;

/**
 * This interfaces defines methods for XML-to-object conversions.
 *
 * @param <T> the object type
 */
public interface XmlToObjectConverter<T> {

    /**
     * Try to convert the content of the given {@link InputStream} to an
     * object of type <code>T</code>. The InputStream must provide a
     * valid XML document.
     *
     * @param is an {@link InputStream} providing a valid XML
     *           document, that should be converted to an instance
     *           of type <code>T</code>
     * @return an instance of type <code>T</code> as a result of
     * XML-to-object conversion
     * @throws XmlConversionException if the conversion was not successful
     */
    T toObject(InputStream is);

}
