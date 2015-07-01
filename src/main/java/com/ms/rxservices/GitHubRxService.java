package com.ms.rxservices;

import com.ms.app.MSApplication;

import org.jvnet.hk2.annotations.Service;
import com.ms.domain.GitHubContributor;
import com.ms.domain.GitHubRepo;
import com.ms.domain.Identity;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

import jersey.repackaged.jsr166e.CompletableFuture;
import org.glassfish.jersey.client.rx.jsr166e.RxCompletableFuture;

@Service
public class GitHubRxService {

    private final WebTarget target = RxCompletableFuture.newClient().target(MSApplication.properties.getProperty("endpoints.url.github"));


    public CompletableFuture<Identity> userRx(String user) {

        return RxCompletableFuture.from(target)
            .path("/users/{user}")
            .resolveTemplate("user", user)
            .request(MediaType.APPLICATION_JSON_TYPE)
            .rx()
            .get(Identity.class);
    }

    public CompletableFuture<List<GitHubRepo>> reposRx(String user) {
        return RxCompletableFuture.from(target)
                .path("users/{user}/repos")
                .resolveTemplate("user", user)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .rx()
                .get(new GenericType<List<GitHubRepo>>() { });
    }

    public CompletableFuture<List<GitHubContributor>> contributorsRx(String owner, String repo) {
        return RxCompletableFuture.from(target)
                .path("/repos/{owner}/{repo}/contributors")
                .resolveTemplate("owner", owner)
                .resolveTemplate("repo", repo)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .rx()
                .get(new GenericType<List<GitHubContributor>>() { });
    }
}
