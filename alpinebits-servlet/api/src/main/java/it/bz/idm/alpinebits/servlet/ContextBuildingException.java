/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.servlet;

import it.bz.idm.alpinebits.common.exception.AlpineBitsException;

/**
 * This exception is thrown if there was an error during context building.
 */
public class ContextBuildingException extends AlpineBitsException {

    private static final int STATUS = 500;

    /**
     * Constructs a {@code ContextBuildingException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public ContextBuildingException(String msg, Throwable t) {
        super(msg, STATUS, "Error while building context", t);
    }

}