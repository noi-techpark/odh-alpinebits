/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.idm.alpinebits.http;

import it.idm.alpinebits.common.AlpineBitsException;

/**
 * This exception is thrown when the Basic Authentication for a request was not successful.
 */
public class BasicAuthenticationException extends AlpineBitsException {

    public static final int STATUS = 401;

    /**
     * Constructs a {@code BasicAuthenticationException} with the specified message and root
     * cause.
     *
     * @param msg the detail message
     * @param t   the root cause
     */
    public BasicAuthenticationException(String msg, Throwable t) {
        super(msg, STATUS, t);
    }

    /**
     * Constructs a {@code BasicAuthenticationException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public BasicAuthenticationException(String msg) {
        super(msg, STATUS);
    }

}
