package com.ms.api;


import com.ms.asyncservices.FacebookService;
import com.ms.domain.GitHubRepo;
import com.ms.utils.Futures;
import com.ms.asyncservices.GitHubService;
import com.ms.utils.TaskExecutor;
import com.ms.domain.FacebookUser;
import com.ms.domain.GitHubContributor;
import com.ms.domain.GitHubUser;
import com.ms.domain.UserInfo;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import org.glassfish.jersey.server.ManagedAsync;

@Path("/msa")
@Produces("application/json")
public class AsyncResource {

    @Inject
    private FacebookService facebookService;

    @Inject
    private GitHubService gitHubService;

    @Inject
    private TaskExecutor executor;

    @GET
    @Path("/userInfo/{user}")
    @Produces(MediaType.APPLICATION_JSON) 
    @ManagedAsync
    public void userInfoAsync(@Suspended final AsyncResponse asyncResponse, @PathParam("user") String user) {
        final long time = System.nanoTime();
        CompletableFuture<GitHubUser> gitHubFuture = Futures.toCompletable(gitHubService.userAsync(user), executor);
        CompletableFuture<FacebookUser> facebookFuture = Futures.toCompletable(facebookService.userAsync(user), executor);

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
        return Futures.sequence(
                repos.stream().limit(5).map(r -> Futures.toCompletable(gitHubService.contributorsAsync(user, r.getName()), executor)));
    }

}