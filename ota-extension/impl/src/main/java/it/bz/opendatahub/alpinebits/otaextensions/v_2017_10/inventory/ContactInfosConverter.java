/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.otaextensions.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfoRootType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfosType;
import it.bz.opendatahub.alpinebits.otaextensions.ConverterUtil;
import it.bz.opendatahub.alpinebits.otaextensions.exception.OtaExtensionException;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ;
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
 * specialized on OTA ContactInfos.
 * <p>
 * The provided converter facilitates the conversion between the generic JAXB classes
 * generated for AlpineBits at the given extension points and OTA classes.
 * <p>
 * The AlpineBits inventory actions specify, that any valid OTA data can be used at
 * the following positions as an extension:
 * <ul>
 * <li>OTAHotelDescriptiveContentNotifRQ {@literal ->} HotelDescriptiveContents {@literal ->} HotelDescriptiveContent {@literal ->} AffiliationInfo</li>
 * <li>OTAHotelDescriptiveContentNotifRQ {@literal ->} HotelDescriptiveContents {@literal ->} HotelDescriptiveContent {@literal ->} ContactInfos</li>
 * <li>OTAHotelDescriptiveContentNotifRQ {@literal ->} HotelDescriptiveContents {@literal ->} HotelDescriptiveContent {@literal ->} HotelInfo</li>
 * <li>OTAHotelDescriptiveContentNotifRQ {@literal ->} HotelDescriptiveContents {@literal ->} HotelDescriptiveContent {@literal ->} Policies</li>
 * </ul>
 *
 * @see AffiliationInfoConverter
 * @see HotelInfoConverter
 * @see PoliciesConverter
 */
public final class ContactInfosConverter {

    private static final String CONTACT_INFO = "ContactInfo";

    private static final String CONTACT_INFOS_ELEMENT_CONTACT_INFO = "ContactInfo";

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    private ContactInfosConverter(Marshaller marshaller, Unmarshaller unmarshaller) {
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
    public static ContactInfosConverter newInstance() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(
                    ContactInfoRootType.class
            );
            Marshaller marshaller = jaxbContext.createMarshaller();
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return new ContactInfosConverter(marshaller, unmarshaller);
        } catch (JAXBException e) {
            throw new OtaExtensionException("Error during converter initialization", e);
        }
    }

    /**
     * Convert an AlpineBits {@link OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos}
     * element to an OTA {@link ContactInfosType}.
     * <p>
     * The AlpineBits ContactInfosType has an <code>any</code> type, that allows any OTA
     * content valid for this position according to the specification.
     * <p>
     * This method converts the content of the AlpineBits ContactInfosType into
     * corresponding OTA types.
     *
     * @param hotelDescriptiveContent the OTA {@link OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent}
     *                                that contains contact info
     * @return the converted {@link ContactInfosType} or null if the hotelDescriptiveContent is null
     * or its contactInfos is null or empty
     * @throws OtaExtensionException if there was an error during the conversion
     */
    public ContactInfosType toContactInfosType(OTAHotelDescriptiveContentNotifRQ
                                                       .HotelDescriptiveContents
                                                       .HotelDescriptiveContent hotelDescriptiveContent) {
        if (this.isContactInfoEmpty(hotelDescriptiveContent)) {
            return null;
        }

        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos contactInfos =
                hotelDescriptiveContent.getContactInfos();

        ContactInfosType contactInfosType = new ContactInfosType();

        List<Element> anies = contactInfos.getAnies();
        if (anies != null) {
            try {
                for (Element element : anies) {
                    this.handleElement(element, contactInfosType);
                }
            } catch (JAXBException e) {
                throw new OtaExtensionException("Error during unmarshalling of OTA extensions", e);
            }
        }

        return contactInfosType;
    }

    /**
     * Apply the {@link ContactInfosType} to the {@link OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent}.
     * <p>
     * The method first tries to convert the provided contactInfosType to XML
     * elements using JAXB. Then it adds those elements to the hotelDescriptiveContent.
     * <p>
     * If either of the input params are null, no modification will take place.
     *
     * @param hotelDescriptiveContent apply the {@link ContactInfosType} to this hotelDescriptiveContent
     * @param contactInfosType        apply this {@link ContactInfosType} to the
     *                                hotelDescriptiveContent
     * @throws OtaExtensionException if there was an error during the conversion of the
     *                               provided {@link ContactInfosType}
     */
    public void applyContactInfo(
            OTAHotelDescriptiveContentNotifRQ
                    .HotelDescriptiveContents
                    .HotelDescriptiveContent hotelDescriptiveContent,
            ContactInfosType contactInfosType
    ) {
        if (hotelDescriptiveContent == null || contactInfosType == null) {
            return;
        }

        try {
            Element elt = ConverterUtil.marshallToElement(marshaller, CONTACT_INFO, ContactInfosType.class, contactInfosType);

            OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos contactInfos =
                    new OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos();
            hotelDescriptiveContent.setContactInfos(contactInfos);

            List<Element> elements = ConverterUtil.convertToElements(elt.getChildNodes());
            contactInfos.getAnies().addAll(elements);

            Map<QName, String> attributeMap = ConverterUtil.convertToAttributeMap(elt.getAttributes());
            contactInfos.getOtherAttributes().putAll(attributeMap);
        } catch (JAXBException e) {
            throw new OtaExtensionException("Error during marshalling of OTA extensions", e);
        }
    }

    private boolean isContactInfoEmpty(OTAHotelDescriptiveContentNotifRQ
                                               .HotelDescriptiveContents
                                               .HotelDescriptiveContent hotelDescriptiveContent) {
        if (hotelDescriptiveContent == null) {
            return true;
        }

        OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.ContactInfos contactInfos =
                hotelDescriptiveContent.getContactInfos();

        return contactInfos == null
                || ConverterUtil.isNodeEmpty(contactInfos.getAnies(), contactInfos.getOtherAttributes());
    }

    private void handleElement(Element element, ContactInfosType contactInfosType) throws JAXBException {
        if (CONTACT_INFOS_ELEMENT_CONTACT_INFO.equals(element.getTagName())) {
            JAXBElement<ContactInfoRootType> jaxbElement = unmarshaller
                    .unmarshal(element, ContactInfoRootType.class);
            contactInfosType.getContactInfos().add(jaxbElement.getValue());
        }
    }

}
