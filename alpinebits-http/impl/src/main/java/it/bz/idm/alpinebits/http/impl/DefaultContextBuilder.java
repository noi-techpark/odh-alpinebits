/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.http.impl;

import it.bz.idm.alpinebits.http.ContextBuilder;
import it.bz.idm.alpinebits.http.HttpContextKey;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.impl.SimpleContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is a default implementation for {@link ContextBuilder}.
 */
public class DefaultContextBuilder implements ContextBuilder {

    /**
     * Create a {@link Context} and store the given values inside of it.
     *
     * @param request   the {@link HttpServletRequest} is stored in the context using
     *                  the {@link HttpContextKey#HTTP_REQUEST} key
     * @param response  the {@link HttpServletResponse} is stored in the context using
     *                  the {@link HttpContextKey#HTTP_RESPONSE} key
     * @param requestId the requestId is stored in the context using
     *                  the {@link HttpContextKey#HTTP_REQUEST_ID} key
     * @return Context
     */
    @Override
    public Context fromRequest(HttpServletRequest request, HttpServletResponse response, String requestId) {
        Context ctx = new SimpleContext();
        ctx.put(HttpContextKey.HTTP_REQUEST, request);
        ctx.put(HttpContextKey.HTTP_RESPONSE, response);
        ctx.put(HttpContextKey.HTTP_REQUEST_ID, requestId);
        return ctx;
    }

}
