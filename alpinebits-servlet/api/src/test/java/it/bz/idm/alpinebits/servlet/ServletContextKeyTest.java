/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.servlet;

import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link ServletContextKey} class.
 */
public class ServletContextKeyTest {

    @Test
    public void testContextKey_ServletRequest() {
        assertEquals(ServletContextKey.SERVLET_REQUEST.getType(), HttpServletRequest.class);
    }

    @Test
    public void testContextKey_ServletResponse() {
        assertEquals(ServletContextKey.SERVLET_RESPONSE.getType(), HttpServletResponse.class);
    }

}