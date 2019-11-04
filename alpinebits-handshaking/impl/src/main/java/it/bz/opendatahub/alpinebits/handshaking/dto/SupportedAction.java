/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking.dto;

import java.util.Objects;
import java.util.Set;

/**
 * This class represents an entry in the "actions" array of
 * an AlpineBits Handshaking JSON.
 */
public class SupportedAction {

    private String action;

    private Set<String> supports;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Set<String> getSupports() {
        return supports;
    }

    public void setSupports(Set<String> supports) {
        this.supports = supports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SupportedAction that = (SupportedAction) o;
        return Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action);
    }

    @Override
    public String toString() {
        return "SupportedAction{" +
                "action='" + action + '\'' +
                ", supports=" + supports +
                '}';
    }
}
