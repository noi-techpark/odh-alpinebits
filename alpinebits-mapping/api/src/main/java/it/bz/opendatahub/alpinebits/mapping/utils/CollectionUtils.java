/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.utils;

import java.util.Collection;

/**
 * Class with collection utilities to support the
 * mapper process.
 */
public final class CollectionUtils {

    private CollectionUtils() {
        // Empty
    }

    /**
     * If the given {@link Collection} is either null or empty,
     * this method returns true. It returns false otherwise.
     *
     * @param collection the collection to test
     * @return true if the collection is null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
