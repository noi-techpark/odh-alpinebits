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
 * for AlpineBits versions from 2020-10 going on.
 */
public final class HotelInvCountNotifContext {

    private final boolean roomsSupported;
    private final boolean categoriesSupported;
    private final boolean deltasSupported;
    private final boolean outOfOrderSupported;
    private final boolean outOfMarketSupported;
    private final boolean closingSeasonsSupported;

    private HotelInvCountNotifContext(
            boolean roomsSupported,
            boolean categoriesSupported,
            boolean deltasSupported,
            boolean outOfOrderSupported,
            boolean outOfMarketSupported,
            boolean closingSeasonsSupported
    ) {
        this.roomsSupported = roomsSupported;
        this.categoriesSupported = categoriesSupported;
        this.deltasSupported = deltasSupported;
        this.outOfOrderSupported = outOfOrderSupported;
        this.outOfMarketSupported = outOfMarketSupported;
        this.closingSeasonsSupported = closingSeasonsSupported;
    }

    public boolean isRoomsSupported() {
        return roomsSupported;
    }

    public boolean isCategoriesSupported() {
        return categoriesSupported;
    }

    public boolean isDeltasSupported() {
        return deltasSupported;
    }

    public boolean isOutOfOrderSupported() {
        return outOfOrderSupported;
    }

    public boolean isOutOfMarketSupported() {
        return outOfMarketSupported;
    }

    public boolean isClosingSeasonsSupported() {
        return closingSeasonsSupported;
    }

    @Override
    public String toString() {
        return "HotelInvCountNotifContext{" +
                "roomsSupported=" + roomsSupported +
                ", categoriesSupported=" + categoriesSupported +
                ", deltasSupported=" + deltasSupported +
                ", outOfOrderSupported=" + outOfOrderSupported +
                ", outOfMarketSupported=" + outOfMarketSupported +
                ", closingSeasonsSupported=" + closingSeasonsSupported +
                '}';
    }

    /**
     * Builder for {@link HotelInvCountNotifContext}.
     */
    public static class Builder {
        private boolean roomsSupported;
        private boolean categoriesSupported;
        private boolean deltasSupported;
        private boolean outOfOrderSupported;
        private boolean outOfMarketSupported;
        private boolean closingSeasonsSupported;

        public HotelInvCountNotifContext build() {
            return new HotelInvCountNotifContext(
                    this.roomsSupported,
                    this.categoriesSupported,
                    this.deltasSupported,
                    this.outOfOrderSupported,
                    this.outOfMarketSupported,
                    this.closingSeasonsSupported
            );
        }

        public Builder withRoomsSupport() {
            this.roomsSupported = true;
            return this;
        }

        public Builder withCategoriesSupport() {
            this.categoriesSupported = true;
            return this;
        }

        public Builder withDeltasSupport() {
            this.deltasSupported = true;
            return this;
        }

        public Builder withOutOfOrderSupport() {
            this.outOfOrderSupported = true;
            return this;
        }

        public Builder withOutOfMarketSupport() {
            this.outOfMarketSupported = true;
            return this;
        }

        public Builder withClosingSeasonsSupport() {
            this.closingSeasonsSupported = true;
            return this;
        }
    }
}
