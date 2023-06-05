// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.context;

import it.bz.opendatahub.alpinebits.middleware.Context;

/**
 * This interfaces defines methods to produce contexts used during validation.
 *
 * @param <C> the validation context type
 */
public interface ValidationContextProvider<C> {

    /**
     * Build context of type <code>C</code>, using the
     * Middleware {@link Context}.
     *
     * @param ctx the middleware context used to build the validation context
     * @return the validation context
     */
    C buildContext(Context ctx);

}
