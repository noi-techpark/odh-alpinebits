// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.utils;

import java.util.List;

/**
 * Simple list utilities.
 */
public final class ListUtil {

    private ListUtil() {
        // Empty
    }

    /**
     * Extract first entry from list, if such an entry exists.
     *
     * @param list A list with elements, from where the first element should
     *             be extracted.
     * @param <T>  The type of list elements and the the type of the expected
     *             first element.
     * @return The first element in the list. If the list is null or there
     * are no elements in the list, then <code>null</code> is returned.
     */
    public static <T> T extractFirst(List<T> list) {
        return list == null || list.isEmpty() ? null : list.get(0);
    }

}
