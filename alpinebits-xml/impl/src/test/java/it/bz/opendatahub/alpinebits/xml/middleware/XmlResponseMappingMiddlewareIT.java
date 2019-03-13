/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.xml.middleware;

import it.bz.opendatahub.alpinebits.servlet.impl.AlpineBitsServlet;
import it.bz.opendatahub.alpinebits.xml.middleware.utils.NotValidatingXmlResponseMappingMiddleware;
import it.bz.opendatahub.alpinebits.xml.middleware.utils.RngValidatingXmlResponseMappingMiddleware;
import it.bz.opendatahub.alpinebits.xml.middleware.utils.XsdValidatingXmlResponseMappingMiddleware;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletResponse;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

/**
 * Integration tests for {@link XmlRequestMappingMiddleware}.
 */
public class XmlResponseMappingMiddlewareIT extends Arquillian {

    @ArquillianResource
    private URL base;

    @Deployment(name = "NoValidation", testable = false)
    @SuppressWarnings("ArquillianTooManyDeployment")
    public static WebArchive createNotValidatingDeployment() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "test-no-validation.war")
                .addClasses(AlpineBitsServlet.class)
                .addClasses(NotValidatingXmlResponseMappingMiddleware.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("web/context.xml", "context.xml")
                .addAsWebInfResource("web/web-not-validating-xml-response-wrapping-middleware-integration-test.xml", "web.xml");

        return war;
    }

    @Deployment(name = "RngValidation", testable = false)
    @SuppressWarnings("ArquillianTooManyDeployment")
    public static WebArchive createRngValidatingDeployment() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "test-rng-validation.war")
                .addClasses(AlpineBitsServlet.class)
                .addClasses(RngValidatingXmlResponseMappingMiddleware.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("web/context.xml", "context.xml")
                .addAsWebInfResource("web/web-rng-validating-xml-response-wrapping-middleware-integration-test.xml", "web.xml");

        return war;
    }

    @Deployment(name = "RngValidationError", testable = false)
    @SuppressWarnings("ArquillianTooManyDeployment")
    public static WebArchive createRngValidatingErrorDeployment() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "test-rng-validation-error.war")
                .addClasses(AlpineBitsServlet.class)
                .addClasses(RngValidatingXmlResponseMappingMiddleware.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("web/context.xml", "context.xml")
                .addAsWebInfResource("web/web-rng-validating-error-xml-response-wrapping-middleware-integration-test.xml", "web.xml");

        return war;
    }

    @Deployment(name = "XsdValidation", testable = false)
    @SuppressWarnings("ArquillianTooManyDeployment")
    public static WebArchive createXsdValidatingDeployment() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "test-xsd-validation.war")
                .addClasses(AlpineBitsServlet.class)
                .addClasses(XsdValidatingXmlResponseMappingMiddleware.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("web/context.xml", "context.xml")
                .addAsWebInfResource("web/web-xsd-validating-xml-response-wrapping-middleware-integration-test.xml", "web.xml");

        return war;
    }

    @Deployment(name = "XsdValidationError", testable = false)
    @SuppressWarnings("ArquillianTooManyDeployment")
    public static WebArchive createXsdValidatingErrorDeployment() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "test-xsd-validation-error.war")
                .addClasses(AlpineBitsServlet.class)
                .addClasses(XsdValidatingXmlResponseMappingMiddleware.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("web/context.xml", "context.xml")
                .addAsWebInfResource("web/web-xsd-validating-error-xml-response-wrapping-middleware-integration-test.xml", "web.xml");

        return war;
    }

    @Test
    @OperateOnDeployment("NoValidation")
    @RunAsClient
    public void testHandleContext_Ok() {
        given()
                .multiPart("action", "some action")
                .multiPart("request", "some string")
                .when()
                .post(this.base + "AlpineBits")
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .content(
                        containsString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"),
                        containsString("Success"));
    }

    @Test
    @OperateOnDeployment("RngValidationError")
    @RunAsClient
    public void testHandleContext_RngValidation_InvalidXML() {
        given()
                .multiPart("action", "some action")
                .multiPart("request", "some string")
                .when()
                .post(this.base + "AlpineBits")
                .then()
                .statusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                .content(containsString("ERROR:"));
    }

    @Test
    @OperateOnDeployment("RngValidation")
    @RunAsClient
    public void testHandleContext_RngValidation_Ok() {
        given()
                .multiPart("action", "some action")
                .multiPart("request", "some string")
                .when()
                .post(this.base + "AlpineBits")
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .content(
                        containsString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"),
                        containsString("Success"));
    }

    @Test
    @OperateOnDeployment("XsdValidationError")
    @RunAsClient
    public void testHandleContext_XsdValidation_InvalidXML() {
        given()
                .multiPart("action", "some action")
                .multiPart("request", "some string")
                .when()
                .post(this.base + "AlpineBits")
                .then()
                .statusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                .content(containsString("ERROR:"));
    }

    @Test
    @OperateOnDeployment("XsdValidation")
    @RunAsClient
    public void testHandleContext_XsdValidation_Ok() {
        given()
                .multiPart("action", "some action")
                .multiPart("request", "some string")
                .when()
                .post(this.base + "AlpineBits")
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .content(
                        containsString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"),
                        containsString("Success"));
    }
}