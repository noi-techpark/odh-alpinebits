/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.http;

import it.bz.idm.alpinebits.middleware.Key;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.function.Supplier;

/**
 * This class contains key definitions, that may be used e.g. in a middleware context.
 */
public final class HttpContextKey {

    /**
     * Context key for HTTP request.
     */
    public static final Key<HttpServletRequest> HTTP_REQUEST = Key.key(
            "http.request", HttpServletRequest.class
    );

    /**
     * Context key for HTTP response.
     */
    public static final Key<HttpServletResponse> HTTP_RESPONSE = Key.key(
            "http.response", HttpServletResponse.class
    );

    /**
     * Context key for HTTP request ID.
     */
    public static final Key<String> HTTP_REQUEST_ID = Key.key(
            "http.request.id", String.class
    );

    /**
     * Context key for HTTP Basic Authentication username.
     */
    public static final Key<String> HTTP_USERNAME = Key.key(
            "http.username", String.class
    );

    /**
     * Context key for HTTP Basic Authentication password. The {@link Supplier} type
     * of this key represents the intent to not store the password as String in the
     * context, since this could have security implications, e.g. if the context is
     * logged somewhere. Instead, the password is wrapped with a {@link Supplier}.
     */
    public static final Key<Supplier> HTTP_PASSWORD = Key.key(
            "http.password.supplier", Supplier.class
    );

    /**
     * Context key for AlpineBits action.
     */
    public static final Key<String> ALPINE_BITS_ACTION = Key.key(
            "alpinebits.action", String.class
    );

    /**
     * Context key for AlpineBits request content in its plain form, i.e. the XML.
     */
    public static final Key<OutputStream> ALPINE_BITS_REQUEST_CONTENT = Key.key(
            "alpinebits.requestcontent", OutputStream.class
    );

    /**
     * Context key for HTTP Basic Authentication password.
     */
    public static final Key<String> ALPINE_BITS_CLIENT_PROTOCOL_VERSION = Key.key(
            "alpinebits.client.protocol.version", String.class
    );

    private HttpContextKey() {
        // Empty
    }

}
