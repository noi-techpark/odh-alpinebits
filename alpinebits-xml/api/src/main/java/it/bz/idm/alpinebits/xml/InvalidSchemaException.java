/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.xml;

import it.bz.idm.alpinebits.common.exception.AlpineBitsException;

/**
 * This exception is thrown if an XML validation schema (XSD/RNG)
 * could not be created.
 */
public class InvalidSchemaException extends AlpineBitsException {

    public static final int STATUS = 500;

    /**
     * Constructs a {@link InvalidSchemaException} with the specified message,
     * root cause and {@link InvalidSchemaException#STATUS}.
     *
     * @param msg the detail message
     * @param t the root cause
     */
    public InvalidSchemaException(String msg, Throwable t) {
        super(msg, InvalidSchemaException.STATUS, t);
    }

}
