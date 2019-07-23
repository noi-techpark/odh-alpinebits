/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;

/**
 * This exception is thrown if the mapping between an OTA object
 * and the corresponding mapped object was not possible.
 */
public class MappingException extends AlpineBitsException {

    /**
     * Constructs a {@code VersionMismatchException} with the specified message and status.
     *
     * @param msg the detail message
     * @param status the status
     */
    public MappingException(String msg, int status) {
        super(msg, status);
    }

}
