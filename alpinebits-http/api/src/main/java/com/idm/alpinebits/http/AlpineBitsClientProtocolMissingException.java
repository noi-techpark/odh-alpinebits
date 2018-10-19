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

import com.idm.alpinebits.common.AlpineBitsException;

/**
 * This exception is thrown if no <code>X-AlpineBits-ClientProtocolVersion</code> was found in a request.
 */
public class AlpineBitsClientProtocolMissingException extends AlpineBitsException {

    public static final int STATUS = 400;

    /**
     * Constructs a {@code AlpineBitsClientProtocolMissingException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public AlpineBitsClientProtocolMissingException(String msg) {
        super(msg, STATUS);
    }

}
