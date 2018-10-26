/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.servlet.impl.utils;

import it.bz.idm.alpinebits.servlet.ContextBuilder;
import it.bz.idm.alpinebits.middleware.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A {@link ContextBuilder} that returns null, used for testing.
 */
public class NullContextBuilder implements ContextBuilder {

    @Override
    public Context fromRequest(HttpServletRequest request, HttpServletResponse response, String requestId) {
        // Return null, since this ContextBuilder is just used for testing
        return null;
    }

}
