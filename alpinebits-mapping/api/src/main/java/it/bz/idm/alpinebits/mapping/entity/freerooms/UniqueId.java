/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.entity.freerooms;

/**
 * This class represents an AlpineBits FreeRooms UniqueID.
 */
public class UniqueId {

    private String type;

    private String instance;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    @Override
    public String toString() {
        return "UniqueId{" +
                "type='" + type + '\'' +
                ", instance='" + instance + '\'' +
                '}';
    }
}
