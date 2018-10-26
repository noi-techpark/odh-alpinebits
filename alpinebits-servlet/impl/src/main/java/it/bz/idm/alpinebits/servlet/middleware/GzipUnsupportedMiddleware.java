/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.servlet.middleware;

import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;
import it.bz.idm.alpinebits.middleware.RequiredContextKeyMissingException;
import it.bz.idm.alpinebits.servlet.GzipUnsupportedException;
import it.bz.idm.alpinebits.servlet.ServletContextKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * This middleware checks if the HTTP requests <code>Content-Encoding</code> header is
 * set to <code>gzip</code>, in which case it throws a {@link GzipUnsupportedException}
 * with HTTP status code of 501, to indicate that the server doesn't support gzip
 * compressed requests.
 * <p>
 * The HTTP request must be present in the {@link Context}. Otherwise, a
 * {@link RequiredContextKeyMissingException} is thrown.
 */
public class GzipUnsupportedMiddleware implements Middleware {

    public static final String CONTENT_ENCODING_HEADER = "Content-Encoding";
    public static final String GZIP = "gzip";

    private static final Logger LOG = LoggerFactory.getLogger(GzipUnsupportedMiddleware.class);

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        HttpServletRequest request = ctx.getOrThrow(ServletContextKey.SERVLET_REQUEST);

        String contentType = request.getHeader(CONTENT_ENCODING_HEADER);
        if (GZIP.equalsIgnoreCase(contentType)) {
            throw new GzipUnsupportedException(
                    "GZIP compression unsupported (Content-Encoding header with value \"gzip\" found in request header)"
            );
        }

        LOG.debug("Content-Encoding header value: {}", contentType);

        chain.next();
    }

}
