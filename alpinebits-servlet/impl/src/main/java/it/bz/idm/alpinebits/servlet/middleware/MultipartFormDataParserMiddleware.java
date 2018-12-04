/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.servlet.middleware;

import it.bz.idm.alpinebits.common.context.RequestContextKey;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;
import it.bz.idm.alpinebits.middleware.RequiredContextKeyMissingException;
import it.bz.idm.alpinebits.servlet.InvalidRequestContentTypeException;
import it.bz.idm.alpinebits.servlet.MultipartFormDataParseException;
import it.bz.idm.alpinebits.servlet.ServletContextKey;
import it.bz.idm.alpinebits.servlet.UndefinedActionException;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This middleware extracts the <code>action</code> and <code>request</code> parts from the
 * HTTP request and adds them to the {@link Context}.
 * <p>
 * The <code>action</code> part is added as <code>String</code> using
 * {@link RequestContextKey#REQUEST_ACTION} key.
 * <p>
 * The <code>request</code> part is added as {@link OutputStream} using the
 * {@link RequestContextKey#REQUEST_CONTENT_STREAM} key.
 * <p>
 * The HTTP request must be present in the {@link Context}. Otherwise, a
 * {@link RequiredContextKeyMissingException} is thrown.
 * <p>
 * If the requests content-type is not <code>multipart/form-data</code>, an
 * {@link InvalidRequestContentTypeException} is thrown.
 * <p>
 * If the multipart/form-data could not be parsed, a {@link MultipartFormDataParseException} is thrown.
 * <p>
 * If non <code>action</code> part was found in the request, a {@link UndefinedActionException} is thrown.
 */
public class MultipartFormDataParserMiddleware implements Middleware {

    private static final Logger LOG = LoggerFactory.getLogger(MultipartFormDataParserMiddleware.class);

    private static final String FORM_PART_ACTION = "action";
    private static final String FORM_PART_REQUEST = "request";

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        HttpServletRequest request = ctx.getOrThrow(ServletContextKey.SERVLET_REQUEST);

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
        InputStream abRequest = null;

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
                        abRequest = IOUtils.toBufferedInputStream(stream);
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

        ctx.put(RequestContextKey.REQUEST_ACTION, abAction);

        if (abRequest != null) {
            ctx.put(RequestContextKey.REQUEST_CONTENT_STREAM, abRequest);
        }

        LOG.info("AlpineBits action parameter: {}, AlpineBits request parameter is present: {}", abAction, abRequest != null);
    }

}
