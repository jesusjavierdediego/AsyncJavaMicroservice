package com.ms.api;


import com.ms.app.MSApplication;
import com.ms.domain.GitHubRepo;
import com.ms.utils.Futures;
import com.ms.asyncservices.GitHubAsyncService;
import com.ms.asyncservices.JSONPlaceholderAsyncService;
import com.ms.utils.TaskExecutor;
import com.ms.domain.GitHubContributor;
import com.ms.domain.Identity;
import com.ms.domain.JSONPlaceholderItem;
import com.ms.domain.UserInfo;
import com.ms.utils.Utils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.glassfish.jersey.server.ManagedAsync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/msa")
@Produces("application/json")
public class AsyncResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncResource.class);
    private final GitHubAsyncService gitHubService = new GitHubAsyncService();
    private final JSONPlaceholderAsyncService jSONPlaceholderService = new JSONPlaceholderAsyncService();
    private final TaskExecutor executor = new TaskExecutor();

    
    @GET
    @Path("/userInfo/{user}")
    @Produces(MediaType.APPLICATION_JSON) 
    @ManagedAsync
    public void userInfoAsync(@Suspended final AsyncResponse asyncResponse, @PathParam("user") String user) {
        final long time = System.nanoTime();
        CompletableFuture<Identity> gitHubFuture = Futures.toCompletable(gitHubService.userAsync(user), executor);
        
        CompletableFuture<JSONPlaceholderItem> jsonItemFuture = 
                Futures.toCompletable(jSONPlaceholderService.itemAsync(Utils.getRandom().toString()), executor);

        gitHubFuture
                .thenCombine(
                        jsonItemFuture, (g, f) -> new UserInfo(f, g))
                .thenApply(
                        info -> asyncResponse.resume(info))
                .exceptionally(
                        e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(1000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(
                ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));
    }

    @GET
    @Path("/contributors/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void contributorsAsync(@Suspended final AsyncResponse asyncResponse, @PathParam("user") String user) {
        final long time = System.nanoTime();
        Futures.toCompletable(gitHubService.reposAsync(user), executor)
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
        final int streamLimit = Integer.parseInt(MSApplication.properties.getProperty("application.stream.limit"));
        return Futures.sequence(
                repos.stream().limit(streamLimit).map(r -> Futures.toCompletable(gitHubService.contributorsAsync(user, r.getName()), executor)));
    }

}