// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.xml.middleware;

import it.bz.opendatahub.alpinebits.common.constants.HttpContentTypeHeaderValues;
import it.bz.opendatahub.alpinebits.common.context.ResponseContextKeys;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.RequiredContextKeyMissingException;
import it.bz.opendatahub.alpinebits.middleware.impl.SimpleContext;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlConversionException;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAResRetrieveRS;
import org.testng.annotations.Test;

import javax.xml.validation.Schema;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Test cases for {@link XmlResponseMappingMiddleware} class.
 */
public class XmlResponseMappingMiddlewareTest {

    private static final Key<OTAResRetrieveRS> DEFAULT_CTX_KEY = Key.key("test", OTAResRetrieveRS.class);

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructor_ConverterIsNull() {
        new XmlResponseMappingMiddleware<>(null, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructor_BusinessContextKeyIsNull() {
        ObjectToXmlConverter converter = this.notValidatingConverter();
        new XmlResponseMappingMiddleware<>(converter, null);
    }

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testHandleContext_ResponseDataIsNull() {
        Middleware middleware = this.notValidatingMiddleware();
        Context ctx = new SimpleContext();
        middleware.handleContext(ctx, () -> {
        });
    }

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testHandleContext_ResponseContentStreamIsNull() {
        Middleware middleware = this.notValidatingMiddleware();
        Context ctx = new SimpleContext();
        ctx.put(DEFAULT_CTX_KEY, new OTAResRetrieveRS());
        middleware.handleContext(ctx, () -> {
        });
    }

    @Test
    public void testHandleContext_NoValidation() {
        Context ctx = this.getDefaultCtx();

        Middleware middleware = this.notValidatingMiddleware();
        middleware.handleContext(ctx, () -> {
        });

        OTAResRetrieveRS businessData = ctx.getOrThrow(DEFAULT_CTX_KEY);
        assertNotNull(businessData);
        assertEquals(ctx.getOrThrow(ResponseContextKeys.RESPONSE_CONTENT_TYPE_HINT), HttpContentTypeHeaderValues.TEXT_XML);
    }

    @Test(expectedExceptions = XmlConversionException.class)
    public void testHandleContext_RngValidationError() {
        Context ctx = this.getDefaultCtx();

        ctx.getOrThrow(DEFAULT_CTX_KEY).setVersion(null);
        Middleware middleware = this.validatingMiddleware(XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10"));
        middleware.handleContext(ctx, () -> {
        });
        assertEquals(ctx.getOrThrow(ResponseContextKeys.RESPONSE_CONTENT_TYPE_HINT), HttpContentTypeHeaderValues.TEXT_XML);
    }

    @Test
    public void testHandleContext_RngValidationOk() {
        Context ctx = this.getDefaultCtx();

        Middleware middleware = this.validatingMiddleware(XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10"));
        middleware.handleContext(ctx, () -> {
        });
        assertEquals(ctx.getOrThrow(ResponseContextKeys.RESPONSE_CONTENT_TYPE_HINT), HttpContentTypeHeaderValues.TEXT_XML);
    }

    @Test(expectedExceptions = XmlConversionException.class)
    public void testHandleContext_XsdValidationError() {
        Context ctx = this.getDefaultCtx();

        ctx.getOrThrow(DEFAULT_CTX_KEY).setVersion(null);
        Middleware middleware = this.validatingMiddleware(XmlValidationSchemaProvider.buildXsdSchemaForAlpineBitsVersion("2017-10"));
        middleware.handleContext(ctx, () -> {
        });
        assertEquals(ctx.getOrThrow(ResponseContextKeys.RESPONSE_CONTENT_TYPE_HINT), HttpContentTypeHeaderValues.TEXT_XML);
    }

    @Test
    public void testHandleContext_XsdValidationOk() {
        Context ctx = this.getDefaultCtx();

        Middleware middleware = this.validatingMiddleware(XmlValidationSchemaProvider.buildXsdSchemaForAlpineBitsVersion("2017-10"));
        middleware.handleContext(ctx, () -> {
        });
        assertEquals(ctx.getOrThrow(ResponseContextKeys.RESPONSE_CONTENT_TYPE_HINT), HttpContentTypeHeaderValues.TEXT_XML);
    }

    private Context getDefaultCtx() {
        Context ctx = new SimpleContext();
        OutputStream os = new ByteArrayOutputStream();
        ctx.put(ResponseContextKeys.RESPONSE_CONTENT_STREAM, os);

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("examples/v_2017_10/GuestRequests-OTA_ResRetrieveRS-reservation.xml");
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTAResRetrieveRS> converter = new JAXBXmlToObjectConverter.Builder<>(OTAResRetrieveRS.class).schema(schema).build();
        OTAResRetrieveRS responseData = converter.toObject(is);
        ctx.put(DEFAULT_CTX_KEY, responseData);
        return ctx;
    }

    private XmlResponseMappingMiddleware<OTAResRetrieveRS> notValidatingMiddleware() {
        ObjectToXmlConverter converter = this.notValidatingConverter();
        return new XmlResponseMappingMiddleware<>(converter, DEFAULT_CTX_KEY);
    }

    private XmlResponseMappingMiddleware<OTAResRetrieveRS> validatingMiddleware(Schema schema) {
        ObjectToXmlConverter converter = this.validatingConverter(schema);
        return new XmlResponseMappingMiddleware<>(converter, DEFAULT_CTX_KEY);
    }

    private ObjectToXmlConverter notValidatingConverter() {
        return new JAXBObjectToXmlConverter.Builder().build();
    }

    private ObjectToXmlConverter validatingConverter(Schema schema) {
        return new JAXBObjectToXmlConverter.Builder().schema(schema).build();
    }

}