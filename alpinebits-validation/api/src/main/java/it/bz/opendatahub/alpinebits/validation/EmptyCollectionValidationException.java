/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation;

/**
 * This exception is thrown if there was a validation error,
 * where a collection was expected to contain elements.
 */
// Suppress warning that inheritance depth is to deep
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class EmptyCollectionValidationException extends ValidationException {

    /**
     * Constructs a {@link ValidationException} with the specified message,
     * and error code.
     *
     * @param msg The error message.
     * @param code The error code.
     */
    public EmptyCollectionValidationException(String msg, int code) {
        super(msg, code);
    }

}
