/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.idm.alpinebits.http.middleware;


import com.idm.alpinebits.http.BasicAuthenticationException;
import com.idm.alpinebits.http.HttpContextKey;
import com.idm.alpinebits.middleware.Context;
import com.idm.alpinebits.middleware.Middleware;
import com.idm.alpinebits.middleware.RequiredContextKeyMissingException;
import com.idm.alpinebits.middleware.impl.SimpleContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.function.Supplier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link BasicAuthenticationMiddleware} class.
 */
public class BasicAuthenticationMiddlewareTest {

    @DataProvider(name = "badBasicAuthentication")
    public static Object[][] badBasicAuthentication() {
        return new Object[][]{
                {null},
                {""},
                {"no authorization header"},
                {"basic "},
                {"basic wrong format"},
        };
    }

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testHandleContext_RequestIsNull() {
        Middleware middleware = new BasicAuthenticationMiddleware();

        Context ctx = new SimpleContext();
        middleware.handleContext(ctx, null);
    }

    @Test(dataProvider = "badBasicAuthentication", expectedExceptions = BasicAuthenticationException.class)
    public void testHandleContext_BadAuthorization(String basicAuthentication) {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(BasicAuthenticationMiddleware.BASIC_AUTHENTICATION_HEADER))
                .thenReturn(basicAuthentication);

        Context ctx = new SimpleContext();
        ctx.put(HttpContextKey.HTTP_REQUEST, request);

        Middleware middleware = new BasicAuthenticationMiddleware();
        middleware.handleContext(ctx, null);
    }

    @Test
    public void testHandleContext_ValidAuthorization() {
        String username = "Aladdin";
        String password = "open sesame";
        String decodedBasicAuth = username + ":" + password;
        String encodedBasicAuth = Base64.getEncoder().encodeToString(decodedBasicAuth.getBytes());
        String authorizationString = "Basic " + encodedBasicAuth;

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(BasicAuthenticationMiddleware.BASIC_AUTHENTICATION_HEADER))
                .thenReturn(authorizationString);

        Context ctx = new SimpleContext();
        ctx.put(HttpContextKey.HTTP_REQUEST, request);

        Middleware middleware = new BasicAuthenticationMiddleware();
        middleware.handleContext(ctx, () -> {
        });

        String usernameValue = ctx.getOrThrow(HttpContextKey.HTTP_USERNAME);
        assertEquals(usernameValue, username);

        Supplier<String> passwordSupplier = ctx.getOrThrow(HttpContextKey.HTTP_PASSWORD);
        String passwordValue = passwordSupplier.get();
        assertEquals(passwordValue, password);
    }

}