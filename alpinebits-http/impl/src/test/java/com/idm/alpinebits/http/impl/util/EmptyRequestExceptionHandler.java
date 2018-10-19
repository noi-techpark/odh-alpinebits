/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.idm.alpinebits.http.impl.util;

import com.idm.alpinebits.http.RequestExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An empty {@link RequestExceptionHandler} used for testing.
 */
public class EmptyRequestExceptionHandler implements RequestExceptionHandler {

    @Override
    public void handleRequestException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        // Do nothing, since this RequestExceptionHandler is just used for testing
    }

}
