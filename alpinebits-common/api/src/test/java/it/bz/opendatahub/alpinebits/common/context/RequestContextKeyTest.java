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

import java.io.InputStream;
import java.util.function.Supplier;

import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link RequestContextKey} class.
 */
public class RequestContextKeyTest {

    @Test
    public void testContextKey_RequestId() {
        assertEquals(RequestContextKey.REQUEST_ID.getType(), String.class);
    }

    @Test
    public void testContextKey_RequestUsername() {
        assertEquals(RequestContextKey.REQUEST_USERNAME.getType(), String.class);
    }

    @Test
    public void testContextKey_RequestPasswordSupplier() {
        assertEquals(RequestContextKey.REQUEST_PASSWORD_SUPPLIER.getType(), Supplier.class);
    }

    @Test
    public void testContextKey_RequestVersion() {
        assertEquals(RequestContextKey.REQUEST_VERSION.getType(), String.class);
    }

    @Test
    public void testContextKey_RequestAction() {
        assertEquals(RequestContextKey.REQUEST_ACTION.getType(), String.class);
    }

    @Test
    public void testContextKey_RequestInputStream() {
        assertEquals(RequestContextKey.REQUEST_CONTENT_STREAM.getType(), InputStream.class);
    }

}