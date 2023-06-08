// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.context.freerooms;

/**
 * This class contains context information for InventoriesValidators.
 */
public final class InventoriesContext {

    private final String instance;
    private final HotelInvCountNotifContext hotelInvCountNotifContext;

    public InventoriesContext(String instance, HotelInvCountNotifContext hotelInvCountNotifContext) {
        this.instance = instance;

        // Assure that hotelInvCountNotifContext is never null
        this.hotelInvCountNotifContext = hotelInvCountNotifContext != null
                ? hotelInvCountNotifContext
                : new HotelInvCountNotifContext.Builder().build();
    }

    public String getInstance() {
        return instance;
    }

    public HotelInvCountNotifContext getHotelInvCountNotifContext() {
        return hotelInvCountNotifContext;
    }

    @Override
    public String toString() {
        return "InventoriesContext{" +
                "instance='" + instance + '\'' +
                ", hotelInvCountNotifContext=" + hotelInvCountNotifContext +
                '}';
    }
}
