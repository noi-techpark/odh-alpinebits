/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking;

import it.bz.opendatahub.alpinebits.common.exception.AlpineBitsException;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.middleware.XmlRequestMappingMiddleware;
import it.bz.opendatahub.alpinebits.xml.middleware.XmlResponseMappingMiddleware;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAPingRQ;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAPingRS;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;

/**
 * This {@link DefaultContextSerializer} uses a {@link XmlRequestMappingMiddleware}
 * to build an {@link OTAPingRQ} instance from the context, and a
 * {@link XmlResponseMappingMiddleware} to serialize {@link OTAPingRQ} instances
 * into the context.
 * <p>
 * The OTAPingRQ and OTAPingRQ instances are put into the context using the
 * {@link #OTA_PING_RQ_KEY} and {@link #OTA_PING_RS_KEY} key respectively.
 */
public final class DefaultContextSerializer implements ContextSerializer {

    public static final Key<OTAPingRQ> OTA_PING_RQ_KEY = Key.key("ping request key", OTAPingRQ.class);
    public static final Key<OTAPingRS> OTA_PING_RS_KEY = Key.key("ping response key", OTAPingRS.class);

    private final XmlRequestMappingMiddleware<OTAPingRQ> xmlRequestMappingMiddleware;
    private final XmlResponseMappingMiddleware<OTAPingRS> xmlResponseMappingMiddleware;

    public DefaultContextSerializer(String alpineBitsVersion) {
        this(XmlValidationSchemaProvider.buildXsdSchemaForAlpineBitsVersion(alpineBitsVersion));
    }

    public DefaultContextSerializer(Schema schema) {
        try {
            XmlToObjectConverter<OTAPingRQ> xmlToObjectConverter = new JAXBXmlToObjectConverter.Builder<>(OTAPingRQ.class)
                    .schema(schema)
                    .build();
            this.xmlRequestMappingMiddleware = new XmlRequestMappingMiddleware<>(xmlToObjectConverter, OTA_PING_RQ_KEY);

            ObjectToXmlConverter<OTAPingRS> objectToXmlConverter = new JAXBObjectToXmlConverter.Builder<>(OTAPingRS.class)
                    .schema(schema)
                    .build();
            this.xmlResponseMappingMiddleware = new XmlResponseMappingMiddleware<>(objectToXmlConverter, OTA_PING_RS_KEY);
        } catch (JAXBException e) {
            throw new AlpineBitsException("DefaultXmlMapper could not be created", 500, e);
        }
    }

    /**
     * Use a {@link XmlRequestMappingMiddleware} to build a {@link OTAPingRQ} instance.
     * <p>
     * The OTAPingRQ instance is also written to the context using the
     * {@link #OTA_PING_RQ_KEY} key.
     *
     * @param ctx This context provides the XML stream such that a
     *            {@link OTAPingRQ} instance can be inferred and returned.
     * @return The {@link OTAPingRQ} instance.
     */
    @Override
    public OTAPingRQ fromContext(Context ctx) {
        if (ctx == null) {
            throw new IllegalArgumentException("Context must not be null");
        }
        this.xmlRequestMappingMiddleware.handleContext(ctx, () -> {
        });
        return ctx.getOrThrow(OTA_PING_RQ_KEY);
    }

    /**
     * Use a {@link XmlResponseMappingMiddleware} to serialize a {@link OTAPingRQ}
     * instance into the context.
     * <p>
     * The input OTAPingRS is also written to the context using the
     * {@link #OTA_PING_RS_KEY} key.
     *
     * @param ctx       The {@link OTAPingRS} instance is put into this context.
     * @param otaPingRS The {@link OTAPingRS} instance.
     */
    @Override
    public void toContext(Context ctx, OTAPingRS otaPingRS) {
        if (ctx == null) {
            throw new IllegalArgumentException("Context must not be null");
        }
        ctx.put(OTA_PING_RS_KEY, otaPingRS);
        this.xmlResponseMappingMiddleware.handleContext(ctx, () -> {
        });
    }

}
