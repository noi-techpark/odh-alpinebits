// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.context.freerooms;

/**
 * This context contains information about FreeRooms capabilities
 * for AlpineBits versions up to 2018-10.
 */
public final class HotelAvailNotifContext {

    private final boolean roomCategoriesSupported;
    private final boolean distinctRoomsSupported;
    private final boolean deltaSupported;
    private final boolean freeButNotBookableSupported;

    private HotelAvailNotifContext(
            boolean roomCategoriesSupported,
            boolean distinctRoomsSupported,
            boolean deltaSupported,
            boolean freeButNotBookableSupported
    ) {
        this.roomCategoriesSupported = roomCategoriesSupported;
        this.distinctRoomsSupported = distinctRoomsSupported;
        this.deltaSupported = deltaSupported;
        this.freeButNotBookableSupported = freeButNotBookableSupported;
    }

    public boolean isRoomCategoriesSupported() {
        return roomCategoriesSupported;
    }

    public boolean isDistinctRoomsSupported() {
        return distinctRoomsSupported;
    }

    public boolean isDeltaSupported() {
        return deltaSupported;
    }

    public boolean isFreeButNotBookableSupported() {
        return freeButNotBookableSupported;
    }

    @Override
    public String toString() {
        return "CapabilitiesSupport{" +
                "roomCategoriesSupported=" + roomCategoriesSupported +
                ", distinctRoomsSupported=" + distinctRoomsSupported +
                ", deltaSupported=" + deltaSupported +
                ", freeButNotBookableSupported=" + freeButNotBookableSupported +
                '}';
    }

    /**
     * Builder for {@link HotelAvailNotifContext}.
     */
    public static class Builder {
        private boolean roomCategoriesSupported;
        private boolean distinctRoomsSupported;
        private boolean deltaSupported;
        private boolean freeButNotBookableSupported;

        public HotelAvailNotifContext build() {
            return new HotelAvailNotifContext(
                    this.roomCategoriesSupported,
                    this.distinctRoomsSupported,
                    this.deltaSupported,
                    this.freeButNotBookableSupported
            );
        }

        public Builder withRoomCategoriesSupport() {
            this.roomCategoriesSupported = true;
            return this;
        }

        public Builder withDistinctRoomsSupport() {
            this.distinctRoomsSupported = true;
            return this;
        }

        public Builder withDeltaSupport() {
            this.deltaSupported = true;
            return this;
        }

        public Builder withFreeButNotBookableSupport() {
            this.freeButNotBookableSupported = true;
            return this;
        }
    }
}
