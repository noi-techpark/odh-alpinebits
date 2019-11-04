/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;

/**
 * This exception is thrown if there was an error during the conversion
 * of Handshaking echo data.
 */
public class HandshakingDataConversionException extends AlpineBitsException {

    public static final int CLIENT_ERROR = 400;
    public static final int SERVER_ERROR = 500;

    /**
     * Constructs a {@code SupportConversionException} with the specified
     * message, status and throwable.
     *
     * @param msg    The exception message.
     * @param status The exception status.
     * @param t      The parent throwable.
     */
    public HandshakingDataConversionException(String msg, int status, Throwable t) {
        super(msg, status, t);
    }
}