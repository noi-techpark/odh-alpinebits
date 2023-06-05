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
 * This class contains context information for AvailStatusMessagesValidators.
 */
public final class AvailStatusMessagesContext {

    private final String instance;
    private final boolean roomCategoriesSupported;
    private final boolean distinctRoomsSupported;
    private final boolean freeButNotBookableSupported;

    private AvailStatusMessagesContext(
            String instance,
            boolean roomCategoriesSupported,
            boolean distinctRoomsSupported,
            boolean freeButNotBookableSupported
    ) {
        this.instance = instance;
        this.roomCategoriesSupported = roomCategoriesSupported;
        this.distinctRoomsSupported = distinctRoomsSupported;
        this.freeButNotBookableSupported = freeButNotBookableSupported;
    }

    public static AvailStatusMessagesContext fromHotelAvailNotifContext(String instance, HotelAvailNotifContext ctx) {
        return new AvailStatusMessagesContext(
                instance,
                ctx.isRoomCategoriesSupported(),
                ctx.isDistinctRoomsSupported(),
                ctx.isFreeButNotBookableSupported()
        );
    }

    public String getInstance() {
        return instance;
    }

    public boolean isRoomCategoriesSupported() {
        return roomCategoriesSupported;
    }

    public boolean isDistinctRoomsSupported() {
        return distinctRoomsSupported;
    }

    public boolean isFreeButNotBookableSupported() {
        return freeButNotBookableSupported;
    }

    @Override
    public String toString() {
        return "AvailStatusContext{" +
                "instance='" + instance + '\'' +
                ", roomCategoriesSupported=" + roomCategoriesSupported +
                ", distinctRoomsSupported=" + distinctRoomsSupported +
                ", freeButNotBookableSupported=" + freeButNotBookableSupported +
                '}';
    }

    /**
     * Builder for {@link AvailStatusMessagesContext}.
     */
    public static class Builder {
        private String instance;
        private boolean roomCategoriesSupported;
        private boolean distinctRoomsSupported;
        private boolean freeButNotBookableSupported;

        public AvailStatusMessagesContext build() {
            return new AvailStatusMessagesContext(
                    this.instance,
                    this.roomCategoriesSupported,
                    this.distinctRoomsSupported,
                    this.freeButNotBookableSupported
            );
        }

        public Builder withInstance(String instance) {
            this.instance = instance;
            return this;
        }

        public Builder withRoomsCategoriesSupport() {
            this.roomCategoriesSupported = true;
            return this;
        }

        public Builder withDistinctRoomsSupport() {
            this.distinctRoomsSupported = true;
            return this;
        }

        public Builder withFreeButNotBookableSupport() {
            this.freeButNotBookableSupported = true;
            return this;
        }
    }
}
