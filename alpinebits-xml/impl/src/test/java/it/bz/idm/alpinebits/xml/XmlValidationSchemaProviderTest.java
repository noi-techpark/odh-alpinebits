/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.xml;

import org.testng.annotations.Test;

import javax.xml.validation.Schema;

import static org.testng.Assert.assertNotNull;

/**
 * Test cases for {@link XmlValidationSchemaProvider} class.
 */
public class XmlValidationSchemaProviderTest {

    private static final String UNKNOWN_FILENAME = "unknown-filename";

    @Test(expectedExceptions = InvalidSchemaException.class)
    public void testBuildRngSchema_FileNotFound() {
        XmlValidationSchemaProvider.buildRngSchema(UNKNOWN_FILENAME);
    }

    @Test(expectedExceptions = InvalidSchemaException.class)
    public void testBuildRngSchema_InvalidSchema() {
        XmlValidationSchemaProvider.buildRngSchema("schema/alpinebits-2017-10-invalid.rng");
    }

    @Test
    public void testBuildRngSchema_ValidSchema() {
        Schema schema = XmlValidationSchemaProvider.buildRngSchema("alpinebits-2017-10.rng");
        assertNotNull(schema);
    }

    @Test(expectedExceptions = InvalidSchemaException.class)
    public void testBuildXsdSchema_FileNotFound() {
        XmlValidationSchemaProvider.buildXsdSchema(UNKNOWN_FILENAME);
    }

    @Test(expectedExceptions = InvalidSchemaException.class)
    public void testBuildXsdSchema_InvalidSchema() {
        XmlValidationSchemaProvider.buildXsdSchema("schema/alpinebits-2017-10-invalid.xsd");
    }

    @Test
    public void testBuildXsdSchema_ValidSchema() {
        Schema schema = XmlValidationSchemaProvider.buildXsdSchema("alpinebits-2017-10.xsd");
        assertNotNull(schema);
    }
}