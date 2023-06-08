// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.middleware;

/**
 * This exception is thrown if a required key is missing in a {@link Context}.
 */
public class RequiredContextKeyMissingException extends RuntimeException {

    public RequiredContextKeyMissingException(String msg) {
        super(msg);
    }

}
