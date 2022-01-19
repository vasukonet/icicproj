package edu.byu.hbll.com.icic.api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZonedDateTime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JAX-RS web service controller that provides at-a-glance information about the current status or health of the
 * application.
 */
@Path("status")
public class StatusService {

    private static final Logger logger = LoggerFactory.getLogger(StatusService.class);
    private static String hostName;
    static {
        try {
            hostName = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            logger.error("the local host name could not be resolved into an address", e);
        }
    }

    /**
     * Returns a {@code 200 OK} response if this application is currently accepting requests or a non-200 response if is
     * is not currently accepting requests.
     * 
     * @return a JAX-RS response as described
     */
    @GET @Path("ping")
    public Response ping() {
        // TODO: This service is primarily used as a standardized way of informing a proxy/balancer server as to whether
        //       or not this particular instance of the application is currently accepting requests. A failure
        //       (represented by any non-200 response) does not necessarily mean the application is unhealthy; it could
        //       simply be indicative that is in a maintenance mode and should not be called by downstream users.
        //
        //       For most applications, the default implementation provided by the archetype is sufficient and this
        //       comment may be summarily removed. If you choose to change the default functionality, recognize that
        //       this service will be polled at least once per second and must therefore be extremely lightweight/fast.
        //       It is recommended therefore, that any time-consuming calculations required to return an appropriate
        //       response be executed using a @Schedule method (or other means of multi-threading) and cached. 
        String responseBody = hostName + ": " + ZonedDateTime.now();
        return Response.ok(responseBody).build();
    }

    /**
     * Returns a response indicating whether or not this application is currently healthy.
     * 
     * @return a JAX-RS response as described
     */
    @GET @Path("health")
    public Response health() {
        // TODO: This service is primarily used by as a standardized way of informing an application monitoring service
        //       (such as Nagios) as to whether or not this particular instance of the application is currently
        //       "healthy".
        //
        //       Since the definition of "healthy" is unique to each application, no guidelines are given as
        //       to how this service should be implemented. Unlike the ping service, this service does not need to give
        //       a near-immediate response, since it will only be polled every few minutes as defined by the monitoring
        //       service. As with ping, it may still be desirable to defer time-consuming calculations to a @Schedule
        //       method (or other means of multi-threading).
        return ping();
    }

}
