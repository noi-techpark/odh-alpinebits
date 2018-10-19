/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.idm.alpinebits.http.impl;

import com.idm.alpinebits.http.HttpContextKey;
import com.idm.alpinebits.http.ContextBuilder;
import com.idm.alpinebits.middleware.Context;
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

        HttpServletRequest requestValue = ctx.getOrThrow(HttpContextKey.HTTP_REQUEST, HttpServletRequest.class);
        assertEquals(requestValue, request);

        HttpServletResponse responseValue = ctx.getOrThrow(HttpContextKey.HTTP_RESPONSE, HttpServletResponse.class);
        assertEquals(responseValue, response);

        String requestIdValue = ctx.getOrThrow(HttpContextKey.HTTP_REQUEST_ID, String.class);
        assertEquals(requestIdValue, requestId);
    }
}