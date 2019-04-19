/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import java.io.OutputStream;

/**
 * This class provides methods to convert Java objects to XML.
 *
 * @param <T> object type
 */
public final class JAXBObjectToXmlConverter<T> implements ObjectToXmlConverter<T> {

    private final JAXBContext jaxbContext;
    private final Schema schema;
    private final boolean doPrettyPrintXml;

    private JAXBObjectToXmlConverter(JAXBContext jaxbContext, Schema schema, boolean doPrettyPrintXml) {
        this.jaxbContext = jaxbContext;
        this.schema = schema;
        this.doPrettyPrintXml = doPrettyPrintXml;
    }

    @Override
    public void toXml(T objectToConvert, OutputStream os) {
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setSchema(this.schema);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, this.doPrettyPrintXml);
            marshaller.marshal(objectToConvert, os);
        } catch (JAXBException e) {
            throw new XmlConversionException("Object-to-XML conversion error", e);
        }
    }

    /**
     * Builder to create instances of {@link JAXBObjectToXmlConverter}.
     *
     * @param <T> target type when converting XML to object
     */
    public static class Builder<T> {

        private final Class<T> classToBeBound;

        private Schema schema;
        private boolean doPrettyPrintXml;

        public Builder(Class<T> classToBeBound) {
            this.classToBeBound = classToBeBound;
        }

        /**
         * The {@link Schema} is used for XML validation.
         * <p>
         * If the schema is null (default: null), no validation will
         * be performed.
         *
         * @param schema the {@link Schema} used for XML validation
         * @return the current Builder
         */
        public Builder<T> schema(Schema schema) {
            this.schema = schema;
            return this;
        }

        /**
         * Configure, if the resulting XML should be pretty printed
         * (default: false).
         *
         * @param doPrettyPrintXml if <code>true</code>, the resuling
         *                         XML will be pretty printed
         * @return the current Builder
         */
        public Builder<T> prettyPrint(boolean doPrettyPrintXml) {
            this.doPrettyPrintXml = doPrettyPrintXml;
            return this;
        }

        /**
         * Build an instance of {@link JAXBObjectToXmlConverter} with
         * the current configuration.
         *
         * @return instance of {@link JAXBObjectToXmlConverter}
         * @throws JAXBException if there went something wrong during
         *                       the creation of the {@link JAXBObjectToXmlConverter} instance
         */
        public ObjectToXmlConverter<T> build() throws JAXBException {
            JAXBContext jaxbContext = JAXBContext.newInstance(classToBeBound);
            return new JAXBObjectToXmlConverter<>(jaxbContext, this.schema, this.doPrettyPrintXml);
        }
    }
}
