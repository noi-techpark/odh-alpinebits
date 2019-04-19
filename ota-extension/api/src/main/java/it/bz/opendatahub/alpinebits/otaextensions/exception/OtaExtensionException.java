/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.otaextensions.exception;

/**
 * Base class for all OTA extension exceptions.
 */
public class OtaExtensionException extends RuntimeException {

    /**
     * Constructs a new OTA extension exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public OtaExtensionException() {
        super();
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public OtaExtensionException(String message) {
        super(message);
    }

    /**
     * Constructs a new OTA extension exception with the specified detail message and
     * cause.
     *
     * @param message the detail message
     * @param cause   the cause. A <tt>null</tt> value is permitted, and indicates that
     *                the cause is nonexistent or unknown
     */
    public OtaExtensionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new OTA extension exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause   the cause. A <tt>null</tt> value is permitted, and indicates that
     *                the cause is nonexistent or unknown
     */
    public OtaExtensionException(Throwable cause) {
        super(cause);
    }
}
