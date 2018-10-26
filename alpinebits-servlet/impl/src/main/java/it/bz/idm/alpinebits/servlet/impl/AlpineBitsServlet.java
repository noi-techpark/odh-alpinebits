/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.servlet.impl;

import it.bz.idm.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.servlet.ContextBuilder;
import it.bz.idm.alpinebits.servlet.RequestExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

/**
 * This servlet is the entry point for an AlpineBits request.
 */
public class AlpineBitsServlet extends HttpServlet {

    public static final String MIDDLEWARE_CLASSNAME = "MIDDLEWARE_CLASSNAME";
    public static final String REQUEST_EXCEPTION_HANDLER_CLASSNAME = "REQUEST_EXCEPTION_HANDLER_CLASSNAME";
    public static final String CONTEXT_BUILDER_CLASSNAME = "CONTEXT_BUILDER_CLASSNAME";

    private static final Logger LOG = LoggerFactory.getLogger(AlpineBitsServlet.class);

    // Make the servlet variables static, since there exists only a single
    // instance of a servlet inside a container
    private static Middleware middleware;
    private static RequestExceptionHandler requestExceptionHandler;
    private static ContextBuilder contextBuilder;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AlpineBitsServlet.handleInit(config);
    }

    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.handleRequest(request, response);
        } catch (Exception e) {
            LOG.error("Uncaught error while handling request", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("checkstyle:illegalcatch")
    private static void handleInit(ServletConfig config) throws ServletException {
        ServletConfigParser servletConfigParser = new ServletConfigParser();

        try {
            Middleware configuredMiddleware = servletConfigParser.getMiddleware(config);

            // Wrap middleware with a composing middleware. The composed middleware can be called with null
            // as chain, which simplifies its invocation
            AlpineBitsServlet.middleware = ComposingMiddlewareBuilder.compose(Collections.singletonList(configuredMiddleware));
            AlpineBitsServlet.requestExceptionHandler = servletConfigParser.getRequestExceptionHandler(config);
            AlpineBitsServlet.contextBuilder = servletConfigParser.getContextBuilder(config);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @SuppressWarnings("checkstyle:illegalcatch")
    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String requestId = this.buildRequestId();
            MDC.put("requestId", requestId);

            LOG.debug("Handle incoming request");

            // Build context for middleware invocation
            Context ctx = AlpineBitsServlet.contextBuilder.fromRequest(request, response, requestId);

            // Invoke middleware. Since the middleware
            AlpineBitsServlet.middleware.handleContext(ctx, null);
        } catch (Exception e) {
            AlpineBitsServlet.requestExceptionHandler.handleRequestException(request, response, e);
        }
    }

    private String buildRequestId() {
        return UUID.randomUUID().toString();
    }
}
