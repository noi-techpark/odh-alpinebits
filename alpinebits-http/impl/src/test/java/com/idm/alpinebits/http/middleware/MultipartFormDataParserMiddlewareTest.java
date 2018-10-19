/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.idm.alpinebits.http.middleware;

import com.idm.alpinebits.http.HttpContextKey;
import com.idm.alpinebits.http.InvalidRequestContentTypeException;
import com.idm.alpinebits.http.MultipartFormDataParseException;
import com.idm.alpinebits.http.UndefinedActionException;
import com.idm.alpinebits.http.impl.util.MultipartFormDataRequestBuilder;
import com.idm.alpinebits.middleware.Context;
import com.idm.alpinebits.middleware.Middleware;
import com.idm.alpinebits.middleware.RequiredContextKeyMissingException;
import com.idm.alpinebits.middleware.impl.SimpleContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Test cases for {@link MultipartFormDataParserMiddleware} class.
 */
public class MultipartFormDataParserMiddlewareTest {

    @DataProvider(name = "badContentType")
    public static Object[][] badContentType() {
        return new Object[][]{
                {null},
                {""},
                {"wrong-content-type"},
        };
    }

    @Test(expectedExceptions = RequiredContextKeyMissingException.class)
    public void testHandleContext_RequestIsNull() {
        Middleware middleware = new MultipartFormDataParserMiddleware();

        Context ctx = new SimpleContext();
        middleware.handleContext(ctx, null);
    }

    @Test(dataProvider = "badContentType", expectedExceptions = InvalidRequestContentTypeException.class)
    public void testHandleContext_NoContentType(String contentType) {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContentType())
                .thenReturn(contentType);

        this.executeMiddleware(request);
    }

    @Test(expectedExceptions = MultipartFormDataParseException.class)
    public void testHandleContext_MultipartFormDataParseError() throws Exception {
        HttpServletRequest request = MultipartFormDataRequestBuilder.buildRequest("");
        // Set an invalid content type (it misses a boundary)
        when(request.getContentType()).thenReturn("multipart/form-data");

        this.executeMiddleware(request);
    }

    @Test(expectedExceptions = UndefinedActionException.class)
    public void testHandleContext_NoActionParam() throws Exception {
        String s = MultipartFormDataRequestBuilder.buildMultiPartRequestOnly();
        HttpServletRequest request = MultipartFormDataRequestBuilder.buildRequest(s);

        this.executeMiddleware(request);
    }

    @Test
    public void testHandleContext_CheckActionParamInContext() throws Exception {
        String s = MultipartFormDataRequestBuilder.buildMultiPartActionOnly();
        HttpServletRequest request = MultipartFormDataRequestBuilder.buildRequest(s);

        Context ctx = this.executeMiddleware(request);
        String action = ctx.getOrThrow(MultipartFormDataParserMiddleware.AB_PART_ACTION, String.class);
        assertEquals(action, MultipartFormDataRequestBuilder.ALPINEBITS_ACTION_PARAM);
    }

    @Test
    public void testHandleContext_CheckRequestParamInContext() throws Exception {
        String s = MultipartFormDataRequestBuilder.buildMultiPartActionAndRequest();
        HttpServletRequest request = MultipartFormDataRequestBuilder.buildRequest(s);

        Context ctx = this.executeMiddleware(request);
        OutputStream stream = ctx.getOrThrow(MultipartFormDataParserMiddleware.AB_PART_REQUEST_OUTPUTSTREAM, OutputStream.class);
        String alpineBitsRequest = stream.toString();
        assertEquals(alpineBitsRequest, MultipartFormDataRequestBuilder.ALPINEBITS_REQUEST_PARAM);
    }

    private Context executeMiddleware(HttpServletRequest request) {
        Context ctx = new SimpleContext();
        ctx.set(HttpContextKey.HTTP_REQUEST, request);

        Middleware middleware = new MultipartFormDataParserMiddleware();
        middleware.handleContext(ctx, () -> {
        });

        return ctx;
    }
}