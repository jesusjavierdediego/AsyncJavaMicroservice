
package com.ms.api;

import com.ms.observableServices.FacebookObsService;
import com.ms.observableServices.GitHubObsService;
import com.ms.domain.FacebookUser;
import com.ms.domain.GitHubUser;
import com.ms.domain.UserInfo;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ManagedAsync;
import rx.Observable;
import rx.Subscriber;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

@Path("/msobs")
@Produces("application/json")
public class ObsService {
    
    @Inject
    private FacebookObsService facebookObsService;
    
    @Inject
    private GitHubObsService gitHubObsService;
    
    
    @GET
    @Path("/userInfo/{user}")
    @Produces(MediaType.APPLICATION_JSON) 
    @ManagedAsync
    public void bookAndComment(@Suspended final AsyncResponse asyncResponse, @PathParam("user") String user) {
        final long time = System.nanoTime();
        
        Observable<FacebookUser> facebookUserObs = facebookObsService.userObs(user);
        Observable<GitHubUser> gitHubUserObs = gitHubObsService.userObs(user);

         Observable.zip(facebookUserObs, gitHubUserObs, (f, g) -> 
                            new UserInfo(f, g)
                  ).subscribe(new Subscriber<UserInfo>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                //asyncResponse.resume(e);
                                asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build());
                            }

                            @Override
                            public void onNext(UserInfo userInfo) {
                                asyncResponse.resume(userInfo);
                            }
                        });
         
        asyncResponse.setTimeout(1000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(
                ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));
    }
}
