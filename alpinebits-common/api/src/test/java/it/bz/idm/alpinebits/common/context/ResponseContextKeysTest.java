/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.common.context;

import org.testng.annotations.Test;

import java.io.OutputStream;
import java.util.Collection;

import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link ResponseContextKeys} class.
 */
public class ResponseContextKeysTest {

    @Test
    public void testContextKey_ResponseCapabilities() {
        assertEquals(ResponseContextKeys.RESPONSE_CAPABILITIES.getType(), Collection.class);
    }

    @Test
    public void testContextKey_ResponseVersion() {
        assertEquals(ResponseContextKeys.RESPONSE_VERSION.getType(), String.class);
    }

    @Test
    public void testContextKey_ResponseContentStream() {
        assertEquals(ResponseContextKeys.RESPONSE_CONTENT_STREAM.getType(), OutputStream.class);
    }

}