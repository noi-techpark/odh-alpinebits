/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

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
import com.idm.alpinebits.middleware.Context;
import com.idm.alpinebits.middleware.Middleware;
import com.idm.alpinebits.middleware.MiddlewareChain;
import com.idm.alpinebits.middleware.RequiredContextKeyMissingException;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This middleware extracts the <code>action</code> and <code>request</code> parts from the
 * HTTP request and adds them to the {@link Context}.
 * <p>
 * The <code>action</code> part is added as <code>String</code> using the
 * {@link MultipartFormDataParserMiddleware#AB_PART_ACTION} key.
 * <p>
 * The <code>request</code> part is added as {@link OutputStream} using the
 * {@link MultipartFormDataParserMiddleware#AB_PART_REQUEST_OUTPUTSTREAM} key.
 * <p>
 * The HTTP request must be present in the {@link Context}, indexed by {@link HttpContextKey#HTTP_REQUEST}.
 * Otherwise, a {@link RequiredContextKeyMissingException} is thrown.
 * <p>
 * If the requests content-type is not <code>multipart/form-data</code>, an
 * {@link InvalidRequestContentTypeException} is thrown.
 * <p>
 * If the multipart/form-data could not be parsed, a {@link MultipartFormDataParseException} is thrown.
 * <p>
 * If non <code>action</code> part was found in the request, a {@link UndefinedActionException} is thrown.
 */
public class MultipartFormDataParserMiddleware implements Middleware {

    public static final String FORM_PART_ACTION = "action";
    public static final String FORM_PART_REQUEST = "request";
    public static final String AB_PART_ACTION = "ab.part.action";
    public static final String AB_PART_REQUEST_OUTPUTSTREAM = "ab.part.request.inputstream";

    private static final Logger LOG = LoggerFactory.getLogger(MultipartFormDataParserMiddleware.class);

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        HttpServletRequest request = ctx.getOrThrow(HttpContextKey.HTTP_REQUEST, HttpServletRequest.class);

        this.checkIsMultipartOrThrow(request);

        this.parseRequestAndAddToContext(request, ctx);

        chain.next();
    }

    private void checkIsMultipartOrThrow(HttpServletRequest request) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            String contentType = request.getContentType();
            throw new InvalidRequestContentTypeException("Expecting content-type: multipart/form-data. Got: " + contentType);
        }
    }

    private void parseRequestAndAddToContext(HttpServletRequest request, Context ctx) {
        LOG.debug("Parsing multipart/form-data");

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload();

        // This list is filled with all form part names. It can be used for logs or exceptions later on
        List<String> formParts = new ArrayList<>();

        String abAction = null;
        OutputStream abRequest = null;

        // Parse the request
        try {
            FileItemIterator iterStream = upload.getItemIterator(request);
            while (iterStream.hasNext()) {
                FileItemStream item = iterStream.next();
                String name = item.getFieldName();
                formParts.add(name);

                try (InputStream stream = item.openStream()) {

                    if (FORM_PART_ACTION.equalsIgnoreCase(name)) {
                        abAction = Streams.asString(stream, "UTF-8");
                    }

                    if (FORM_PART_REQUEST.equalsIgnoreCase(name)) {
                        abRequest = new ByteArrayOutputStream();
                        Streams.copy(stream, abRequest, true);
                    }
                }
            }
        } catch (FileUploadException | IOException e) {
            throw new MultipartFormDataParseException("Error while parsing multipart/form-data", e);
        }

        if (abAction == null) {
            String formPartsInfo = formParts.isEmpty()
                    ? "No multipart/form-data parts found at all"
                    : "The following multipart/form-data parts were found: " + formParts.toString();
            throw new UndefinedActionException("No action part defined in the multipart/form-data request. " + formPartsInfo);
        }

        ctx.set(AB_PART_ACTION, abAction);

        if (abRequest != null) {
            ctx.set(AB_PART_REQUEST_OUTPUTSTREAM, abRequest);
        }

        LOG.info("AlpineBits action parameter: {}, AlpineBits request parameter is present: {}", abAction, abRequest != null);
    }

}
