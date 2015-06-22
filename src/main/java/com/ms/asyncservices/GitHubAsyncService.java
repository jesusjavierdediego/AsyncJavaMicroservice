package com.ms.asyncservices;

import com.ms.app.MSApplication;

import org.jvnet.hk2.annotations.Service;
import com.ms.domain.GitHubContributor;
import com.ms.domain.GitHubRepo;
import com.ms.domain.GitHubUser;


import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.Future;


@Service
public class GitHubAsyncService {
    
    private final WebTarget target = ClientBuilder.newClient().target(MSApplication.properties.getProperty("endpoints.url.github"));

    public Future<GitHubUser> userAsync(String user) {
        return target
                .path("/users/{user}")
                .resolveTemplate("user", user)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .async()
                .get(GitHubUser.class);
    }

    public Future<List<GitHubRepo>> reposAsync(String user) {
        return target
                .path("users/{user}/repos")
                .resolveTemplate("user", user)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .async()
                .get(new GenericType<List<GitHubRepo>>() { });
    }

    public Future<List<GitHubContributor>> contributorsAsync(String owner, String repo) {
        return target
                .path("/repos/{owner}/{repo}/contributors")
                .resolveTemplate("owner", owner)
                .resolveTemplate("repo", repo)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .async()
                .get(new GenericType<List<GitHubContributor>>() { });
    }
}
