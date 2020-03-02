/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.freerooms;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.AvailStatusMessagesContext;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.FreeRoomsContext;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelAvailNotifRQ;

/**
 * Use this validator to validate {@link OTAHotelAvailNotifRQ}
 * objects (AlpineBits 2018-10).
 */
public class OTAHotelAvailNotifRQValidator implements Validator<OTAHotelAvailNotifRQ, FreeRoomsContext> {

    public static final String ELEMENT_NAME = Names.OTA_HOTEL_AVAIL_NOTIF_RQ;
    public static final String COMPLETE_SET = "CompleteSet";

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final UniqueIDValidator uniqueIDValidator = new UniqueIDValidator();
    private final AvailStatusMessagesValidator availStatusMessagesValidator = new AvailStatusMessagesValidator();

    @Override
    public void validate(OTAHotelAvailNotifRQ hotelAvailNotifRQ, FreeRoomsContext ctx, ValidationPath unused) {
        // Initialize validation path
        ValidationPath path = SimpleValidationPath.fromPath(ELEMENT_NAME);

        VALIDATOR.expectNotNull(
                hotelAvailNotifRQ,
                ErrorMessage.EXPECT_HOTEL_AVAIL_NOTIF_RQ_TO_BE_NOT_NULL,
                path
        );
        VALIDATOR.expectNotNull(ctx, ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL);

        this.uniqueIDValidator.validate(
                hotelAvailNotifRQ.getUniqueID(),
                ctx.isDeltaSupported(),
                path.withElement(UniqueIDValidator.ELEMENT_NAME)
        );

        AvailStatusMessagesContext availStatusMessagesContext = this.buildAvailStatusContext(hotelAvailNotifRQ, ctx);
        this.availStatusMessagesValidator.validate(
                hotelAvailNotifRQ.getAvailStatusMessages(),
                availStatusMessagesContext,
                path.withElement(AvailStatusMessagesValidator.ELEMENT_NAME)
        );
    }

    private AvailStatusMessagesContext buildAvailStatusContext(OTAHotelAvailNotifRQ hotelAvailNotifRQ, FreeRoomsContext ctx) {
        String instance = hotelAvailNotifRQ.getUniqueID() != null
                ? hotelAvailNotifRQ.getUniqueID().getInstance()
                : null;
        return AvailStatusMessagesContext.fromFreeRoomsContext(instance, ctx);
    }

}
