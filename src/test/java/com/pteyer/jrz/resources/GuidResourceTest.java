package com.pteyer.jrz.resources;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.google.inject.Guice;
import com.pteyer.jrz.Main;
import com.pteyer.jrz.modules.JrzModulle;
import org.glassfish.grizzly.http.server.HttpServer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class GuidResourceTest {

    private HttpServer server;
    private WebTarget target;

    @BeforeClass
    public void setUp() throws Exception {
        Guice.createInjector(new JrzModulle());
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @AfterClass
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test(enabled = false)
    public void testGetIt() {
        String responseMsg = target.path("guid").request().get(String.class);
        assertNotNull(responseMsg);
    }
}
