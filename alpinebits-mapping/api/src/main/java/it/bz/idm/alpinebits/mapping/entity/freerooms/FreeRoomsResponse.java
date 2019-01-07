/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.freerooms;

import it.bz.idm.alpinebits.mapping.entity.GenericResponse;

/**
 * The response for AlpineBits FreeRooms.
 */
public class FreeRoomsResponse extends GenericResponse {

    @Override
    public String toString() {
        return "FreeRoomsResponse{" +
                "success='" + getSuccess() + '\'' +
                ", errors=" + getErrors() +
                ", warnings=" + getWarnings() +
                '}';
    }
}
