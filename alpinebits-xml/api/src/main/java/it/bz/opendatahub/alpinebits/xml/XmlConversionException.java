/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.xml;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;

/**
 * This exception is thrown if there was an error during XML marshalling/unmarshalling.
 */
public class XmlConversionException extends AlpineBitsException {

    public static final int STATUS = 500;

    /**
     * Constructs a {@link XmlConversionException} with the specified message,
     * code and root cause. The response message
     * sent to the client is the same as <code>msg</code>.
     *
     * @param msg the detail message
     * @param t the root cause
     */
    public XmlConversionException(String msg, int code, Throwable t) {
        super(msg, code, msg, t);
    }

    /**
     * Constructs a {@link XmlConversionException} with the specified message,
     * root cause and {@link XmlConversionException#STATUS}. The response message
     * sent to the client is the same as <code>msg</code>.
     *
     * @param msg the detail message
     * @param t the root cause
     */
    public XmlConversionException(String msg, Throwable t) {
        super(msg, XmlConversionException.STATUS, msg, t);
    }

}
