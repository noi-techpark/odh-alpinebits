/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.idm.alpinebits.common;

/**
 * Base class for all AlpineBits exceptions.
 */
public class AlpineBitsException extends RuntimeException {

    private final int code;

    /**
     * Constructs an {@code AlpineBitsException} with the specified message, code and root
     * cause.
     *
     * @param msg the detail message
     * @param code the error code
     * @param t the root cause
     */
    public AlpineBitsException(String msg, int code, Throwable t) {
        super(msg, t);
        this.code = code;
    }

    /**
     * Constructs an {@code AlpineBitsException} with the specified message, code and no
     * root cause.
     *
     * @param msg the detail message
     * @param code the error code
     */
    public AlpineBitsException(String msg, int code) {
        super(msg);
        this.code = code;
    }



    public int getCode() {
        return code;
    }
}
