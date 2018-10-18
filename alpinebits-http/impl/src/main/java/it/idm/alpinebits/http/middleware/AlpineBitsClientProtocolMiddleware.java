/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.idm.alpinebits.http.middleware;

import it.idm.alpinebits.http.AlpineBitsClientProtocolMissingException;
import it.idm.alpinebits.http.HttpContextKey;
import it.idm.alpinebits.middleware.Context;
import it.idm.alpinebits.middleware.Middleware;
import it.idm.alpinebits.middleware.MiddlewareChain;
import it.idm.alpinebits.middleware.RequiredContextKeyMissingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * This middleware extracts the value of the <code>X-AlpineBits-ClientProtocolVersion</code> header
 * from the HTTP request and adds it to the {@link Context} using the key
 * {@link AlpineBitsClientProtocolMiddleware#AB_CLIENT_PROTOCOL_VERSION}.
 * <p>
 * The HTTP request must be present in the {@link Context}, indexed by {@link HttpContextKey#HTTP_REQUEST}.
 * Otherwise, a {@link RequiredContextKeyMissingException} is thrown.
 * <p>
 * If the <code>X-AlpineBits-ClientProtocolVersion</code> header is not present, an
 * {@link AlpineBitsClientProtocolMissingException} is thrown.
 */
public class AlpineBitsClientProtocolMiddleware implements Middleware {

    public static final String CLIENT_PROTOCOL_VERSION_HEADER = "X-AlpineBits-ClientProtocolVersion";
    public static final String AB_CLIENT_PROTOCOL_VERSION = "ab.client.protocol.version";

    private static final Logger LOG = LoggerFactory.getLogger(AlpineBitsClientProtocolMiddleware.class);

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        HttpServletRequest request = ctx.getOrThrow(HttpContextKey.HTTP_REQUEST, HttpServletRequest.class);

        String clientProtocolVersion = request.getHeader(CLIENT_PROTOCOL_VERSION_HEADER);
        if (clientProtocolVersion == null) {
            throw new AlpineBitsClientProtocolMissingException("No X-AlpineBits-ClientProtocolVersion header found");
        }

        LOG.debug("X-AlpineBits-ClientProtocolVersion header found: {}", clientProtocolVersion);

        ctx.set(AB_CLIENT_PROTOCOL_VERSION, clientProtocolVersion);

        chain.next();
    }

}
