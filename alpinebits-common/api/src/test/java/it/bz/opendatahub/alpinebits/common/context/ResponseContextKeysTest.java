// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.context;

import org.testng.annotations.Test;

import java.io.OutputStream;

import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link ResponseContextKeys} class.
 */
public class ResponseContextKeysTest {

    @Test
    public void testContextKey_ResponseContentStream() {
        assertEquals(ResponseContextKeys.RESPONSE_CONTENT_STREAM.getType(), OutputStream.class);
    }

}