/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.idm.alpinebits.http;

import it.idm.alpinebits.common.AlpineBitsException;

/**
 * This exception is thrown if a request has not the expected content type.
 */
public class MultipartFormDataParseException extends AlpineBitsException {

    public static final int STATUS = 500;

    /**
     * Constructs a {@code MultipartFormDataParseException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public MultipartFormDataParseException(String msg) {
        super(msg, STATUS);
    }

    /**
     * Constructs a {@code MultipartFormDataParseException} with the specified message and root cause.
     *
     * @param msg the detail message
     * @param t the root cause
     */
    public MultipartFormDataParseException(String msg, Throwable t) {
        super(msg, STATUS, t);
    }

}
