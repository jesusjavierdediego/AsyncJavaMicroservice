package com.ms.api;

import com.ms.domain.Identity;
import com.ms.domain.Metrics;
import com.ms.utils.Utils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/metadata")
@Produces("application/json")
public class MetadataResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataResource.class);
    
    @GET
    @Path("/identity")
    @Produces(MediaType.APPLICATION_JSON) 
    public Identity userInfoSync(Response response, @PathParam("user") String user) {
        return new Identity(Utils.getImplVersion(this), Utils.getImplName(this));
    }
    
    @GET
    @Path("/metrics/performance")
    @Produces(MediaType.APPLICATION_JSON) 
    public Metrics getPerformance(Response response, @PathParam("mode") String mode) {
        return null;
    }

}