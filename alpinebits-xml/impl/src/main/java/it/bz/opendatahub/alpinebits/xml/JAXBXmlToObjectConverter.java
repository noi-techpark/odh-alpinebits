/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import java.io.InputStream;

/**
 * This class provides methods to convert XML to Java objects.
 *
 * @param <T> converted object type
 */
public final class JAXBXmlToObjectConverter<T> implements XmlToObjectConverter<T> {


    private final JAXBContext jaxbContext;
    private final Schema schema;
    private final Class<T> classToBeBound;

    private JAXBXmlToObjectConverter(JAXBContext jaxbContext, Schema schema, Class<T> classToBeBound) {
        this.jaxbContext = jaxbContext;
        this.schema = schema;
        this.classToBeBound = classToBeBound;
    }

    @Override
    public T toObject(InputStream is) {
        try {
            Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
            unmarshaller.setSchema(this.schema);
            return this.classToBeBound.cast(unmarshaller.unmarshal(is));
        } catch (JAXBException e) {
            throw new XmlConversionException("XML-to-object conversion error", 400, e);
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
         * Build an instance of {@link JAXBObjectToXmlConverter} with
         * the current configuration.
         *
         * @return instance of {@link JAXBObjectToXmlConverter}
         * @throws JAXBException if there went something wrong during
         *                       the creation of the {@link JAXBObjectToXmlConverter} instance
         */
        public XmlToObjectConverter<T> build() throws JAXBException {
            JAXBContext jaxbContext = JAXBContext.newInstance(this.classToBeBound);
            return new JAXBXmlToObjectConverter<>(jaxbContext, this.schema, this.classToBeBound);
        }
    }

}
