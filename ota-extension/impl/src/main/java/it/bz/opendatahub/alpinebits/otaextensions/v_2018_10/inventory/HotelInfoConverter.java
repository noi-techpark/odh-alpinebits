/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.otaextensions.v_2018_10.inventory;

import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.CategoryCodesType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelInfoType;
import it.bz.opendatahub.alpinebits.otaextensions.ConverterUtil;
import it.bz.opendatahub.alpinebits.otaextensions.exception.OtaExtensionException;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

/**
 * This class implements an OTA extension converter for AlpineBits inventory actions,
 * specialized on OTA HotelInfo.
 * <p>
 * The provided converter facilitates the conversion between the generic JAXB classes
 * generated for AlpineBits at the given extension points and OTA classes.
 * <p>
 * The AlpineBits 2018-10 inventory actions specify, that any valid OTA data can be used at
 * the following positions as an extension:
 * <ul>
 * <li>OTAHotelDescriptiveContentNotifRQ {@literal ->} HotelDescriptiveContents {@literal ->} HotelDescriptiveContent {@literal ->} AffiliationInfo</li>
 * <li>OTAHotelDescriptiveContentNotifRQ {@literal ->} HotelDescriptiveContents {@literal ->} HotelDescriptiveContent {@literal ->} HotelInfo</li>
 * <li>OTAHotelDescriptiveContentNotifRQ {@literal ->} HotelDescriptiveContents {@literal ->} HotelDescriptiveContent {@literal ->} Policies</li>
 * </ul>
 *
 * @see AffiliationInfoConverter
 * @see PoliciesConverter
 */
public final class HotelInfoConverter {

    private static final String HOTEL_INFO = "HotelInfo";

    private static final String HOTEL_INFO_ATTRIBUTE_WHEN_BUILT = "WhenBuilt";
    private static final String HOTEL_INFO_ATTRIBUTE_LAST_UPDATED = "LastUpdated";
    private static final String HOTEL_INFO_ATTRIBUTE_AREA_WEATHER = "AreaWeather";
    private static final String HOTEL_INFO_ATTRIBUTE_INTERFACE_COMPLIANCE = "InterfaceCompliance";
    private static final String HOTEL_INFO_ATTRIBUTE_PMS_SYSTEM = "PMSSystem";
    private static final String HOTEL_INFO_ATTRIBUTE_HOTEL_STATUS = "HotelStatus";
    private static final String HOTEL_INFO_ATTRIBUTE_HOTEL_STATUS_CODE = "HotelStatusCode";
    private static final String HOTEL_INFO_ATTRIBUTE_TAX_ID = "TaxID";
    private static final String HOTEL_INFO_ATTRIBUTE_DAYLIGHT_SAVING_INDICATOR = "DaylightSavingIndicator";
    private static final String HOTEL_INFO_ATTRIBUTE_ISO9000_CERTIFIED_IND = "ISO9000CertifiedInd";
    private static final String HOTEL_INFO_ATTRIBUTE_START = "Start";
    private static final String HOTEL_INFO_ATTRIBUTE_DURATION = "Duration";
    private static final String HOTEL_INFO_ATTRIBUTE_END = "End";

    private static final String HOTEL_INFO_ELEMENT_HOTEL_NAME = "HotelName";
    private static final String HOTEL_INFO_ELEMENT_CLOSED_SEASONS = "ClosedSeasons";
    private static final String HOTEL_INFO_ELEMENT_BLACKOUT_DATES = "BlackoutDates";
    private static final String HOTEL_INFO_ELEMENT_RELATIVE_POSITIONS = "RelativePositions";
    private static final String HOTEL_INFO_ELEMENT_CATEGORY_CODES = "CategoryCodes";
    private static final String HOTEL_INFO_ELEMENT_DESCRIPTIONS = "Descriptions";
    private static final String HOTEL_INFO_ELEMENT_HOTEL_INFO_CODES = "HotelInfoCodes";
    private static final String HOTEL_INFO_ELEMENT_POSITION = "Position";
    private static final String HOTEL_INFO_ELEMENT_SERVICES = "Services";
    private static final String HOTEL_INFO_ELEMENT_WEATHER_INFOS = "WeatherInfos";
    private static final String HOTEL_INFO_ELEMENT_OWNERSHIP_MANAGEMENT_INFOS = "OwnershipManagementInfos";
    private static final String HOTEL_INFO_ELEMENT_LANGUAGES = "Languages";

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    private HotelInfoConverter(Marshaller marshaller, Unmarshaller unmarshaller) {
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
    }

    /**
     * Create a new instance of InventoryObjectToXmlConverter that can be used
     * to convert between generic JAXB classes for OTAHotelDescriptiveContentNotifRQ
     * {@literal ->} HotelDescriptiveContents {@literal ->} HotelDescriptiveContent and OTA classes.
     *
     * @return an instance of InventoryObjectToXmlConverter
     * @throws OtaExtensionException if the creation of the instance was not possible
     */
    public static HotelInfoConverter newInstance() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(
                    HotelInfoType.class
            );
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Marshaller marshaller = jaxbContext.createMarshaller();
            return new HotelInfoConverter(marshaller, unmarshaller);
        } catch (JAXBException e) {
            throw new OtaExtensionException("Error during converter initialization", e);
        }
    }

    /**
     * Convert an AlpineBits HotelInfo element to an OTA {@link HotelInfoType}.
     * <p>
     * The AlpineBits HotelInfoType has an <code>any</code> type, that allows any OTA
     * content valid for this position according to the specification.
     * <p>
     * This method converts the content of the AlpineBits HotelInfoType into
     * corresponding OTA types.
     *
     * @param hotelDescriptiveContent the OTA HotelDescriptiveContent that contains hotel info
     * @return the converted {@link HotelInfoType} or null if the hotelDescriptiveContent is null
     * or its hotelInfo is null or empty
     * @throws OtaExtensionException if there was an error during the conversion
     */
    public HotelInfoType toHotelInfoType(OTAHotelDescriptiveContentNotifRQ
                                                 .HotelDescriptiveContents
                                                 .HotelDescriptiveContent hotelDescriptiveContent) {
        if (this.isHotelInfoEmpty(hotelDescriptiveContent)) {
            return null;
        }

        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.HotelInfo hotelInfo =
                hotelDescriptiveContent.getHotelInfo();

        HotelInfoType hotelInfoType = new HotelInfoType();

        List<Element> anies = hotelInfo.getAnies();
        if (anies != null) {
            try {
                for (Element element : hotelInfo.getAnies()) {
                    this.handleElement(element, hotelInfoType);
                }
            } catch (JAXBException e) {
                throw new OtaExtensionException("Error during unmarshalling of OTA extensions", e);
            }
        }

        for (Map.Entry<QName, String> entry : hotelInfo.getOtherAttributes().entrySet()) {
            this.handleAttribute(entry, hotelInfoType);
        }

        return hotelInfoType;
    }

    /**
     * Apply the {@link HotelInfoType} to the HotelDescriptiveContent.
     * <p>
     * The method first tries to convert the provided hotelInfoType to XML
     * elements using JAXB. Then it adds those elements to the hotelDescriptiveContent.
     * <p>
     * If either of the input params are null, no modification will take place.
     *
     * @param hotelDescriptiveContent apply the {@link HotelInfoType} to this hotelDescriptiveContent
     * @param hotelInfoType           apply this {@link HotelInfoType} to the
     *                                hotelDescriptiveContent
     * @throws OtaExtensionException if there was an error during the conversion of the
     *                               provided {@link HotelInfoType}
     */
    public void applyHotelInfo(
            OTAHotelDescriptiveContentNotifRQ
                    .HotelDescriptiveContents
                    .HotelDescriptiveContent hotelDescriptiveContent,
            HotelInfoType hotelInfoType
    ) {
        if (hotelDescriptiveContent == null || hotelInfoType == null) {
            return;
        }

        try {
            Element elt = ConverterUtil.marshallToElement(marshaller, HOTEL_INFO, HotelInfoType.class, hotelInfoType);

            OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.HotelInfo hotelInfo =
                    new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.HotelInfo();
            hotelDescriptiveContent.setHotelInfo(hotelInfo);

            List<Element> elements = ConverterUtil.convertToElements(elt.getChildNodes());
            hotelInfo.getAnies().addAll(elements);

            Map<QName, String> attributeMap = ConverterUtil.convertToAttributeMap(elt.getAttributes());
            hotelInfo.getOtherAttributes().putAll(attributeMap);
        } catch (JAXBException e) {
            throw new OtaExtensionException("Error during marshalling of OTA extensions", e);
        }
    }

    private boolean isHotelInfoEmpty(OTAHotelDescriptiveContentNotifRQ
                                             .HotelDescriptiveContents
                                             .HotelDescriptiveContent hotelDescriptiveContent) {
        if (hotelDescriptiveContent == null) {
            return true;
        }

        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.HotelInfo hotelInfo =
                hotelDescriptiveContent.getHotelInfo();

        return hotelInfo == null
                || ConverterUtil.isNodeEmpty(hotelInfo.getAnies(), hotelInfo.getOtherAttributes());
    }

    private void handleElement(Element element, HotelInfoType hotelInfoType) throws JAXBException {
        switch (element.getTagName()) {
            case HOTEL_INFO_ELEMENT_HOTEL_NAME:
                HotelInfoType.HotelName hotelName = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        HotelInfoType.HotelName.class
                );
                hotelInfoType.setHotelName(hotelName);
                break;
            case HOTEL_INFO_ELEMENT_CLOSED_SEASONS:
                HotelInfoType.ClosedSeasons closedSeasons = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        HotelInfoType.ClosedSeasons.class
                );
                hotelInfoType.setClosedSeasons(closedSeasons);
                break;
            case HOTEL_INFO_ELEMENT_BLACKOUT_DATES:
                HotelInfoType.BlackoutDates blackoutDates = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        HotelInfoType.BlackoutDates.class
                );
                hotelInfoType.setBlackoutDates(blackoutDates);
                break;
            case HOTEL_INFO_ELEMENT_RELATIVE_POSITIONS:
                HotelInfoType.RelativePositions relativePositions = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        HotelInfoType.RelativePositions.class
                );
                hotelInfoType.setRelativePositions(relativePositions);
                break;
            case HOTEL_INFO_ELEMENT_CATEGORY_CODES:
                CategoryCodesType categoryCodesType = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        CategoryCodesType.class
                );
                hotelInfoType.setCategoryCodes(categoryCodesType);
                break;
            case HOTEL_INFO_ELEMENT_DESCRIPTIONS:
                HotelInfoType.Descriptions descriptions = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        HotelInfoType.Descriptions.class
                );
                hotelInfoType.setDescriptions(descriptions);
                break;
            case HOTEL_INFO_ELEMENT_HOTEL_INFO_CODES:
                HotelInfoType.HotelInfoCodes hotelInfoCodes = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        HotelInfoType.HotelInfoCodes.class
                );
                hotelInfoType.setHotelInfoCodes(hotelInfoCodes);
                break;
            case HOTEL_INFO_ELEMENT_POSITION:
                HotelInfoType.Position position = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        HotelInfoType.Position.class
                );
                hotelInfoType.setPosition(position);
                break;
            case HOTEL_INFO_ELEMENT_SERVICES:
                HotelInfoType.Services services = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        HotelInfoType.Services.class
                );
                hotelInfoType.setServices(services);
                break;
            case HOTEL_INFO_ELEMENT_WEATHER_INFOS:
                HotelInfoType.WeatherInfos weatherInfos = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        HotelInfoType.WeatherInfos.class
                );
                hotelInfoType.setWeatherInfos(weatherInfos);
                break;
            case HOTEL_INFO_ELEMENT_OWNERSHIP_MANAGEMENT_INFOS:
                HotelInfoType.OwnershipManagementInfos ownershipManagementInfos = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        HotelInfoType.OwnershipManagementInfos.class
                );
                hotelInfoType.setOwnershipManagementInfos(ownershipManagementInfos);
                break;
            case HOTEL_INFO_ELEMENT_LANGUAGES:
                HotelInfoType.Languages languages = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        HotelInfoType.Languages.class
                );
                hotelInfoType.setLanguages(languages);
                break;
            default:
                break;
        }
    }

    private void handleAttribute(Map.Entry<QName, String> entry, HotelInfoType hotelInfoType) {
        switch (entry.getKey().getLocalPart()) {
            case HOTEL_INFO_ATTRIBUTE_WHEN_BUILT:
                hotelInfoType.setWhenBuilt(entry.getValue());
                break;
            case HOTEL_INFO_ATTRIBUTE_LAST_UPDATED:
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(entry.getValue());
                hotelInfoType.setLastUpdated(zonedDateTime);
                break;
            case HOTEL_INFO_ATTRIBUTE_AREA_WEATHER:
                hotelInfoType.setAreaWeather(entry.getValue());
                break;
            case HOTEL_INFO_ATTRIBUTE_INTERFACE_COMPLIANCE:
                hotelInfoType.setInterfaceCompliance(entry.getValue());
                break;
            case HOTEL_INFO_ATTRIBUTE_PMS_SYSTEM:
                hotelInfoType.setPMSSystem(entry.getValue());
                break;
            case HOTEL_INFO_ATTRIBUTE_HOTEL_STATUS:
                hotelInfoType.setHotelStatus(entry.getValue());
                break;
            case HOTEL_INFO_ATTRIBUTE_HOTEL_STATUS_CODE:
                hotelInfoType.setHotelStatusCode(entry.getValue());
                break;
            case HOTEL_INFO_ATTRIBUTE_TAX_ID:
                hotelInfoType.setTaxID(entry.getValue());
                break;
            case HOTEL_INFO_ATTRIBUTE_DAYLIGHT_SAVING_INDICATOR:
                hotelInfoType.setDaylightSavingIndicator(Boolean.valueOf(entry.getValue()));
                break;
            case HOTEL_INFO_ATTRIBUTE_ISO9000_CERTIFIED_IND:
                hotelInfoType.setISO9000CertifiedInd(Boolean.valueOf(entry.getValue()));
                break;
            case HOTEL_INFO_ATTRIBUTE_START:
                hotelInfoType.setStart(entry.getValue());
                break;
            case HOTEL_INFO_ATTRIBUTE_DURATION:
                hotelInfoType.setDuration(entry.getValue());
                break;
            case HOTEL_INFO_ATTRIBUTE_END:
                hotelInfoType.setEnd(entry.getValue());
                break;
            default:
                break;
        }
    }
}
