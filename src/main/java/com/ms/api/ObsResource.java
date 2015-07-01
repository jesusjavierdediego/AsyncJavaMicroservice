
package com.ms.api;

import com.ms.domain.Identity;
import com.ms.observableServices.GitHubObsService;
import com.ms.domain.JSONPlaceholderItem;
import com.ms.domain.UserInfo;
import com.ms.observableServices.JSONPlaceholderObsService;
import com.ms.utils.Utils;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ManagedAsync;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.schedulers.Schedulers;

@Path("/msobs")
@Produces("application/json")
public class ObsResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObsResource.class);
    private static final Logger PERFORMANCE_LOGGER = LoggerFactory.getLogger("performance");
    
    private final JSONPlaceholderObsService jSONPlaceholderObsService = new JSONPlaceholderObsService();
    private final GitHubObsService gitHubObsService = new GitHubObsService();

    @GET
    @Path("/userInfo/{user}")
    @Produces(MediaType.APPLICATION_JSON) 
    @ManagedAsync
    public void bookAndComment(@Suspended final AsyncResponse asyncResponse, @PathParam("user") String user) {
        final long timeInitial = System.nanoTime();
        
        Observable<JSONPlaceholderItem> jSONPlaceholderItem = jSONPlaceholderObsService.itemObs(Utils.getRandom().toString());
        Observable<Identity> gitHubUserObs = gitHubObsService.userObs(user);

        
        Observable.just(new UserInfo())
                .zipWith(jSONPlaceholderItem, (response, jsonItem) -> {
                    response.setJSONplaceholderItem(jsonItem);
                    return response;
                })
                .zipWith(gitHubUserObs, (response, gUser) -> {
                    response.setGitHubUser(gUser);
                    return response;
                })
                // Observe on another thread than the one processing the jSONplaceholderItem/gitHub user.
                .observeOn(Schedulers.io())
                .subscribe(response -> {
                    // Do something with errors.
                    PERFORMANCE_LOGGER.debug("Reactive Observable operation performed in: " + (System.nanoTime() - timeInitial) / 1000000 + " ms");
                    asyncResponse.resume(response);
                }, asyncResponse::resume);
        
            
         
        asyncResponse.setTimeout(1000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(
                ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));
    }
}
