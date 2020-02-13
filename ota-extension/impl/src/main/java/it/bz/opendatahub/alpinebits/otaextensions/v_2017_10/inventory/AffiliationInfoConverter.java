/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.otaextensions.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.AffiliationInfoType;
import it.bz.opendatahub.alpinebits.otaextensions.ConverterUtil;
import it.bz.opendatahub.alpinebits.otaextensions.exception.OtaExtensionException;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ;
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
 * specialized on OTA AffilliationInfo.
 * <p>
 * The provided converter facilitates the conversion between the generic JAXB classes
 * generated for AlpineBits at the given extension points and OTA classes.
 * <p>
 * The AlpineBits 2017-10 inventory actions specify, that any valid OTA data can be used at
 * the following positions as an extension:
 * <ul>
 * <li>OTAHotelDescriptiveContentNotifRQ {@literal ->} HotelDescriptiveContents {@literal ->} HotelDescriptiveContent {@literal ->} AffiliationInfo</li>
 * <li>OTAHotelDescriptiveContentNotifRQ {@literal ->} HotelDescriptiveContents {@literal ->} HotelDescriptiveContent {@literal ->} ContactInfos</li>
 * <li>OTAHotelDescriptiveContentNotifRQ {@literal ->} HotelDescriptiveContents {@literal ->} HotelDescriptiveContent {@literal ->} HotelInfo</li>
 * <li>OTAHotelDescriptiveContentNotifRQ {@literal ->} HotelDescriptiveContents {@literal ->} HotelDescriptiveContent {@literal ->} Policies</li>
 * </ul>
 *
 * @see ContactInfosConverter
 * @see HotelInfoConverter
 * @see PoliciesConverter
 */
public final class AffiliationInfoConverter {

    private static final String AFFILIATION_INFO = "AffiliationInfo";

    private static final String AFFILIATION_INFO_ATTRIBUTE_LAST_UPDATED = "LastUpdated";
    private static final String AFFILIATION_INFO_ELEMENT_AWARDS = "Awards";
    private static final String AFFILIATION_INFO_ELEMENT_BRANDS = "Brands";
    private static final String AFFILIATION_INFO_ELEMENT_DESCRIPTIONS = "Descriptions";
    private static final String AFFILIATION_INFO_ELEMENT_DISTRIB_SYSTEMS = "DistribSystems";
    private static final String AFFILIATION_INFO_ELEMENT_LOYAL_PROGRAMS = "LoyalPrograms";
    private static final String AFFILIATION_INFO_ELEMENT_PARTNER_INFOS = "PartnerInfos";

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    private AffiliationInfoConverter(Marshaller marshaller, Unmarshaller unmarshaller) {
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
    public static AffiliationInfoConverter newInstance() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(
                    AffiliationInfoType.class
            );
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Marshaller marshaller = jaxbContext.createMarshaller();
            return new AffiliationInfoConverter(marshaller, unmarshaller);
        } catch (JAXBException e) {
            throw new OtaExtensionException("Error during converter initialization", e);
        }
    }

    /**
     * Convert an AlpineBits AffiliationInfo element to an OTA {@link AffiliationInfoType}.
     * <p>
     * The AlpineBits AffiliationInfo has an <code>any</code> type, that allows any OTA
     * content valid for this position according to the specification.
     * <p>
     * This method converts the content of the AlpineBits AffiliationInfo into
     * corresponding OTA types.
     *
     * @param hotelDescriptiveContent the OTA HotelDescriptiveContent that contains affiliation info
     * @return the converted {@link AffiliationInfoType} or null if the hotelDescriptiveContent is null
     * or its affiliationInfo is null or empty
     * @throws OtaExtensionException if there was an error during the conversion
     */
    public AffiliationInfoType toAffiliationInfoType(OTAHotelDescriptiveContentNotifRQ
                                                             .HotelDescriptiveContents
                                                             .HotelDescriptiveContent hotelDescriptiveContent) {
        if (this.isAffiliationInfoEmpty(hotelDescriptiveContent)) {
            return null;
        }

        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.AffiliationInfo affiliationInfo =
                hotelDescriptiveContent.getAffiliationInfo();

        AffiliationInfoType affiliationInfoType = new AffiliationInfoType();

        List<Element> anies = affiliationInfo.getAnies();
        if (anies != null) {
            try {
                for (Element element : anies) {
                    this.handleElement(element, affiliationInfoType);
                }
            } catch (JAXBException e) {
                throw new OtaExtensionException("Error during unmarshalling of OTA extensions", e);
            }
        }

        Map<QName, String> attributeMap = hotelDescriptiveContent.getAffiliationInfo().getOtherAttributes();
        if (attributeMap != null) {
            for (Map.Entry<QName, String> entry : attributeMap.entrySet()) {
                this.handleAttribute(entry, affiliationInfoType);
            }
        }

        return affiliationInfoType;
    }

    /**
     * Apply the {@link AffiliationInfoType} to the HotelDescriptiveContent.
     * <p>
     * The method first tries to convert the provided affiliationInfoType to XML
     * elements using JAXB. Then it adds those elements to the hotelDescriptiveContent.
     * <p>
     * If either of the input params are null, no modification will take place.
     *
     * @param hotelDescriptiveContent apply the {@link AffiliationInfoType} to this hotelDescriptiveContent
     * @param affiliationInfoType     apply this {@link AffiliationInfoType} to the inventory
     * @throws OtaExtensionException if there was an error during the conversion of the
     *                               provided {@link AffiliationInfoType}
     */
    public void applyAffiliationInfo(
            OTAHotelDescriptiveContentNotifRQ
                    .HotelDescriptiveContents
                    .HotelDescriptiveContent hotelDescriptiveContent,
            AffiliationInfoType affiliationInfoType
    ) {
        if (hotelDescriptiveContent == null || affiliationInfoType == null) {
            return;
        }

        try {
            Element elt = ConverterUtil.marshallToElement(marshaller, AFFILIATION_INFO, AffiliationInfoType.class, affiliationInfoType);

            OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.AffiliationInfo affiliationInfo =
                    new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.AffiliationInfo();
            hotelDescriptiveContent.setAffiliationInfo(affiliationInfo);

            List<Element> elements = ConverterUtil.convertToElements(elt.getChildNodes());
            affiliationInfo.getAnies().addAll(elements);

            Map<QName, String> attributeMap = ConverterUtil.convertToAttributeMap(elt.getAttributes());
            affiliationInfo.getOtherAttributes().putAll(attributeMap);
        } catch (JAXBException e) {
            throw new OtaExtensionException("Error during marshalling of OTA extensions", e);
        }
    }

    private boolean isAffiliationInfoEmpty(OTAHotelDescriptiveContentNotifRQ
                                                   .HotelDescriptiveContents
                                                   .HotelDescriptiveContent hotelDescriptiveContent) {
        if (hotelDescriptiveContent == null) {
            return true;
        }

        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.AffiliationInfo affiliationInfo =
                hotelDescriptiveContent.getAffiliationInfo();

        return affiliationInfo == null
                || ConverterUtil.isNodeEmpty(affiliationInfo.getAnies(), affiliationInfo.getOtherAttributes());
    }

    private void handleElement(Element element, AffiliationInfoType affiliationInfoType) throws JAXBException {
        switch (element.getTagName()) {
            case AFFILIATION_INFO_ELEMENT_AWARDS:
                AffiliationInfoType.Awards awards = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        AffiliationInfoType.Awards.class
                );
                affiliationInfoType.setAwards(awards);
                break;
            case AFFILIATION_INFO_ELEMENT_BRANDS:
                AffiliationInfoType.Brands brands = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        AffiliationInfoType.Brands.class
                );
                affiliationInfoType.setBrands(brands);
                break;
            case AFFILIATION_INFO_ELEMENT_DISTRIB_SYSTEMS:
                AffiliationInfoType.DistribSystems distribSystems = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        AffiliationInfoType.DistribSystems.class
                );
                affiliationInfoType.setDistribSystems(distribSystems);
                break;
            case AFFILIATION_INFO_ELEMENT_LOYAL_PROGRAMS:
                AffiliationInfoType.LoyalPrograms loyalPrograms = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        AffiliationInfoType.LoyalPrograms.class
                );
                affiliationInfoType.setLoyalPrograms(loyalPrograms);
                break;
            case AFFILIATION_INFO_ELEMENT_PARTNER_INFOS:
                AffiliationInfoType.PartnerInfos partnerInfos = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        AffiliationInfoType.PartnerInfos.class
                );
                affiliationInfoType.setPartnerInfos(partnerInfos);
                break;
            case AFFILIATION_INFO_ELEMENT_DESCRIPTIONS:
                AffiliationInfoType.Descriptions descriptions = ConverterUtil.unmarshallElement(
                        unmarshaller,
                        element,
                        AffiliationInfoType.Descriptions.class
                );
                affiliationInfoType.setDescriptions(descriptions);
                break;
            default:
                break;
        }
    }

    private void handleAttribute(Map.Entry<QName, String> entry, AffiliationInfoType affiliationInfoType) {
        if (AFFILIATION_INFO_ATTRIBUTE_LAST_UPDATED.equals(entry.getKey().getLocalPart())) {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(entry.getValue());
            affiliationInfoType.setLastUpdated(zonedDateTime);
        }
    }
}
