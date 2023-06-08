// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.utils.middleware.utils;

import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.impl.SimpleContext;

/**
 * Builder for AlpineBits middleware {@link Context}.
 */
public final class ContextBuilder {

    private ContextBuilder() {
        // Empty
    }

    /**
     * Build and return a {@link SimpleContext}.
     *
     * @return a {@link SimpleContext}
     */
    public static Context buildSimpleContext() {
        return new SimpleContext();
    }

}
