/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.freerooms.fromxml;

import it.bz.opendatahub.alpinebits.validation.NotLesserOrEqualValidationException;
import it.bz.opendatahub.alpinebits.validation.NotNullValidationException;
import it.bz.opendatahub.alpinebits.validation.NullValidationException;
import it.bz.opendatahub.alpinebits.validation.ValidationException;
import it.bz.opendatahub.alpinebits.validation.XmlSchemaType;
import it.bz.opendatahub.alpinebits.validation.context.freerooms.HotelAvailNotifContext;
import it.bz.opendatahub.alpinebits.xml.XmlConversionException;
import org.testng.annotations.DataProvider;

/**
 * Data providing class for {@link FreeRoomsFromXmlTest}.
 */
public class XmlDataProvider {

    private static final HotelAvailNotifContext DEFAULT_CTX = new HotelAvailNotifContext.Builder().build();
    private static final HotelAvailNotifContext CTX_WITH_DELTA_SUPPORT = new HotelAvailNotifContext.Builder()
            .withDeltaSupport().build();
    private static final HotelAvailNotifContext CTX_WITH_DISTINCT_ROOMS_SUPPORT = new HotelAvailNotifContext.Builder()
            .withDistinctRoomsSupport().build();
    private static final HotelAvailNotifContext CTX_WITH_ROOM_CATEGORIES_AND_DISTINCT_ROOM_SUPPORT = new HotelAvailNotifContext.Builder()
            .withRoomCategoriesSupport().withDistinctRoomsSupport().build();
    private static final HotelAvailNotifContext CTX_WITH_FREE_BUT_NOT_BOOKABLE_SUPPORT = new HotelAvailNotifContext.Builder()
            .withFreeButNotBookableSupport().build();

    @DataProvider(name = "xml")
    // Suppress line-length warnings for this test configuration
    @SuppressWarnings("checkstyle:linelength")
    public static Object[][] xmlProvider() {
        return new Object[][]{
                {"HotelAvailNotifRQ_ok_uniqueid-and-reset.xml", DEFAULT_CTX, XmlSchemaType.XSD_SCHEMA, null},
                {"HotelAvailNotifRQ_ok_uniqueid-and-reset.xml", DEFAULT_CTX, XmlSchemaType.RNG_SCHEMA, null},

                // Reset message (single empty "AvailStatusMessage" element)
                // requires "UniqueID" element, independently of delta supported.
                // To not trigger a different error ("UniqueID" element requires
                // delta support), delta support is set for this test
                {"HotelAvailNotifRQ_err_no-uniqueid-and-reset.xml", CTX_WITH_DELTA_SUPPORT, XmlSchemaType.XSD_SCHEMA, NullValidationException.class},
                {"HotelAvailNotifRQ_err_no-uniqueid-and-reset.xml", CTX_WITH_DELTA_SUPPORT, XmlSchemaType.RNG_SCHEMA, NullValidationException.class},

                // Missing "UniqueID" element means delta update, delta support required (OTA_HotelAvailNotif_accept_deltas capability)
                {"HotelAvailNotifRQ_no-uniqueid-and-distinct-rooms.xml", DEFAULT_CTX, XmlSchemaType.XSD_SCHEMA, NullValidationException.class},
                {"HotelAvailNotifRQ_no-uniqueid-and-distinct-rooms.xml", DEFAULT_CTX, XmlSchemaType.RNG_SCHEMA, NullValidationException.class},

                {"HotelAvailNotifRQ_err_no-hotelcode-and-hotelname.xml", DEFAULT_CTX, XmlSchemaType.XSD_SCHEMA, ValidationException.class},
                {"HotelAvailNotifRQ_err_no-hotelcode-and-hotelname.xml", DEFAULT_CTX, XmlSchemaType.RNG_SCHEMA, XmlConversionException.class},

                // The following test throws a XmlConversionException for RNG Schema only, because
                // RNG schema is clever enough to recognize that BookingLimit must not be null.
                {"HotelAvailNotifRQ_err_bookinglimit-null.xml", DEFAULT_CTX, XmlSchemaType.XSD_SCHEMA, NullValidationException.class},
                {"HotelAvailNotifRQ_err_bookinglimit-null.xml", DEFAULT_CTX, XmlSchemaType.RNG_SCHEMA, XmlConversionException.class},

                // The following test throws a XmlConversionException for RNG Schema only, because
                // RNG schema is clever enough to recognize that BookingLimitMessage.
                {"HotelAvailNotifRQ_err_bookinglimitmessagetype-null.xml", DEFAULT_CTX, XmlSchemaType.XSD_SCHEMA, NullValidationException.class},
                {"HotelAvailNotifRQ_err_bookinglimitmessagetype-null.xml", DEFAULT_CTX, XmlSchemaType.RNG_SCHEMA, XmlConversionException.class},

                {"HotelAvailNotifRQ_err_bookingthreshold-not-null.xml", DEFAULT_CTX, XmlSchemaType.XSD_SCHEMA, NotNullValidationException.class},
                {"HotelAvailNotifRQ_err_bookingthreshold-not-null.xml", DEFAULT_CTX, XmlSchemaType.RNG_SCHEMA, NotNullValidationException.class},

                {"HotelAvailNotifRQ_err_bookingthreshold-null.xml", CTX_WITH_FREE_BUT_NOT_BOOKABLE_SUPPORT, XmlSchemaType.XSD_SCHEMA, NullValidationException.class},
                {"HotelAvailNotifRQ_err_bookingthreshold-null.xml", CTX_WITH_FREE_BUT_NOT_BOOKABLE_SUPPORT, XmlSchemaType.RNG_SCHEMA, NullValidationException.class},

                {"HotelAvailNotifRQ_err_bookingthreshold-condition.xml", CTX_WITH_FREE_BUT_NOT_BOOKABLE_SUPPORT, XmlSchemaType.XSD_SCHEMA, NotLesserOrEqualValidationException.class},
                {"HotelAvailNotifRQ_err_bookingthreshold-condition.xml", CTX_WITH_FREE_BUT_NOT_BOOKABLE_SUPPORT, XmlSchemaType.RNG_SCHEMA, NotLesserOrEqualValidationException.class},

                // The following test throws a XmlConversionException for RNG Schema only, because
                // RNG schema is clever enough to recognize that InvTypeCode.
                {"HotelAvailNotifRQ_err_invtypecode-null.xml", DEFAULT_CTX, XmlSchemaType.XSD_SCHEMA, NullValidationException.class},
                {"HotelAvailNotifRQ_err_invtypecode-null.xml", DEFAULT_CTX, XmlSchemaType.RNG_SCHEMA, XmlConversionException.class},

                {"HotelAvailNotifRQ_err_invtypecode-null.xml", DEFAULT_CTX, XmlSchemaType.XSD_SCHEMA, NullValidationException.class},
                {"HotelAvailNotifRQ_err_invtypecode-null.xml", DEFAULT_CTX, XmlSchemaType.RNG_SCHEMA, XmlConversionException.class},

                {"HotelAvailNotifRQ_uniqueid-room-categories.xml", DEFAULT_CTX, XmlSchemaType.XSD_SCHEMA, ValidationException.class},
                {"HotelAvailNotifRQ_uniqueid-room-categories.xml", DEFAULT_CTX, XmlSchemaType.RNG_SCHEMA, ValidationException.class},

                // FreeRooms update for room categories requires OTA_HotelAvailNotif_accept_categories capability
                {"HotelAvailNotifRQ_uniqueid-room-categories.xml", DEFAULT_CTX, XmlSchemaType.XSD_SCHEMA, ValidationException.class},
                {"HotelAvailNotifRQ_uniqueid-room-categories.xml", DEFAULT_CTX, XmlSchemaType.RNG_SCHEMA, ValidationException.class},

                // FreeRooms update for distinct rooms requires OTA_HotelAvailNotif_accept_rooms capability
                {"HotelAvailNotifRQ_uniqueid-distinct-rooms.xml", DEFAULT_CTX, XmlSchemaType.XSD_SCHEMA, ValidationException.class},
                {"HotelAvailNotifRQ_uniqueid-distinct-rooms.xml", DEFAULT_CTX, XmlSchemaType.RNG_SCHEMA, ValidationException.class},

                // Distinct rooms requires BookingLimit to be "0" or "1"
                {"HotelAvailNotifRQ_err_bookinglimit-condition.xml", CTX_WITH_DISTINCT_ROOMS_SUPPORT, XmlSchemaType.XSD_SCHEMA, ValidationException.class},
                {"HotelAvailNotifRQ_err_bookinglimit-condition.xml", CTX_WITH_DISTINCT_ROOMS_SUPPORT, XmlSchemaType.RNG_SCHEMA, ValidationException.class},

                // FreeRooms update for room categories and distinct rooms can not be mixed
                {"HotelAvailNotifRQ_err_room-categories-and-distinct-rooms-mixed.xml", CTX_WITH_ROOM_CATEGORIES_AND_DISTINCT_ROOM_SUPPORT, XmlSchemaType.XSD_SCHEMA, ValidationException.class},
                {"HotelAvailNotifRQ_err_room-categories-and-distinct-rooms-mixed.xml", CTX_WITH_ROOM_CATEGORIES_AND_DISTINCT_ROOM_SUPPORT, XmlSchemaType.RNG_SCHEMA, ValidationException.class},
        };
    }

}
