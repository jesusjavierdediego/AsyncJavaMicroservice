package com.ms.api;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import org.glassfish.jersey.server.Uri;
import rx.Observable;
import com.ms.domain.FacebookUser;
import com.ms.services.FacebookRxService;
import javax.inject.Inject;
import org.glassfish.jersey.server.ManagedAsync;

@Path("/ms")
public class RxResource {

    @Uri("/userInfo/{user}")
    private WebTarget user;
    
    @Uri("/contributors/{user}")
    private WebTarget contributor;
    
    @Inject
    private FacebookRxService facebookService;

    @GET
    @ManagedAsync
    public void observableUser(@Suspended final AsyncResponse async) {
        final long time = System.nanoTime();
        
        Observable.just(new FacebookUser("", "", ""));
    }
    @GET
    @ManagedAsync
    public void observableContributor(@Suspended final AsyncResponse async) {
        final long time = System.nanoTime();
        
        
    }

}