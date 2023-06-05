// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation;

/**
 * This exception is thrown if there was a validation error,
 * where two values were expected to be equal, but they were not.
 */
// Suppress warning that inheritance depth is to deep
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class NotEqualValidationException extends ValidationException {

    /**
     * Constructs a {@link ValidationException} with the specified message,
     * and error code.
     *
     * @param msg The error message.
     * @param code The error code.
     */
    public NotEqualValidationException(String msg, int code) {
        super(msg, code);
    }

}
