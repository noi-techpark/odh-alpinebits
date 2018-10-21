/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.common;

/**
 * Base class for all AlpineBits exceptions.
 * <p>
 * It provides constructors to define a code, that can be used e.g. as HTTP response status. In addition,
 * it is possible to define a response message that is returned to the client.
 */
public class AlpineBitsException extends RuntimeException {

    private final int code;
    private final String responseMessage;

    /**
     * Constructs an {@code AlpineBitsException} with the specified message, code and root
     * cause. The responseText is the same as the message.
     *
     * @param msg  the detail message
     * @param code the error code
     * @param t    the root cause
     */
    public AlpineBitsException(String msg, int code, Throwable t) {
        super(msg, t);
        this.code = code;
        this.responseMessage = msg;
    }

    /**
     * Constructs an {@code AlpineBitsException} with the specified message, responseText,
     * code and root cause.
     *
     * @param msg  the detail message
     * @param code the error code
     * @param responseMessage the response message that should be returned to the client
     * @param t    the root cause
     */
    public AlpineBitsException(String msg, int code, String responseMessage, Throwable t) {
        super(msg, t);
        this.code = code;
        this.responseMessage = responseMessage;
    }

    /**
     * Constructs an {@code AlpineBitsException} with the specified message, code and no
     * root cause. The responseText is the same as the message.
     *
     * @param msg  the detail message
     * @param code the error code
     */
    public AlpineBitsException(String msg, int code) {
        super(msg);
        this.code = code;
        this.responseMessage = msg;
    }

    /**
     * Constructs an {@code AlpineBitsException} with the specified message, responseText,
     * code and no root cause.
     *
     * @param msg  the detail message
     * @param code the error code
     * @param responseMessage the response message that should be returned to the client
     */
    public AlpineBitsException(String msg, int code, String responseMessage) {
        super(msg);
        this.code = code;
        this.responseMessage = responseMessage;
    }

    /**
     * Return the exception's response message, that may be different than the exception message.
     *
     * @return the exception's response message
     */
    public String getResponseMessage() {
        return this.responseMessage;
    }

    /**
     * Return the exception code.
     *
     * @return the exception code
     */
    public int getCode() {
        return code;
    }

}
