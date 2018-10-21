/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.middleware.impl.utils;

import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.impl.SimpleContext;

/**
 * Builder for AlpineBits middleware {@link Context}.
 */
public class ContextBuilder {


    /**
     * Build and return a {@link SimpleContext}.
     *
     * @return a {@link SimpleContext}
     */
    public static SimpleContext buildSimpleContext() {
        return new SimpleContext();
    }

}
