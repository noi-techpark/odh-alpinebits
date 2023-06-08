// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.xml;

import java.io.OutputStream;

/**
 * This interfaces defines methods for object-to-XML conversions.
 */
public interface ObjectToXmlConverter {

    /**
     * Try to convert <code>objectToConvert</code> to an XML and write
     * that XML to the given {@link OutputStream}.
     *
     * @param objectToConvert this object will be converted to XML
     * @param os              an {@link OutputStream} where the converted XML
     *                        will be written to
     * @throws ExceptionInInitializerError if the underlying JAXBContext could not be created.
     * @throws XmlConversionException      if the conversion was not successful.
     */

    void toXml(Object objectToConvert, OutputStream os);

}
