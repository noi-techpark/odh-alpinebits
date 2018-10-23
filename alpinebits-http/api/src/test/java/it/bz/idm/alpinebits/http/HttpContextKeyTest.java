/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.http;

import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.util.function.Supplier;

import static org.testng.Assert.*;

/**
 * Test cases for {@link HttpContextKey} class.
 */
public class HttpContextKeyTest {

    @Test
    public void testContextKey_HttpRequest() {
        assertEquals(HttpContextKey.HTTP_REQUEST.getType(), HttpServletRequest.class);
    }

    @Test
    public void testContextKey_HttpResponse() {
        assertEquals(HttpContextKey.HTTP_RESPONSE.getType(), HttpServletResponse.class);
    }

    @Test
    public void testContextKey_HttpRequestId() {
        assertEquals(HttpContextKey.HTTP_REQUEST_ID.getType(), String.class);
    }

    @Test
    public void testContextKey_HttpUsername() {
        assertEquals(HttpContextKey.HTTP_USERNAME.getType(), String.class);
    }

    @Test
    public void testContextKey_HttpPassword() {
        assertEquals(HttpContextKey.HTTP_PASSWORD.getType(), Supplier.class);
    }

    @Test
    public void testContextKey_AlpineBitsAction() {
        assertEquals(HttpContextKey.ALPINE_BITS_ACTION.getType(), String.class);
    }

    @Test
    public void testContextKey_AlpineBitsRequestContent() {
        assertEquals(HttpContextKey.ALPINE_BITS_REQUEST_CONTENT.getType(), OutputStream.class);
    }

    @Test
    public void testContextKey_AlpineBitsClientProtocolVersion() {
        assertEquals(HttpContextKey.ALPINE_BITS_CLIENT_PROTOCOL_VERSION.getType(), String.class);
    }

}