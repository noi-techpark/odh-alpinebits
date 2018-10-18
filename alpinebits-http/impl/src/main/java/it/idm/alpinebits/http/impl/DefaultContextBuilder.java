/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.idm.alpinebits.http.impl;

import it.idm.alpinebits.http.ContextBuilder;
import it.idm.alpinebits.http.HttpContextKey;
import it.idm.alpinebits.middleware.Context;
import it.idm.alpinebits.middleware.impl.SimpleContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is a default implementation for {@link ContextBuilder}.
 */
public class DefaultContextBuilder implements ContextBuilder {

    /**
     * Create a {@link Context}, using the given values.
     *
     * @param request   the {@link HttpServletRequest} is stored in the context using the key
     *                  {@link HttpContextKey#HTTP_REQUEST}
     * @param response  the {@link HttpServletResponse} is stored in the context using the key
     *                  {@link HttpContextKey#HTTP_RESPONSE}
     * @param requestId the requestId is stored in the context using the key {@link HttpContextKey#HTTP_REQUEST_ID}
     * @return Context
     */
    @Override
    public Context fromRequest(HttpServletRequest request, HttpServletResponse response, String requestId) {
        Context ctx = new SimpleContext();
        ctx.set(HttpContextKey.HTTP_REQUEST, request);
        ctx.set(HttpContextKey.HTTP_RESPONSE, response);
        ctx.set(HttpContextKey.HTTP_REQUEST_ID, requestId);
        return ctx;
    }

}
