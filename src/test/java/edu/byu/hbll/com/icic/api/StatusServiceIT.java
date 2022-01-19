package edu.byu.hbll.com.icic.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.time.ZonedDateTime;

import org.junit.Test;

/**
 * Integration tests for {@link StatusService}.
 */
public class StatusServiceIT {

    private Client client = ClientBuilder.newClient();

    /**
     * Verifies that the ping service returns a 200 status and a parseable timestamp.
     */
    @Test
    public void pingShouldReturnHostNameAndTimestamp() {
        Response response = client.target("http://localhost:8080/com.icic/status/ping").request().get();

        // Verify that the correct response code was sent.
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        // Verify that the correct response body was sent.
        String responseBody = response.readEntity(String.class);
        String[] responseElements = responseBody.split(" ");

        assertNotNull(responseElements[0]);
        ZonedDateTime.parse(responseElements[1]);
        // If we reach this point without exceptions, the test has passed. No further assertions are necessary.
    }

}
