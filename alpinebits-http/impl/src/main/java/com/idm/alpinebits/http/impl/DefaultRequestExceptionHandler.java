/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.idm.alpinebits.http.impl;

import com.idm.alpinebits.common.AlpineBitsException;
import com.idm.alpinebits.http.RequestExceptionHandler;
import com.idm.alpinebits.middleware.RequiredContextKeyMissingException;
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
 *     <li>
 *        for an {@link AlpineBitsException}, the value provided by {@link AlpineBitsException#getCode()}
 *        is returned as HTTP status. If the status is not in the range of valid HTTP statuses (i.e.
 *        status < 100 || status > 599), a HTTP status of 500 is returned to align to common HTTP statuses.
 *     </li>
 *     <li>
 *        for all other exception types, a 500 HTTP status is returned
 *     </li>
 * </ul>
 * <p>
 *
 */
public class DefaultRequestExceptionHandler implements RequestExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultRequestExceptionHandler.class);

    @Override
    public void handleRequestException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        if (e instanceof AlpineBitsException) {
            LOG.error("Handling uncaught AlpinBitsException", e);

            int status = this.getStatus((AlpineBitsException) e);
            this.sendError(response, status, e);
        } else if (e instanceof RequiredContextKeyMissingException) {
            LOG.error("Context key missing", e);
            this.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        } else {
            LOG.error("Handling uncaught Exception", e);
            this.sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        }
    }

    public String getErrorMessage(Exception e) {
        return e instanceof AlpineBitsException
                ? "ERROR:" + ((AlpineBitsException)e).getResponseMessage()
                : "ERROR:" + e.getMessage();
    }

    private int getStatus(AlpineBitsException e) {
        int status = e.getCode();
        return status < 100 || status > 599 ? HttpServletResponse.SC_INTERNAL_SERVER_ERROR : status;
    }

    /**
     * Set the response error by setting status explicitly and writing the error message using the writer.
     *
     * This is necessary, since otherwise application servers (e.g. Tomcat) may wrap the error inside
     * their (HTML) error page
     *
     * @param response set the status and message for this {@link HttpServletResponse}
     * @param status the status to set
     * @param e the exception, containing the relevant message
     * @throws IOException if an input or output exception occurred
     */
    private void sendError(HttpServletResponse response, int status, Exception e) throws IOException {
        response.setStatus(status);
        response.getWriter().print(this.getErrorMessage(e));
    }

}
