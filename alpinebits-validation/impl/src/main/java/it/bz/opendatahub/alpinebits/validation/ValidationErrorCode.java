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
 * This class provides validation error codes.
 */
public final class ValidationErrorCode {

    // Client errors range from 1000 to 1999
    public static final int CLIENT_ERROR = 1000;
    public static final int CLIENT_DATA_ERROR = 1001;

    // Server errors range from 2000 to 2999
    public static final int SERVER_ERROR = 2000;
    public static final int SERVER_DATA_ERROR = 2001;

    private ValidationErrorCode() {
        // Empty constructor to prevent the creation of
        // an instance of this class
    }
}
