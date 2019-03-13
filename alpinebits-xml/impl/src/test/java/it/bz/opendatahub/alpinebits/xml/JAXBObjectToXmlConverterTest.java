/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.xml;

import it.bz.opendatahub.alpinebits.xml.entity.TestEntity;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.testng.Assert.assertTrue;

/**
 * Test cases for {@link JAXBObjectToXmlConverter} class.
 */
public class JAXBObjectToXmlConverterTest {

    @Test(expectedExceptions = XmlConversionException.class)
    public void testToXml_Error() throws Exception {
        TestEntity testEntity = new TestEntity();
        testEntity.setName("John Doe");
        testEntity.setAge(23);

        OutputStream os = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException("Throw error while writing");
            }
        };

        ObjectToXmlConverter<TestEntity> converter = new JAXBObjectToXmlConverter.Builder(TestEntity.class).prettyPrint(true).build();
        converter.toXml(testEntity, os);
    }

    @Test
    public void testToXml_Ok() throws Exception {
        TestEntity testEntity = new TestEntity();
        testEntity.setName("John Doe");
        testEntity.setAge(23);

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        ObjectToXmlConverter<TestEntity> converter = new JAXBObjectToXmlConverter.Builder(TestEntity.class).prettyPrint(true).build();
        converter.toXml(testEntity, os);

        assertTrue(os.size() > 0);
    }
}