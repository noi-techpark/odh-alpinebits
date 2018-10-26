/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.servlet.impl;

import it.bz.idm.alpinebits.common.context.RequestContextKey;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.impl.SimpleContext;
import it.bz.idm.alpinebits.servlet.ContextBuilder;
import it.bz.idm.alpinebits.servlet.ServletContextKey;

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
     *                  the {@link ServletContextKey#SERVLET_REQUEST} key
     * @param response  the {@link HttpServletResponse} is stored in the context using
     *                  the {@link ServletContextKey#SERVLET_RESPONSE} key
     * @param requestId the requestId is stored in the context using
     *                  the {@link RequestContextKey#REQUEST_ID} key
     * @return Context
     */
    @Override
    public Context fromRequest(HttpServletRequest request, HttpServletResponse response, String requestId) {
        Context ctx = new SimpleContext();
        ctx.put(ServletContextKey.SERVLET_REQUEST, request);
        ctx.put(ServletContextKey.SERVLET_RESPONSE, response);
        ctx.put(RequestContextKey.REQUEST_ID, requestId);
        return ctx;
    }

}
