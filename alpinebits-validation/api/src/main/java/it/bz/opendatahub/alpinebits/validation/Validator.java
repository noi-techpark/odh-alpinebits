// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation;


/**
 * A Validator can be used to validate objects.
 *
 * @param <T> the type of the object to validate
 * @param <C> the type of the validation context
 */
public interface Validator<T, C> {

    /**
     * Validate the given object and throw a {@link ValidationException}
     * if the object was not valid.
     * <p>
     * Additional data for the validation can be provided by
     * the <code>context</code>.
     *
     * @param object      The object to validate
     * @param ctx         The context provides additional data needed
     *                    for the validation
     * @param currentPath The {@link ValidationPath} contains the
     *                    path to the current validation
     * @throws ValidationException if the validation failed
     */
    void validate(T object, C ctx, ValidationPath currentPath);

}
