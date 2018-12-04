/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.xml.middleware.utils;

import it.bz.idm.alpinebits.common.context.ResponseContextKeys;
import it.bz.idm.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Key;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;
import it.bz.idm.alpinebits.servlet.middleware.MultipartFormDataParserMiddleware;
import it.bz.idm.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.idm.alpinebits.xml.XmlToObjectConverter;
import it.bz.idm.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.idm.alpinebits.xml.middleware.XmlRequestMappingMiddleware;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAReadRQ;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * This helper class for XmlRequestMappingMiddleware configures the middleware
 * chain needed for integration tests, where the XSD validation is active.
 */
public class XsdValidatingXmlRequestMappingMiddleware implements Middleware {

    private static final Key<OTAReadRQ> DEFAULT_CTX_KEY = Key.key("test", OTAReadRQ.class);

    private final Middleware middleware;

    public XsdValidatingXmlRequestMappingMiddleware() {
        try {
            this.middleware = ComposingMiddlewareBuilder.compose(Arrays.asList(
                    new MultipartFormDataParserMiddleware(),
                    this.validatingMiddleware()
            ));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        this.middleware.handleContext(ctx, chain);

        OTAReadRQ otaReadRQ = ctx.getOrThrow(XsdValidatingXmlRequestMappingMiddleware.DEFAULT_CTX_KEY);

        OutputStream os = ctx.getOrThrow(ResponseContextKeys.RESPONSE_CONTENT_STREAM);

        try {
            os.write(otaReadRQ.getClass().toString().getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private XmlRequestMappingMiddleware<OTAReadRQ> validatingMiddleware() throws JAXBException {
        Schema schema = XmlValidationSchemaProvider.buildXsdSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<OTAReadRQ> converter = this.validatingConverter(OTAReadRQ.class, schema);
        return new XmlRequestMappingMiddleware<>(converter, DEFAULT_CTX_KEY);
    }

    private <T> XmlToObjectConverter<T> validatingConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBXmlToObjectConverter.Builder(classToBeBound).schema(schema).build();
    }
}
