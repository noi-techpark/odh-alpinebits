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
 * Default validation messages.
 */
public final class DefaultErrorMessage {

    public static final String EXPECT_AT_LEAST_ONE_SUB_ELEMENT = "Expecting at least one of the following sub elements (none found): '%s'";
    public static final String EXPECT_NOT_NULL = "Expecting not-null value but the validated object '%s' is null";
    public static final String EXPECT_NULL = "Expecting null value but the validated object '%s' is not null";
    public static final String EXPECT_EQUAL = "Expecting the validated objects '%s' and '%s' to be equal, but they are not";
    public static final String EXPECT_ARG0_LESSER_OR_EQUAL_THAN_ARG1 = "Expecting '%s' to be lesser or equal than '%s', but it is not";

    private DefaultErrorMessage() {
        // Empty
    }

    public static String expectNotNullErrorMessage(Object object) {
        return String.format(EXPECT_NOT_NULL, object);
    }

    public static String expectNullErrorMessage(Object object) {
        return String.format(EXPECT_NULL, object);
    }

    public static String expectArg0LesserOrEqualThanArg1ErrorMessage(Object object1, Object object2) {
        return String.format(EXPECT_ARG0_LESSER_OR_EQUAL_THAN_ARG1, object1, object2);
    }
}
