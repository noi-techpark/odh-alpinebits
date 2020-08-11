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
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;

import java.io.OutputStream;

/**
 * This middleware converts the response data from the {@link Context} into
 * XML and writes the resulting XML to the {@link OutputStream}, defined
 * by {@link ResponseContextKeys#RESPONSE_CONTENT_STREAM} context key.
 * <p>
 * The converter as well as the context key used as identifier for the POJO
 * inside the context, are defined by the constructor.
 *
 * @param <T> response data type
 */
public class XmlResponseMappingMiddleware<T> implements Middleware {

    private final ObjectToXmlConverter converter;
    private final Key<T> responseDataCtxKey;

    public XmlResponseMappingMiddleware(
            ObjectToXmlConverter converter,
            Key<T> responseDataCtxKey
    ) {
        if (converter == null) {
            throw new IllegalArgumentException("The object-to-XML converter must not be null");
        }
        if (responseDataCtxKey == null) {
            throw new IllegalArgumentException("The response data context key must not be null");
        }

        this.converter = converter;
        this.responseDataCtxKey = responseDataCtxKey;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        chain.next();

        T responseData = ctx.getOrThrow(this.responseDataCtxKey);
        OutputStream os = ctx.getOrThrow(ResponseContextKeys.RESPONSE_CONTENT_STREAM);

        this.converter.toXml(responseData, os);
        ctx.put(ResponseContextKeys.RESPONSE_CONTENT_TYPE_HINT, HttpContentTypeHeaderValues.TEXT_XML);
    }

}
