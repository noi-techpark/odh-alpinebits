/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.servlet;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;

/**
 * This exception is thrown if a request has not the expected content type.
 */
public class InvalidRequestContentTypeException extends AlpineBitsException {

    public static final int STATUS = 415;

    /**
     * Constructs a {@code InvalidRequestContentTypeException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public InvalidRequestContentTypeException(String msg) {
        super(msg, STATUS);
    }

}
