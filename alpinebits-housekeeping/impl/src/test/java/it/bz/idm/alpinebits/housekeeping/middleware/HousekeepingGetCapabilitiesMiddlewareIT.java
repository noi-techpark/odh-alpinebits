/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.housekeeping.middleware;

import it.bz.idm.alpinebits.common.constants.HousekeepingActionEnum;
import it.bz.idm.alpinebits.housekeeping.middleware.utils.IntegrationTestingMiddleware;
import it.bz.idm.alpinebits.housekeeping.middleware.utils.RouterMiddlewareBuilder;
import it.bz.idm.alpinebits.servlet.impl.AlpineBitsServlet;
import it.bz.idm.alpinebits.servlet.middleware.AlpineBitsClientProtocolMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.MultipartFormDataParserMiddleware;
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
 * Integration tests for {@link HousekeepingGetCapabilitiesMiddleware}.
 */
public class HousekeepingGetCapabilitiesMiddlewareIT extends Arquillian {

    @ArquillianResource
    private URL base;

    @Deployment(testable = false)
    @SuppressWarnings("ArquillianTooManyDeployment")
    public static WebArchive createDeployment() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(AlpineBitsServlet.class)
                .addClasses(AlpineBitsClientProtocolMiddleware.class)
                .addClasses(MultipartFormDataParserMiddleware.class)
                .addClasses(IntegrationTestingMiddleware.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("web-housekeeping-middleware-integration-test.xml", "web.xml");

        return war;
    }

    @Test
    @RunAsClient
    public void testHandleContext_InvokeGetCapabilititesWithNotMatchingVersion() {
        given()
                .header(
                        AlpineBitsClientProtocolMiddleware.CLIENT_PROTOCOL_VERSION_HEADER,
                        RouterMiddlewareBuilder.DEFAULT_VERSION + 1
                )
                .multiPart("action", HousekeepingActionEnum.GET_CAPABLILITIES.toString())
                .when()
                .post(this.base + "AlpineBits")
                .then()
                .statusCode(HttpServletResponse.SC_BAD_REQUEST)
                .content(containsString("ERROR"));
    }

    @Test
    @RunAsClient
    public void testHandleContext_InvokeGetCapabilititesWithVersionMatch() {
        given()
                .header(
                        AlpineBitsClientProtocolMiddleware.CLIENT_PROTOCOL_VERSION_HEADER,
                        RouterMiddlewareBuilder.DEFAULT_VERSION
                )
                .multiPart("action", HousekeepingActionEnum.GET_CAPABLILITIES.toString())
                .when()
                .post(this.base + "AlpineBits")
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .content(containsString("getVersion"), containsString("getCapabilities"));
    }

}