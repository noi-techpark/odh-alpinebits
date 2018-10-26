/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.servlet.middleware;

import io.restassured.response.ValidatableResponse;
import it.bz.idm.alpinebits.common.constants.HousekeepingActionEnum;
import it.bz.idm.alpinebits.servlet.impl.AlpineBitsServlet;
import it.bz.idm.alpinebits.servlet.middleware.utils.HousekeepingWrapperMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.utils.RegexMatcher;
import org.hamcrest.Matchers;
import org.hamcrest.text.IsEmptyString;
import org.jboss.arquillian.container.test.api.Deployment;
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
import static org.hamcrest.core.StringContains.containsString;

/**
 * Integration tests for {@link HousekeepingWriterMiddleware}.
 */
public class HousekeepingWriterMiddlewareIT extends Arquillian {

    @ArquillianResource
    private URL base;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(AlpineBitsServlet.class)
                .addClasses(AlpineBitsClientProtocolMiddleware.class)
                .addClasses(MultipartFormDataParserMiddleware.class)
                .addClasses(HousekeepingWriterMiddleware.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("web-housekeeping-writer-middleware-integration-test.xml", "web.xml");

        return war;
    }

    @Test
    @RunAsClient
    public void testHandleContext_noHousekeepingAction() {
        given()
                .header(
                        AlpineBitsClientProtocolMiddleware.CLIENT_PROTOCOL_VERSION_HEADER,
                        HousekeepingWrapperMiddleware.DEFAULT_VERSION
                )
                .multiPart("action", "some")
                .when()
                .post(this.base + "AlpineBits")
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .content(IsEmptyString.isEmptyString());
    }

    @Test
    @RunAsClient
    public void testHandleContext_getVersionAction() {
        given()
                .header(
                        AlpineBitsClientProtocolMiddleware.CLIENT_PROTOCOL_VERSION_HEADER,
                        HousekeepingWrapperMiddleware.DEFAULT_VERSION
                )
                .multiPart("action", HousekeepingActionEnum.GET_VERSION.getAction())
                .when()
                .post(this.base + "AlpineBits")
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .content(Matchers.equalTo("OK:" + HousekeepingWrapperMiddleware.DEFAULT_VERSION));
    }

    @Test
    @RunAsClient
    public void testHandleContext_getCapabilitiesAction() {
        ValidatableResponse response = given()
                .header(
                        AlpineBitsClientProtocolMiddleware.CLIENT_PROTOCOL_VERSION_HEADER,
                        HousekeepingWrapperMiddleware.DEFAULT_VERSION
                )
                .multiPart("action", HousekeepingActionEnum.GET_CAPABLILITIES.getAction())
                .when()
                .post(this.base + "AlpineBits")
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .content(RegexMatcher.matches("OK:([\\w]+)(,[\\w]+)*"));

        HousekeepingWrapperMiddleware.DEFAULT_CAPABILITIES.stream()
                .forEach(capability -> response.content(containsString(capability)));
    }

}