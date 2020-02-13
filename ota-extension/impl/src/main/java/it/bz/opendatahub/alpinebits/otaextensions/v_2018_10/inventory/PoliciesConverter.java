/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.otaextensions.v_2018_10.inventory;

import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelDescriptiveContentType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.PoliciesType;
import it.bz.opendatahub.alpinebits.otaextensions.ConverterUtil;
import it.bz.opendatahub.alpinebits.otaextensions.exception.OtaExtensionException;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;

/**
 * This class implements an OTA extension converter for AlpineBits inventory actions,
 * specialized on OTA Policies.
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
 * @see HotelInfoConverter
 */
public final class PoliciesConverter {

    private static final String POLICIES = "Policies";

    private static final String POLICIES_ATTRIBUTE_GUARANTEE_ROOM_TYPE_VIA_CRC = "GuaranteeRoomTypeViaCRC";
    private static final String POLICIES_ATTRIBUTE_GUARANTEE_ROOM_TYPE_VIA_GDS = "GuaranteeRoomTypeViaGDS";
    private static final String POLICIES_ATTRIBUTE_GUARANTEE_ROOM_TYPE_VIA_PROPERTY = "GuaranteeRoomTypeViaProperty";
    private static final String POLICIES_ELEMENT_POLICY = "Policy";

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    private PoliciesConverter(Marshaller marshaller, Unmarshaller unmarshaller) {
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
    public static PoliciesConverter newInstance() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(
                    HotelDescriptiveContentType.Policies.class
            );
            Marshaller marshaller = jaxbContext.createMarshaller();
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return new PoliciesConverter(marshaller, unmarshaller);
        } catch (JAXBException e) {
            throw new OtaExtensionException("Error during converter initialization", e);
        }

    }

    /**
     * Convert an AlpineBits Policies element to an OTA Policies.
     * <p>
     * The AlpineBits Policies has an <code>any</code> type, that allows any OTA
     * content valid for this position according to the specification.
     * <p>
     * This method converts the content of the AlpineBits Policies into
     * corresponding OTA types.
     *
     * @param hotelDescriptiveContent the OTA HotelDescriptiveContent that contains policies
     * @return the converted Policies or null if the hotelDescriptiveContent is null
     * or its policies is null or empty
     * @throws OtaExtensionException if there was an error during the conversion
     */
    public HotelDescriptiveContentType.Policies toPolicies(OTAHotelDescriptiveContentNotifRQ
                                                                   .HotelDescriptiveContents
                                                                   .HotelDescriptiveContent hotelDescriptiveContent) {
        if (this.isPoliciesEmpty(hotelDescriptiveContent)) {
            return null;
        }

        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.Policies policies =
                hotelDescriptiveContent.getPolicies();

        HotelDescriptiveContentType.Policies otaPolicies = new HotelDescriptiveContentType.Policies();

        List<Element> anies = policies.getAnies();
        if (anies != null) {
            try {
                for (Element element : anies) {
                    this.handleElement(element, otaPolicies);
                }
            } catch (JAXBException e) {
                throw new OtaExtensionException("Error during unmarshalling of OTA extensions", e);
            }
        }

        for (Map.Entry<QName, String> entry : policies.getOtherAttributes().entrySet()) {
            this.handleAttribute(entry, otaPolicies);
        }

        return otaPolicies;
    }

    /**
     * Apply the Policies to the HotelDescriptiveContent.
     * <p>
     * The method first tries to convert the provided policies to XML
     * elements using JAXB. Then it adds the elements to the hotelDescriptiveContent.
     * <p>
     * If either of the input params are null, no modification will take place.
     *
     * @param hotelDescriptiveContent apply the Policies to this hotelDescriptiveContent
     * @param policiesType            apply this Policies to the hotelDescriptiveContent
     * @throws OtaExtensionException if there was an error during the conversion of the
     *                               provided Policies
     */
    public void applyPolicies(
            OTAHotelDescriptiveContentNotifRQ
                    .HotelDescriptiveContents
                    .HotelDescriptiveContent hotelDescriptiveContent,
            HotelDescriptiveContentType.Policies policiesType
    ) {
        if (hotelDescriptiveContent == null || policiesType == null) {
            return;
        }

        try {
            Element elt = ConverterUtil.marshallToElement(marshaller, POLICIES, HotelDescriptiveContentType.Policies.class, policiesType);

            OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.Policies policies =
                    new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.Policies();
            hotelDescriptiveContent.setPolicies(policies);

            List<Element> elements = ConverterUtil.convertToElements(elt.getChildNodes());
            policies.getAnies().addAll(elements);

            Map<QName, String> attributeMap = ConverterUtil.convertToAttributeMap(elt.getAttributes());
            policies.getOtherAttributes().putAll(attributeMap);
        } catch (JAXBException e) {
            throw new OtaExtensionException("Error during marshalling of OTA extensions", e);
        }
    }

    private boolean isPoliciesEmpty(OTAHotelDescriptiveContentNotifRQ
                                               .HotelDescriptiveContents
                                               .HotelDescriptiveContent hotelDescriptiveContent) {
        if (hotelDescriptiveContent == null) {
            return true;
        }

        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.Policies policies =
                hotelDescriptiveContent.getPolicies();

        return policies == null
                || ConverterUtil.isNodeEmpty(policies.getAnies(), policies.getOtherAttributes());
    }

    private void handleElement(Element element, HotelDescriptiveContentType.Policies policies) throws JAXBException {
        if (POLICIES_ELEMENT_POLICY.equals(element.getTagName())) {
            JAXBElement<PoliciesType.Policy> jaxbElement = unmarshaller
                    .unmarshal(element, PoliciesType.Policy.class);
            policies.getPolicies().add(jaxbElement.getValue());
        }
    }

    private void handleAttribute(Map.Entry<QName, String> entry, HotelDescriptiveContentType.Policies otaPolicies) {
        switch (entry.getKey().getLocalPart()) {
            case POLICIES_ATTRIBUTE_GUARANTEE_ROOM_TYPE_VIA_CRC:
                otaPolicies.setGuaranteeRoomTypeViaCRC(Boolean.parseBoolean(entry.getValue()));
                break;
            case POLICIES_ATTRIBUTE_GUARANTEE_ROOM_TYPE_VIA_GDS:
                otaPolicies.setGuaranteeRoomTypeViaGDS(Boolean.parseBoolean(entry.getValue()));
                break;
            case POLICIES_ATTRIBUTE_GUARANTEE_ROOM_TYPE_VIA_PROPERTY:
                otaPolicies.setGuaranteeRoomTypeViaProperty(Boolean.parseBoolean(entry.getValue()));
                break;
            default:
                break;
        }
    }
}
