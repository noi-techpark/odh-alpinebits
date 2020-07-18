/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.freerooms;

import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.AvailStatusMessagesContext;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.HotelAvailNotifContext;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAHotelAvailNotifRQ;

/**
 * Use this validator to validate the OTAHotelAvailNotifRQ in AlpineBits 2017
 * FreeRooms documents.
 *
 * @see OTAHotelAvailNotifRQ
 */
public class OTAHotelAvailNotifRQValidator implements Validator<OTAHotelAvailNotifRQ, HotelAvailNotifContext> {

    public static final String ELEMENT_NAME = Names.OTA_HOTEL_AVAIL_NOTIF_RQ;
    public static final String COMPLETE_SET = "CompleteSet";

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final UniqueIDValidator uniqueIDValidator = new UniqueIDValidator(ErrorMessage.EXPECT_UNIQUE_ID_TO_BE_NOT_NULL);
    private final AvailStatusMessagesValidator availStatusMessagesValidator = new AvailStatusMessagesValidator();

    @Override
    public void validate(OTAHotelAvailNotifRQ hotelAvailNotifRQ, HotelAvailNotifContext ctx, ValidationPath unused) {
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

    private AvailStatusMessagesContext buildAvailStatusContext(OTAHotelAvailNotifRQ hotelAvailNotifRQ, HotelAvailNotifContext ctx) {
        String instance = hotelAvailNotifRQ.getUniqueID() != null
                ? hotelAvailNotifRQ.getUniqueID().getInstance()
                : null;
        return AvailStatusMessagesContext.fromHotelAvailNotifContext(instance, ctx);
    }

}
