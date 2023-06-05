// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.routing.constants;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Tests for {@link Action}.
 */
public class ActionTest {

    private static final String ACTION_NAME_VALUE = "actionName";
    private static final String ACTION_REQUEST_PARAM_VALUE = "actionRequestParam";

    @Test
    public void testOf_ShouldReturnActionInstance() {
        assertNotNull(Action.of(ACTION_REQUEST_PARAM_VALUE, ACTION_NAME_VALUE));
    }

    @Test
    public void testGetRequestParameter_ShouldReturnActionRequestParamProvidedDuringInstantiation() {
        Action action = Action.of(ACTION_REQUEST_PARAM_VALUE, ACTION_NAME_VALUE);
        assertEquals(action.getRequestParameter(), ACTION_REQUEST_PARAM_VALUE);
    }

    @Test
    public void testGetName_ShouldReturnActionNameProvidedDuringInstantiation() {
        Action action = Action.of(ACTION_REQUEST_PARAM_VALUE, ACTION_NAME_VALUE);
        assertEquals(action.getName(), ACTION_NAME_VALUE);
    }

}