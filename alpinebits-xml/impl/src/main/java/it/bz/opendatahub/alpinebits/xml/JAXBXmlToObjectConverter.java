/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.xml;

import org.xml.sax.SAXParseException;

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

    private final Schema schema;
    private final Class<T> classToBeBound;

    private JAXBXmlToObjectConverter(Schema schema, Class<T> classToBeBound) {
        this.schema = schema;
        this.classToBeBound = classToBeBound;
    }

    @Override
    public T toObject(InputStream is) {
        try {
            Unmarshaller unmarshaller = JAXBContextSingleton.getInstance().createUnmarshaller();
            unmarshaller.setSchema(this.schema);
            return this.classToBeBound.cast(unmarshaller.unmarshal(is));
        } catch (JAXBException e) {
            String message = buildErrorMessage(e);
            throw new XmlConversionException(message, 400, e);
        }
    }

    private String buildErrorMessage(JAXBException e) {
        String message = "XML-to-object conversion error";

        if (e.getCause() instanceof SAXParseException) {
            SAXParseException se = (SAXParseException) e.getCause();
            int lineNumber = se.getLineNumber();
            int colNumber = se.getColumnNumber();
            message += " on line " + lineNumber + " and col " + colNumber + ": " + se.getMessage();
        } else {
            message += ": " + e.getMessage();
        }
        return message;
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
         */
        public XmlToObjectConverter<T> build() {
            return new JAXBXmlToObjectConverter<>(this.schema, this.classToBeBound);
        }
    }

}
