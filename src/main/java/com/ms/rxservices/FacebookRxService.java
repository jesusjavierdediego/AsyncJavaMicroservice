package com.ms.rxservices;


import com.ms.domain.FacebookUser;
import org.jvnet.hk2.annotations.Service;

import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.rx.*;
import org.glassfish.jersey.client.rx.jsr166e.RxCompletableFuture;
import org.glassfish.jersey.client.rx.jsr166e.RxCompletableFutureInvoker;
import jersey.repackaged.jsr166e.CompletableFuture;

@Service
public class FacebookRxService {

    RxClient<RxCompletableFutureInvoker> newRxClient = RxCompletableFuture.newClient();
    private final WebTarget target = newRxClient.target("http://graph.facebook.com/");
    
    public CompletableFuture<FacebookUser> userRx(String user) {

        return RxCompletableFuture.from(target)
            .resolveTemplate("user", user)
            .request()
            .rx()
            .get(FacebookUser.class);
    }
}
