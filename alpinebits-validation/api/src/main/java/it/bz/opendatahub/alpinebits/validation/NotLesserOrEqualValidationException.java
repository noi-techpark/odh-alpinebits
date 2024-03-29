// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation;

/**
 * This exception is thrown if there was a validation error,
 * where the first of two values was expected to be lesser
 * or equal than the second one, but that is not the case.
 */
// Suppress warning that inheritance depth is to deep
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class NotLesserOrEqualValidationException extends ValidationException {

    /**
     * Constructs a {@link ValidationException} with the specified message,
     * and error code.
     *
     * @param msg The error message.
     * @param code The error code.
     */
    public NotLesserOrEqualValidationException(String msg, int code) {
        super(msg, code);
    }

}
