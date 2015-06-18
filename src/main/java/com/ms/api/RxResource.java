package com.ms.api;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import org.glassfish.jersey.server.Uri;

import com.ms.domain.FacebookUser;
import com.ms.domain.GitHubUser;
import com.ms.rxservices.FacebookRxService;
import com.ms.utils.Futures;
import com.ms.utils.TaskExecutor;
import java.util.concurrent.CompletableFuture;
//import jersey.repackaged.jsr166e.CompletableFuture;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ManagedAsync;


@Path("/msa")
@Produces("application/json")
public class RxResource {

    @Uri("/userInfo/{user}")
    private WebTarget user;
    
    @Uri("/contributors/{user}")
    private WebTarget contributor;
    
    @Inject
    private FacebookRxService facebookRxService;
    
    @Inject
    private TaskExecutor executor;

    @GET
    @ManagedAsync
    @Path("/userInfo/{user}")
    @Produces(MediaType.APPLICATION_JSON) 
    public void observableUserInfo(@Suspended final AsyncResponse async, @PathParam("user") String user) {
        final long timeInitial = System.nanoTime();
        CompletableFuture<FacebookUser> facebookFuture = Futures.toCompletable(facebookRxService.userRx(user), executor);
        
       final long timeResult = (System.nanoTime() - timeInitial) / 1000000;
    }
    @GET
    @ManagedAsync
    @Path("/contributors/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public void observableContributor(@Suspended final AsyncResponse async) {
        final long time = System.nanoTime();
        
        
    }

}