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

package com.idm.alpinebits.http;

import com.idm.alpinebits.middleware.Context;

/**
 * This class defines {@link Context} keys for HTTP values.
 */
public final class HttpContextKey {

    public static final String HTTP_REQUEST = "http.request";
    public static final String HTTP_REQUEST_ID = "http.request.id";
    public static final String HTTP_RESPONSE = "http.response";

    private HttpContextKey() {
        // Empty
    }
}
