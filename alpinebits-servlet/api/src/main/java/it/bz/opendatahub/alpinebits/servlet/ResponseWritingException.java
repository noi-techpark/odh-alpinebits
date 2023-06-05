// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.servlet;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;

/**
 * This exception is thrown if the response writing to the threw an error.
 */
public class ResponseWritingException extends AlpineBitsException {

    private static final int STATUS = 500;

    /**
     * Constructs a {@code ResponseWritingException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public ResponseWritingException(String msg, Throwable t) {
        super(msg, STATUS, "Error while writing response", t);
    }

}