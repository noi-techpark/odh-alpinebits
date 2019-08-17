/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation;

/**
 * A validation path can be used to trace the
 * path between from the beginning of a validation
 * to the current position.
 *
 * This information can be used to simplify the
 * detection of the cause of a validation exception.
 */
public interface ValidationPath {

    ValidationPath withElement(String path);

    ValidationPath withAttribute(String path);

    ValidationPath withIndex(int index);

}
