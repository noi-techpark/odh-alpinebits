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
 * The {@link MiddlewareChain} can be used by a {@link Middleware} to invoke
 * the next middleware in the chain, or if the calling middleware is the last middleware
 * in the chain, to invoke the resource at the end of the chain.
 */
public interface MiddlewareChain {

    /**
     * Causes the next {@link Middleware} in the chain to be invoked, or if the calling middleware
     * is the last middleware in the chain, causes the resource at the end of the chain to
     * be invoked.
     */
    void next();

}
