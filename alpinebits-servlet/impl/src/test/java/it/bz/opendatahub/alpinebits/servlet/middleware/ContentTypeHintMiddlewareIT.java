// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.servlet.middleware;

import it.bz.opendatahub.alpinebits.servlet.impl.AlpineBitsServlet;
import it.bz.opendatahub.alpinebits.servlet.middleware.utils.ContentTypeHintDefinedMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.utils.ContentTypeResponseDefinedMiddleware;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.annotations.Test;

import java.net.URL;

import static io.restassured.RestAssured.when;

/**
 * Integration tests for {@link ContentTypeHintMiddleware}.
 */
public class ContentTypeHintMiddlewareIT extends Arquillian {

    @ArquillianResource
    private URL base;

    @Deployment(name = "NeitherResponseDefinedNorHint", testable = false)
    @SuppressWarnings("ArquillianTooManyDeployment")
    public static WebArchive createDeployment1() {
        return ShrinkWrap.create(WebArchive.class, "test-neither-response-defined-nor-hint.war")
                .addClasses(AlpineBitsServlet.class)
                .addClasses(ContentTypeHintMiddleware.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("web-content-type-hint-middleware-integration-test.xml", "web.xml");
    }

    @Deployment(name = "ResponseDefinedContentType", testable = false)
    @SuppressWarnings("ArquillianTooManyDeployment")
    public static WebArchive createDeployment2() {
        return ShrinkWrap.create(WebArchive.class, "test-response-defined.war")
                .addClasses(AlpineBitsServlet.class)
                .addClasses(ContentTypeHintMiddleware.class)
                .addClasses(ContentTypeResponseDefinedMiddleware.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("web-content-type-hint-response-defined-middleware-integration-test.xml", "web.xml");
    }

    @Deployment(name = "HintDefinedContentType", testable = false)
    @SuppressWarnings("ArquillianTooManyDeployment")
    public static WebArchive createDeployment3() {
        return ShrinkWrap.create(WebArchive.class, "test-hint-defined.war")
                .addClasses(AlpineBitsServlet.class)
                .addClasses(ContentTypeHintMiddleware.class)
                .addClasses(ContentTypeResponseDefinedMiddleware.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("web-content-type-hint-hint-defined-middleware-integration-test.xml", "web.xml");
    }

    @Test
    @OperateOnDeployment("NeitherResponseDefinedNorHint")
    @RunAsClient
    public void testHandleContext_ShouldHaveNoContentTypeHeader_WhenNeitherResponseHeaderNorHintAreSet() {
        when()
                .post(this.base + "AlpineBits")
                .then()
                .header(ContentTypeHintMiddleware.RESPONSE_CONTENT_TYPE_HEADER, (String) null);
    }

    @Test
    @OperateOnDeployment("ResponseDefinedContentType")
    @RunAsClient
    public void testHandleContext_ShouldHaveResponseDefinedContentTypeHeader() {
        when()
                .post(this.base + "AlpineBits")
                .then()
                .header(ContentTypeHintMiddleware.RESPONSE_CONTENT_TYPE_HEADER, ContentTypeResponseDefinedMiddleware.CONTENT_TYPE_HEADER_VALUE);
    }

    @Test
    @OperateOnDeployment("HintDefinedContentType")
    @RunAsClient
    public void testHandleContext_ShouldHaveHintDefinedContentTypeHeader() {
        when()
                .post(this.base + "AlpineBits")
                .then()
                .header(ContentTypeHintMiddleware.RESPONSE_CONTENT_TYPE_HEADER, ContentTypeHintDefinedMiddleware.CONTENT_TYPE_HEADER_VALUE);
    }

}