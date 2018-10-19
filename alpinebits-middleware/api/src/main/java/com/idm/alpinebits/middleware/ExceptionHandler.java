/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.idm.alpinebits.middleware;

/**
 * An ExceptionHandler is used to handleContext uncaught exceptions.
 */
public interface ExceptionHandler {

    /**
     * Handle given exception.
     *
     * @param e exception that should be handled
     */
    void handleException(Exception e);
}
