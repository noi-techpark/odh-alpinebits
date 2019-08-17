/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation;

/**
 * This context contains the AlpineBits action as single property.
 */
public class ContextWithAction {

    private final String action;

    public ContextWithAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "ContextWithAction{" +
                "action='" + action + '\'' +
                '}';
    }
}
