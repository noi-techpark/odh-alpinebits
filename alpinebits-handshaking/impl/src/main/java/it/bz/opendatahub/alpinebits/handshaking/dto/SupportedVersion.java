/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking.dto;

import java.util.Objects;
import java.util.Set;

/**
 * This class represents an entry in the "versions" array of
 * an AlpineBits Handshaking JSON.
 */
public class SupportedVersion {

    private String version;
    private Set<SupportedAction> actions;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Set<SupportedAction> getActions() {
        return actions;
    }

    public void setActions(Set<SupportedAction> actions) {
        this.actions = actions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SupportedVersion version1 = (SupportedVersion) o;
        return Objects.equals(version, version1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version);
    }

    @Override
    public String toString() {
        return "SupportedVersion{" +
                "version='" + version + '\'' +
                ", actions=" + actions +
                '}';
    }
}
