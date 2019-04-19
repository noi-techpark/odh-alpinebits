/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.otaextensions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides utilities for OTA extension conversion.
 */
public final class ConverterUtil {

    private ConverterUtil() {
        // Empty
    }

    /**
     * Convert the given {@link NodeList} to a list of elements.
     * <p>
     * Only nodes of type {@link Node#ELEMENT_NODE} are converted.
     *
     * @param nodeList convert this list to a list of elements.
     * @return a list of elements, extracted from the given nodeList,
     * containing only nodes of type {@link Node#ELEMENT_NODE}. If the
     * node list is <code>null</code>, an empty element list is returned
     */
    public static List<Element> convertToElements(NodeList nodeList) {
        List<Element> elements = new ArrayList<>();

        if (nodeList == null) {
            return elements;
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                elements.add((Element) node);
            }
        }

        return elements;
    }

    /**
     * Convert the given {@link NamedNodeMap} to an attribute map.
     * <p>
     * Only nodes of type {@link Node#ATTRIBUTE_NODE} are converted.
     *
     * @param namedNodeMap a map of nodes that can be accessed by name
     * @return a map of qualified names to String values
     */
    public static Map<QName, String> convertToAttributeMap(NamedNodeMap namedNodeMap) {
        Map<QName, String> attributeMap = new HashMap<>();

        if (namedNodeMap == null) {
            return attributeMap;
        }

        for (int i = 0; i < namedNodeMap.getLength(); i++) {
            Node node = namedNodeMap.item(i);
            if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
                attributeMap.put(
                        QName.valueOf(node.getNodeName()),
                        node.getNodeValue()
                );

            }
        }

        return attributeMap;
    }

    /**
     * Unmarshal the given {@link Element}.
     *
     * @param unmarshaller  the unmarshaller to use
     * @param element       the element to unmarshal
     * @param expectedClass the expected class of the result
     * @param <T>           the result type
     * @return the unmarshalled element
     * @throws JAXBException if there was an error during conversion
     */
    public static <T> T unmarshallElement(
            Unmarshaller unmarshaller,
            Element element,
            Class<T> expectedClass
    ) throws JAXBException {
        JAXBElement<T> jaxbElement = unmarshaller.unmarshal(element, expectedClass);
        return jaxbElement.getValue();
    }

    /**
     * Marshall the given value to {@link Element}.
     *
     * @param marshaller   the {@link Marshaller} to use
     * @param elementName  the resulting element name
     * @param elementClass the resulting element class
     * @param value        the value to marshall
     * @param <T>          the type of the values
     * @return the value converted to an {@link Element}
     * @throws JAXBException if there was an error during conversion
     */
    public static <T> Element marshallToElement(
            Marshaller marshaller,
            String elementName,
            Class<T> elementClass,
            T value
    ) throws JAXBException {
        DOMResult res = new DOMResult();

        marshaller.marshal(
                new JAXBElement<>(
                        new QName(OTAConstants.OTA_NAMESPACE, elementName),
                        elementClass,
                        value
                ),
                res
        );

        return ((Document) res.getNode()).getDocumentElement();
    }

    /**
     * Check if both anies and otherAttributes are null or empty.
     *
     * @param anies a list of elements
     * @param attributeMap a map of attributes
     * @return true is both the element list and attribute map are null or empty
     */
    public static boolean isNodeEmpty(List<Element> anies, Map<QName, String> attributeMap) {
        // Check if both anies and otherAttributes are null or empty.
        return (anies == null || anies.isEmpty())
                && (attributeMap == null || attributeMap.isEmpty());
    }
}
