/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.http;

import it.bz.idm.alpinebits.common.AlpineBitsException;

/**
 * This exception is thrown if no action is present in a request.
 */
public class UndefinedActionException extends AlpineBitsException {

    public static final int STATUS = 400;

    /**
     * Constructs a {@code UndefinedActionException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public UndefinedActionException(String msg) {
        super(msg, STATUS, "unknown or missing action");
    }

}
