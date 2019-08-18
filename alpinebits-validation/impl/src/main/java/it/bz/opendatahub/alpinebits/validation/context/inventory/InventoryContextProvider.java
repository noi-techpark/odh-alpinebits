/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.context.inventory;

import it.bz.opendatahub.alpinebits.common.context.RequestContextKey;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.validation.context.ValidationContextProvider;

/**
 * Implementation of {@link ValidationContextProvider} that
 * produces a {@link InventoryContext}.
 */
public class InventoryContextProvider implements ValidationContextProvider<InventoryContext> {

    @Override
    public InventoryContext buildContext(Context ctx) {
        if (ctx == null) {
            throw new IllegalArgumentException("Context is required");
        }

        String action = ctx.getOrThrow(RequestContextKey.REQUEST_ACTION);
        return new InventoryContext(action);
    }

}
