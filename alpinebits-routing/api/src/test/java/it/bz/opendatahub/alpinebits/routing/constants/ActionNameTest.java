/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.routing.constants;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for {@link ActionName}.
 */
public class ActionNameTest {

    @Test
    public void testOf_ShouldReturnActionNameInstance() {
        assertNotNull(ActionName.of("some"));
    }

    @Test
    public void testGetValue_ShouldReturnValueProvidedDuringInstantiation() {
        String text = "text";
        ActionName actionName = ActionName.of(text);
        assertEquals(actionName.getValue(), text);
    }

}