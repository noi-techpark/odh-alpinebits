/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.servlet.impl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The methods provided by this class can be used to write
 * results to the servlet response and its output stream.
 */
public final class ResponseWriter {

    private ResponseWriter() {
        // Empty
    }

    public static void writeMessage(HttpServletResponse response, String message) throws IOException {
        response.getOutputStream().print(message);
    }

    public static void writeError(HttpServletResponse response, int httpStatus, String requestId, String message) throws IOException {
        String errorMessage = ResponseWriter.buildErrorMessage(message, requestId);
        ResponseWriter.writeMessage(response, errorMessage);
        response.setStatus(httpStatus);
    }

    public static String buildErrorMessage(String message, String requestId) {
        return "ERROR:" + message + " [rid=" + requestId + "]";
    }

}
