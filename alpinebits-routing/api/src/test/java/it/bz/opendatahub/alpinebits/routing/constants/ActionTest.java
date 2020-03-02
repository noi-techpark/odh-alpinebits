/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.routing.constants;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for {@link Action}.
 */
public class ActionTest {

    private static final String ACTION_NAME_VALUE = "actionName";
    private static final ActionName ACTION_NAME = ActionName.of(ACTION_NAME_VALUE);

    private static final String ACTION_REQUEST_PARAM_VALUE = "actionRequestParam";
    private static final ActionRequestParam ACTION_REQUEST_PARAM = ActionRequestParam.of(ACTION_REQUEST_PARAM_VALUE);

    @Test
    public void testOf_ShouldReturnActionInstance() {
        assertNotNull(Action.of(ACTION_REQUEST_PARAM, ACTION_NAME));
    }

    @Test
    public void testGetRequestParameter_ShouldReturnActionRequestParamProvidedDuringInstantiation() {
        Action action = Action.of(ACTION_REQUEST_PARAM, ACTION_NAME);
        assertEquals(action.getRequestParameter().getValue(), ACTION_REQUEST_PARAM_VALUE);
    }

    @Test
    public void testGetName_ShouldReturnActionNameProvidedDuringInstantiation() {
        Action action = Action.of(ACTION_REQUEST_PARAM, ACTION_NAME);
        assertEquals(action.getName().getValue(), ACTION_NAME_VALUE);
    }

}