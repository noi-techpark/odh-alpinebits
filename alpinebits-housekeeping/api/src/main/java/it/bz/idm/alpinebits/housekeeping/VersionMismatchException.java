/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.housekeeping;

import it.bz.idm.alpinebits.common.exception.AlpineBitsException;

/**
 * This exception is thrown if some AlpineBits version is expected, but
 * the current context's version doesn't match with it.
 */
public class VersionMismatchException extends AlpineBitsException {

    public static final int STATUS = 500;

    /**
     * Constructs a {@code VersionMismatchException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public VersionMismatchException(String msg) {
        super(msg, STATUS);
    }

}
