/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.freerooms.middleware.configuration;

import it.bz.opendatahub.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.examples.freerooms.middleware.FreeRoomsMiddleware;
import it.bz.opendatahub.alpinebits.middleware.Middleware;

import java.util.Arrays;

/**
 * Utility class to build a {@link FreeRoomsMiddleware}.
 */
public final class FreeRoomsMiddlewareBuilder {

    private FreeRoomsMiddlewareBuilder() {
        // Empty
    }

    public static Middleware buildFreeRoomsMiddleware() {
        return ComposingMiddlewareBuilder.compose(Arrays.asList(
                XmlMiddlewareBuilder.buildXmlToObjectConvertingMiddleware(FreeRoomsMiddleware.OTA_FREE_ROOMS_REQUEST),
                XmlMiddlewareBuilder.buildObjectToXmlConvertingMiddleware(FreeRoomsMiddleware.OTA_FREE_ROOMS_RESPONSE),
                new FreeRoomsMiddleware()
        ));
    }
}
