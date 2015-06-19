package com.ms.api;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import org.glassfish.jersey.server.Uri;

import com.ms.domain.FacebookUser;
import com.ms.domain.GitHubContributor;
import com.ms.domain.GitHubRepo;
import com.ms.domain.GitHubUser;
import com.ms.domain.UserInfo;
import com.ms.rxservices.FacebookRxService;
import com.ms.rxservices.GitHubRxService;
import com.ms.utils.Futures;
import com.ms.utils.TaskExecutor;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//import jersey.repackaged.jsr166e.CompletableFuture;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import org.glassfish.jersey.server.ManagedAsync;


@Path("/msrx")
@Produces("application/json")
public class RxResource {

    @Uri("/userInfo/{user}")
    private WebTarget user;
    
    @Uri("/contributors/{user}")
    private WebTarget contributor;
    
    @Inject
    private FacebookRxService facebookRxService;
    
    @Inject
    private GitHubRxService gitHubRxService;
    
    @Inject
    private TaskExecutor executor;

    @GET
    @ManagedAsync
    @Path("/userInfo/{user}")
    @Produces(MediaType.APPLICATION_JSON) 
    public void observableUserInfo(@Suspended final AsyncResponse asyncResponse, @PathParam("user") String user) {
        final long timeInitial = System.nanoTime();
        
        CompletableFuture<GitHubUser> gitHubFuture = Futures.toCompletable(gitHubRxService.userRx(user), executor);
        CompletableFuture<FacebookUser> facebookFuture = Futures.toCompletable(facebookRxService.userRx(user), executor);
        
        gitHubFuture
                .thenCombine(
                        facebookFuture, (g, f) -> new UserInfo(f, g))
                .thenApply(
                        info -> asyncResponse.resume(info))
                .exceptionally(
                        e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));
        
        asyncResponse.setTimeout(1000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(
                ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));
        
       final long timeResult = (System.nanoTime() - timeInitial) / 1000000;
    }
    @GET
    @ManagedAsync
    @Path("/contributors/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public void observableContributor(@Suspended final AsyncResponse asyncResponse, @PathParam("user") String user) {
        final long time = System.nanoTime();
        
        Futures.toCompletable(gitHubRxService.reposRx(user), executor)
                .thenCompose(
                        repos -> getContributors(user, repos))
                .thenApply(
                        contributors -> contributors.flatMap(list -> list.stream()))
                .thenApply(
                        contributors -> contributors.collect(Collectors.groupingBy(
                                c -> c.getLogin(),
                                Collectors.counting())))
                .thenApply(
                        contributors -> asyncResponse.resume(contributors))
                .exceptionally(
                        e -> asyncResponse.resume(
                                Response.status(INTERNAL_SERVER_ERROR).entity(e).build())
                );
        
    }
    
    private CompletableFuture<Stream<List<GitHubContributor>>> getContributors(String user, List<GitHubRepo> repos) {
        return Futures.sequence(
                repos.stream().limit(5).map(r -> Futures.toCompletable(gitHubRxService.contributorsRx(user, r.getName()), executor)));
    }

}