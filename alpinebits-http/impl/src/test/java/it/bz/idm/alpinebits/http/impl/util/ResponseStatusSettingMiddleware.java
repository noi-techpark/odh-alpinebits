/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.http.impl.util;

import it.bz.idm.alpinebits.http.HttpContextKey;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;

import javax.servlet.http.HttpServletResponse;

/**
 * A {@link Middleware} that sets a responses body, used for testing.
 */
public class ResponseStatusSettingMiddleware implements Middleware {

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        HttpServletResponse response = ctx.getOrThrow(HttpContextKey.HTTP_RESPONSE);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
