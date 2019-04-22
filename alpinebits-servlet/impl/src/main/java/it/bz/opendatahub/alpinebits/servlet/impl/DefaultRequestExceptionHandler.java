/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.servlet.impl;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.middleware.RequiredContextKeyMissingException;
import it.bz.opendatahub.alpinebits.servlet.RequestExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This default {@link RequestExceptionHandler} implementation logs the given exception and
 * sets the response body to "ERROR: " + {@link AlpineBitsException#getResponseMessage()} if
 * it is a {@link AlpineBitsException} and {@link Exception#getMessage()} otherwise. The
 * status depends on the exception type:
 * <ul>
 * <li>
 * for an {@link AlpineBitsException}, the value provided by {@link AlpineBitsException#getCode()}
 * is returned as HTTP status. If the status is not in the range of valid HTTP statuses (i.e.
 * status < 100 || status > 599), a HTTP status of 500 is returned to align to common HTTP statuses.
 * </li>
 * <li>
 * for all other exception types, a 500 HTTP status is returned
 * </li>
 * </ul>
 * <p>
 */
public class DefaultRequestExceptionHandler implements RequestExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultRequestExceptionHandler.class);

    @Override
    public void handleRequestException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        String requestId = (String) request.getAttribute(AlpineBitsServlet.REQUEST_ID);

        if (e instanceof AlpineBitsException) {
            LOG.error("Handling uncaught AlpineBitsException", e);

            int status = this.getStatus((AlpineBitsException) e);
            this.sendError(response, status, e, requestId);
        } else if (e instanceof RequiredContextKeyMissingException) {
            LOG.error("Context key missing", e);
            this.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, requestId);
        } else {
            LOG.error("Handling uncaught Exception", e);
            this.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, requestId);
        }
    }

    public String getErrorMessage(Exception e, String requestId) {
        StringBuilder sb = new StringBuilder("ERROR:");

        if (e instanceof AlpineBitsException) {
            sb.append(((AlpineBitsException) e).getResponseMessage());
        } else {
            sb.append(e.getMessage());
        }

        sb.append(" [rid=").append(requestId).append("]");

        return sb.toString();
    }

    private int getStatus(AlpineBitsException e) {
        int status = e.getCode();
        return status < 100 || status > 599 ? HttpServletResponse.SC_INTERNAL_SERVER_ERROR : status;
    }

    /**
     * Set the response error by setting status explicitly and writing the error message using the writer.
     * <p>
     * This is necessary, since otherwise application servers (e.g. Tomcat) may wrap the error inside
     * their (HTML) error page
     *
     * @param response set the status and message for this {@link HttpServletResponse}
     * @param status   the status to set
     * @param e        the exception, containing the relevant message
     * @throws IOException if an input or output exception occurred
     */
    private void sendError(HttpServletResponse response, int status, Exception e, String requestId) throws IOException {
        response.setStatus(status);
        response.getOutputStream().print(this.getErrorMessage(e, requestId));
    }

}
