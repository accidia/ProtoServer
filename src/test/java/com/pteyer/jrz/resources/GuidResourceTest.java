package com.pteyer.jrz.resources;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.pteyer.jrz.IJrzApplication;
import com.pteyer.jrz.JrzApplicationTestGuid;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class GuidResourceTest {

    private WebTarget target;
    private IJrzApplication application = new JrzApplicationTestGuid();

    @BeforeClass
    public void setUp() throws Exception {
        this.application.startServer();
        final Client client = ClientBuilder.newClient();
        this.target = client.target(JrzApplicationTestGuid.BASE_URI);
    }

    @AfterClass
    public void tearDown() throws Exception {
        this.application.stopServer();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        String responseMsg = this.target.path(".guid").request().get(String.class);
        assertNotNull(responseMsg);
    }
}
