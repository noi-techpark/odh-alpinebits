/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.servlet.middleware;

import it.bz.idm.alpinebits.common.constants.HousekeepingActionEnum;
import it.bz.idm.alpinebits.common.context.RequestContextKey;
import it.bz.idm.alpinebits.common.context.ResponseContextKeys;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;
import it.bz.idm.alpinebits.servlet.ResponseWritingException;
import it.bz.idm.alpinebits.servlet.ServletContextKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

/**
 * This middleware inspects the current AlpineBits <code>action</code> by
 * looking for the {@link RequestContextKey#REQUEST_ACTION} key in the
 * {@link Context}.
 * <p>
 * If the <code>action</code> is the <code>getVersion</code>
 * Housekeeping action, it extracts the result from the
 * {@link ResponseContextKeys#RESPONSE_VERSION} context key and writes
 * its value, prepended with "OK:" to the {@link HttpServletResponse},
 * e.g. "OK:2017-10".
 * <p>
 * If the <code>action</code> is the <code>getCapabilities</code>
 * Housekeeping action, it extracts the result from the
 * {@link ResponseContextKeys#RESPONSE_CAPABILITIES} context key and
 * writes its value, prepended by "OK:" to the
 * {@link HttpServletResponse}, e.g. "OK:getVersion,getCapabilities".
 * <p>
 * If the <code>action</code> is not a Housekeeping action, this
 * middleware does nothing.
 * <p>
 * This middleware first invokes the next middleware in the chain,
 * and executes its own behavior only after that middleware returns.
 */
public class HousekeepingWriterMiddleware implements Middleware {

    private static final Logger LOG = LoggerFactory.getLogger(HousekeepingWriterMiddleware.class);

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Call remaining middlewares first (downstream)
        chain.next();

        Optional<String> actionOptional = ctx.get(RequestContextKey.REQUEST_ACTION);

        if (actionOptional.isPresent()) {
            String action = actionOptional.get();

            if (this.isGetVersionAction(action)) {
                String version = ctx.getOrThrow(ResponseContextKeys.RESPONSE_VERSION);
                LOG.debug("AlpineBits getVersion housekeeping action found: {}", version);
                this.writeResponse(ctx, version);
            }
            if (this.isGetCapabilitiesAction(action)) {
                Collection<String> capabilities = ctx.getOrThrow(ResponseContextKeys.RESPONSE_CAPABILITIES);
                String joinedCapabilities = String.join(",", capabilities);
                LOG.debug("AlpineBits getCapabilitites housekeeping action found: {}", joinedCapabilities);
                this.writeResponse(ctx, joinedCapabilities);
            }
        }
    }

    private boolean isGetVersionAction(String action) {
        return HousekeepingActionEnum.GET_VERSION.getAction().equals(action);
    }

    private boolean isGetCapabilitiesAction(String action) {
        return HousekeepingActionEnum.GET_CAPABLILITIES.getAction().equals(action);
    }

    private void writeResponse(Context ctx, String message) {
        HttpServletResponse response = ctx.getOrThrow(ServletContextKey.SERVLET_RESPONSE);

        String responseMessage = "OK:" + message;
        try {
            LOG.debug("Writing AlpineBits Housekeeping action response: {}", responseMessage);
            response.getWriter().print(responseMessage);
        } catch (IOException e) {
            throw new ResponseWritingException(
                    "Error while writing Housekeeping action response. Response message" +
                            "would have been: \"" + responseMessage + "\"", e);
        }
    }
}
