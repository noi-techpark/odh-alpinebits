/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.servlet.impl;

import it.bz.idm.alpinebits.common.context.RequestContextKey;
import it.bz.idm.alpinebits.servlet.ContextBuilder;
import it.bz.idm.alpinebits.servlet.ServletContextKey;
import it.bz.idm.alpinebits.middleware.Context;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link DefaultContextBuilder} class.
 */
public class DefaultContextBuilderTest {

    @Test
    public void testFromRequest() {
        ContextBuilder builder = new DefaultContextBuilder();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String requestId = "REQUEST-ID";

        Context ctx = builder.fromRequest(request, response, requestId);

        HttpServletRequest requestValue = ctx.getOrThrow(ServletContextKey.SERVLET_REQUEST);
        assertEquals(requestValue, request);

        HttpServletResponse responseValue = ctx.getOrThrow(ServletContextKey.SERVLET_RESPONSE);
        assertEquals(responseValue, response);

        String requestIdValue = ctx.getOrThrow(RequestContextKey.REQUEST_ID);
        assertEquals(requestIdValue, requestId);
    }
}