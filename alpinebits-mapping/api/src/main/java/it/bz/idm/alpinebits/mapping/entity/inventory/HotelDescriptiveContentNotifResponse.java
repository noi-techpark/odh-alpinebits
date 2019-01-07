/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.inventory;

import it.bz.idm.alpinebits.mapping.entity.GenericResponse;

/**
 * The HotelDescriptiveContentNotifRS for AlpineBits Inventory/Basic
 * (push) and Inventory/HotelInfo (push) server responses.
 */
public class HotelDescriptiveContentNotifResponse extends GenericResponse {

    @Override
    public String toString() {
        return "HotelDescriptiveContentNotifResponse{" +
                "success='" + getSuccess() + '\'' +
                ", errors=" + getErrors() +
                ", warnings=" + getWarnings() +
                '}';
    }
}
