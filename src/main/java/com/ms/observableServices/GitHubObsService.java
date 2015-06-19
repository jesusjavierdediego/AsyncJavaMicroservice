package com.ms.observableServices;


import org.jvnet.hk2.annotations.Service;

import com.ms.domain.GitHubContributor;
import com.ms.domain.GitHubRepo;
import com.ms.domain.GitHubUser;

import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.rx.Rx;
import org.glassfish.jersey.client.rx.RxWebTarget;
import org.glassfish.jersey.client.rx.rxjava.RxObservable;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvoker;

import rx.Observable;

@Service
public class GitHubObsService {

    
    private final WebTarget target = ClientBuilder.newClient().target("http://graph.facebook.com/");
    
    private final  RxWebTarget<RxObservableInvoker> rxTarget = Rx.from(target, RxObservableInvoker.class);


    public Observable<GitHubUser> userObs(String user) {

        return RxObservable.from(rxTarget)
            .path("/users/{user}")
            .resolveTemplate("user", user)
            .request(MediaType.APPLICATION_JSON_TYPE)
            .rx()
            .get(GitHubUser.class);
    }

    public Observable<List<GitHubRepo>> reposObs(String user) {
        return RxObservable.from(rxTarget)
                .path("users/{user}/repos")
                .resolveTemplate("user", user)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .rx()
                .get(new GenericType<List<GitHubRepo>>() { });
    }

    public Observable<List<GitHubContributor>> contributorsObs(String owner, String repo) {
        return RxObservable.from(rxTarget)
                .path("/repos/{owner}/{repo}/contributors")
                .resolveTemplate("owner", owner)
                .resolveTemplate("repo", repo)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .rx()
                .get(new GenericType<List<GitHubContributor>>() { });
    }
}
