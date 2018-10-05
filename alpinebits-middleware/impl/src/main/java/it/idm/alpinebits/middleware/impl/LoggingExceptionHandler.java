/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.idm.alpinebits.middleware.impl;

import it.idm.alpinebits.middleware.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * All this {@link ExceptionHandler} does is to log the given exception.
 */
public class LoggingExceptionHandler implements ExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(LoggingExceptionHandler.class);

    @Override
    public void handleException(Exception e) {
        LOG.error("Uncaught exception", e);
    }
}
