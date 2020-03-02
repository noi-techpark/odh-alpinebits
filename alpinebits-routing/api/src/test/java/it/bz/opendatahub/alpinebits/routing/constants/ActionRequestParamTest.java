/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.routing.constants;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for {@link ActionRequestParam}.
 */
public class ActionRequestParamTest {

    @Test
    public void testOf_ShouldReturnActionRequestParamInstance() {
        assertNotNull(ActionRequestParam.of("some"));
    }

    @Test
    public void testGetValue_ShouldReturnValueProvidedDuringInstantiation() {
        String text = "text";
        ActionRequestParam actionName = ActionRequestParam.of(text);
        assertEquals(actionName.getValue(), text);
    }

}