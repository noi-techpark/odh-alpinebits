/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.idm.alpinebits.http.impl.util;

import it.idm.alpinebits.http.HttpContextKey;
import it.idm.alpinebits.middleware.Context;
import it.idm.alpinebits.middleware.Middleware;
import it.idm.alpinebits.middleware.MiddlewareChain;

import javax.servlet.http.HttpServletResponse;

/**
 * A {@link Middleware} that sets a responses body, used for testing.
 */
public class ResponseStatusSettingMiddleware implements Middleware {

    public static final String EXCEPTION_MESSAGE = "exception message";

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        HttpServletResponse response = ctx.getOrThrow(HttpContextKey.HTTP_RESPONSE, HttpServletResponse.class);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
