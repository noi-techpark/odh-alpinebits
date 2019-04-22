/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.servlet.middleware;

import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.middleware.RequiredContextKeyMissingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This middleware writes statistics about the
 * current request to the log.
 * <p>
 * The log is written in the upstreams phase,
 * i.e. this middleware first invokes the next
 * middleware in the chain. When that call returns,
 * it the log is written.
 * <p>
 * At the moment, the following statistics are
 * written for any request:
 * <ul>
 * <li>Username</li>
 * <li>AlpineBits version</li>
 * <li>AlpineBits action</li>
 * <li>Request duration</li>
 * </ul>
 */
public class StatisticsMiddleware implements Middleware {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsMiddleware.class);

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        long start = System.nanoTime();

        chain.next();

        try {
            String username = ctx.getOrThrow(RequestContextKey.REQUEST_USERNAME);
            String version = ctx.getOrThrow(RequestContextKey.REQUEST_VERSION);
            String action = ctx.getOrThrow(RequestContextKey.REQUEST_ACTION);
            long durationInNano = (System.nanoTime() - start) / 1000000;

            LOG.info("[username={}] - [version={}] - [action={}] - [duration={}ms]", username, version, action, durationInNano);
        } catch (RequiredContextKeyMissingException e) {
            LOG.error("Could not read context key: {}", e.getMessage());
        }
    }

}
