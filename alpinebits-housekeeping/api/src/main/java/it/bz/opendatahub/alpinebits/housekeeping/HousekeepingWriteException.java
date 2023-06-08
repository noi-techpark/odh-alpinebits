// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.housekeeping;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;

/**
 * This exception is thrown if the Housekeeping response
 * could not be written to the OutputStream of the middleware
 * context.
 */
public class HousekeepingWriteException extends AlpineBitsException {

    private static final int STATUS = 500;

    /**
     * Constructs a {@code HousekeepingWriteException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public HousekeepingWriteException(String msg, Throwable t) {
        super(msg, STATUS, "Error while writing Housekeeping response", t);
    }

}