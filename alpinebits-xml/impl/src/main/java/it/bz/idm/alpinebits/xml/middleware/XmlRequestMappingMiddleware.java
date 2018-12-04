/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.xml.middleware;

import it.bz.idm.alpinebits.common.context.RequestContextKey;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Key;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;
import it.bz.idm.alpinebits.xml.XmlToObjectConverter;

import java.io.InputStream;

/**
 * This middleware uses the {@link RequestContextKey#REQUEST_CONTENT_STREAM}, taken
 * from {@link Context}, and tries to convert the contained XML to a POJO.
 * <p>
 * The converter as well as the context key used as identifier for the resulting POJO
 * inside the context, are defined by the constructor.
 *
 * @param <T> request data type
 */
public class XmlRequestMappingMiddleware<T> implements Middleware {

    private final XmlToObjectConverter<T> converter;
    private final Key<T> requestDataCtxKey;

    public XmlRequestMappingMiddleware(
            XmlToObjectConverter<T> converter,
            Key<T> requestDataCtxKey
    ) {
        if (converter == null) {
            throw new IllegalArgumentException("The XML-to-object converter must not be null");
        }
        if (requestDataCtxKey == null) {
            throw new IllegalArgumentException("The request data context key must not be null");
        }

        this.converter = converter;
        this.requestDataCtxKey = requestDataCtxKey;
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        InputStream is = ctx.getOrThrow(RequestContextKey.REQUEST_CONTENT_STREAM);

        T requestData = this.converter.toObject(is);

        ctx.put(this.requestDataCtxKey, requestData);

        chain.next();
    }

}
