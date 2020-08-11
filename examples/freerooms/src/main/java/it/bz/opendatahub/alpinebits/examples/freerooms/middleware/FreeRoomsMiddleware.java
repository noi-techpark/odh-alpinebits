/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.freerooms.middleware;

import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRS;

/**
 * A simple {@link Middleware} to handle FreeRooms requests,
 * persisting the data from DB.
 */
public class FreeRoomsMiddleware implements Middleware {

    public static final Key<OTAHotelAvailNotifRQ> OTA_FREE_ROOMS_REQUEST
            = Key.key("free rooms request", OTAHotelAvailNotifRQ.class);
    public static final Key<OTAHotelAvailNotifRS> OTA_FREE_ROOMS_RESPONSE
            = Key.key("free rooms response", OTAHotelAvailNotifRS.class);

    private final FreeRoomsService freeRoomsService = new FreeRoomsService();

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        // Get necessary objects from middleware context
        OTAHotelAvailNotifRQ otaHotelAvailNotifRQ = ctx.getOrThrow(OTA_FREE_ROOMS_REQUEST);

        // Use a service to invoke the business logic
        OTAHotelAvailNotifRS otaHotelAvailNotifRS = freeRoomsService.logFreeRooms(otaHotelAvailNotifRQ);

        // Put result back into middleware context
        ctx.put(OTA_FREE_ROOMS_RESPONSE, otaHotelAvailNotifRS);
    }

}
