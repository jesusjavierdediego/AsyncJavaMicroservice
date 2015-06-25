package com.ms.api;


import com.ms.app.MSApplication;
import com.ms.domain.GitHubContributor;
import com.ms.domain.GitHubRepo;
import com.ms.domain.GitHubUser;
import com.ms.domain.JSONPlaceholderItem;
import com.ms.domain.UserInfo;
import com.ms.rxservices.GitHubRxService;
import com.ms.rxservices.JSONPlaceholderRxService;
import com.ms.utils.Futures;
import com.ms.utils.TaskExecutor;
import com.ms.utils.TimeFinal;
import com.ms.utils.Utils;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import org.glassfish.jersey.server.ManagedAsync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/msrx")
@Produces(MediaType.APPLICATION_JSON)
public class RxResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(RxResource.class);
    
    private final JSONPlaceholderRxService jSONPlaceholderRxService = new JSONPlaceholderRxService();
    private final GitHubRxService gitHubRxService = new GitHubRxService();
    private final TaskExecutor executor = new TaskExecutor();

    @GET
    @ManagedAsync
    @Path("/userInfo/{user}")
    @Produces(MediaType.APPLICATION_JSON) 
    public void observableUserInfo(@Suspended final AsyncResponse asyncResponse, @PathParam("user") String user) {
        final long timeInitial = System.nanoTime();
        
        CompletableFuture<GitHubUser> gitHubFuture = Futures.toCompletable(gitHubRxService.userRx(user), executor);
        CompletableFuture<JSONPlaceholderItem> jsonItemFuture = 
                Futures.toCompletable(jSONPlaceholderRxService.itemRx(Utils.getRandom().toString()), executor);
        //final Long time = (System.nanoTime() - timeInitial) / 1000000;
        
        gitHubFuture
                .thenCombine(
                        jsonItemFuture, (g, f) -> new UserInfo(f, g))
                .thenApply(
                        info -> asyncResponse.resume(info))
                .exceptionally(
                        e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));
        

        asyncResponse.setTimeout(Integer.parseInt(MSApplication.properties.getProperty("timeout.milliseconds")), TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(
                ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity(MSApplication.properties.getProperty("timeout.message")).build()));
        
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