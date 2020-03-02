/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking.dto;

import java.util.Objects;
import java.util.Set;

/**
 * This class represents the content of the AlpineBits Handshaking JSON.
 */
public class HandshakingData {

    private Set<SupportedVersion> versions;

    public Set<SupportedVersion> getVersions() {
        return versions;
    }

    public void setVersions(Set<SupportedVersion> versions) {
        this.versions = versions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HandshakingData handshakingData = (HandshakingData) o;
        return Objects.equals(versions, handshakingData.versions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versions);
    }

    @Override
    public String toString() {
        return "HandshakingData{" +
                "versions=" + versions +
                '}';
    }
}
