/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.handshaking.middleware;

import it.bz.opendatahub.alpinebits.handshaking.ContextSerializer;
import it.bz.opendatahub.alpinebits.handshaking.HandshakingDataConversionException;
import it.bz.opendatahub.alpinebits.handshaking.JsonSerializer;
import it.bz.opendatahub.alpinebits.handshaking.utils.ConfigurableContextSerializer;
import it.bz.opendatahub.alpinebits.handshaking.utils.EmptyContextSerializer;
import it.bz.opendatahub.alpinebits.handshaking.utils.HandshakingDataBuilder;
import it.bz.opendatahub.alpinebits.handshaking.utils.RouterBuilder;
import it.bz.opendatahub.alpinebits.handshaking.utils.ThrowingContextSerializer;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.RequiredContextKeyMissingException;
import it.bz.opendatahub.alpinebits.middleware.impl.SimpleContext;
import it.bz.opendatahub.alpinebits.routing.Router;
import it.bz.opendatahub.alpinebits.routing.RouterContextKey;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAPingRQ;
import it.bz.opendatahub.alpinebits.xml.schema.ota.OTAPingRS;
import it.bz.opendatahub.alpinebits.xml.schema.ota.WarningsType;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test cases for {@link HandshakingMiddleware} class.
 */
public class HandshakingMiddlewareTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructor_ShouldThrow_WhenContextSerializerIsNull() {
        new HandshakingMiddleware(null);
    }


    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testHandleContext_ShouldThrow_WhenAlpineBitsRouterIsNull() {
        Context ctx = new SimpleContext();
        Middleware middleware = new HandshakingMiddleware(new EmptyContextSerializer());
        middleware.handleContext(ctx, null);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testHandleContext_ShouldThrow_WhenContextSerializerThrows() {
        Context ctx = getDefaultContext();
        ContextSerializer serializer = new ThrowingContextSerializer(new RuntimeException("error"));
        Middleware middleware = new HandshakingMiddleware(serializer);
        middleware.handleContext(ctx, null);
    }

    @Test(expectedExceptions = HandshakingDataConversionException.class)
    public void testHandleContext_ShouldThrow_WhenJsonSerializerThrows() {
        Context ctx = getDefaultContext();
        OTAPingRQ otaPingRQ = new OTAPingRQ();
        otaPingRQ.setEchoData("[invalid json");
        ContextSerializer serializer = new ConfigurableContextSerializer(otaPingRQ);
        Middleware middleware = new HandshakingMiddleware(serializer);
        middleware.handleContext(ctx, null);
    }

    @Test
    public void testHandleContext_ShouldReturnEmptyJsonObjectAsWarningContent_IfNoMatch() {
        Context ctx = getContextForRouter(RouterBuilder.buildRouter(RouterBuilder.DEFAULT_VERSION + 1));

        JsonSerializer jsonSerializer = new JsonSerializer();
        String echoData = jsonSerializer.toJson(HandshakingDataBuilder.getDefaultHandshakingData());

        OTAPingRQ otaPingRQ = new OTAPingRQ();
        otaPingRQ.setEchoData(echoData);

        ContextSerializer serializer = new ConfigurableContextSerializer(otaPingRQ);
        Middleware middleware = new HandshakingMiddleware(serializer);
        middleware.handleContext(ctx, null);

        OTAPingRS otaPingRs = ctx.getOrThrow(ConfigurableContextSerializer.OTA_PING_RS_KEY);

        checkOtaPingRsEchoData(otaPingRs, echoData);

        checkOtaPingRsWarning(otaPingRs, "{}");
    }

    @Test
    public void testHandleContext_ShouldReturnIntersectionAsWarningContent_OnMatch() {
        Context ctx = getContextForRouter(RouterBuilder.buildRouter(RouterBuilder.DEFAULT_VERSION));

        JsonSerializer jsonSerializer = new JsonSerializer();
        String echoData = jsonSerializer.toJson(HandshakingDataBuilder.getDefaultHandshakingData());

        OTAPingRQ otaPingRQ = new OTAPingRQ();
        otaPingRQ.setEchoData(echoData);

        ContextSerializer serializer = new ConfigurableContextSerializer(otaPingRQ);
        Middleware middleware = new HandshakingMiddleware(serializer);
        middleware.handleContext(ctx, null);

        OTAPingRS otaPingRs = ctx.getOrThrow(ConfigurableContextSerializer.OTA_PING_RS_KEY);

        checkOtaPingRsEchoData(otaPingRs, echoData);

        checkOtaPingRsWarning(otaPingRs, echoData);
    }

    private Context getDefaultContext() {
        return getContextForRouter(RouterBuilder.DEFAULT_ROUTER);
    }

    private Context getContextForRouter(Router router) {
        Context ctx = new SimpleContext();
        ctx.put(RouterContextKey.ALPINEBITS_ROUTER, router);
        return ctx;
    }

    private void checkOtaPingRsEchoData(OTAPingRS otaPingRs, String echoData) {
        // Extract echo data from otaPingRs
        Optional<String> otaPingRsEchoData = otaPingRs.getSuccessesAndEchoDatasAndWarnings().stream()
                .filter(o -> o instanceof String)
                .map(o -> Optional.of((String) o))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Echo data expected but no No echo data found"));

        assertTrue(otaPingRsEchoData.isPresent());
        assertEquals(otaPingRsEchoData.get(), echoData);
    }

    private void checkOtaPingRsWarning(OTAPingRS otaPingRs, String warningContent) {
        // Extract warning from otaPingRs
        Optional<WarningsType> otaPingRsWarnings = otaPingRs.getSuccessesAndEchoDatasAndWarnings().stream()
                .filter(o -> o instanceof WarningsType)
                .map(o -> Optional.of((WarningsType) o))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Warning expected but no warning found"));

        assertTrue(otaPingRsWarnings.isPresent());

        WarningsType wt = otaPingRsWarnings.get();
        assertEquals(wt.getWarnings().size(), 1);
        assertEquals(wt.getWarnings().get(0).getValue(), warningContent);
    }

}