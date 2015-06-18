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

//    private Client client = ClientBuilder.newClient();
//    WebTarget target = client.target("http://graph.facebook.com/");
    
    //RxWebTarget<RxObservableInvoker> rxTarget = Rx.from(target, RxObservableInvoker.class);
    
    
    /*
    https://jersey.java.net/documentation/latest/rx-client.html
    */
//    public Observable<FacebookUser> userRx(String user) {
//        //return rxTarget
//        return RxObservable.from(rxTarget)
//                .path("/{user}")
//                .resolveTemplate("user", user)
//                .request()
//                .rx()
//                .get(FacebookUser.class)
//                .onErrorReturn(throwable -> {
//                    //errors.offer("Recommended: " + throwable.getMessage());
//                    System.out.println(throwable.getMessage());
//                    return new FacebookUser("", "", "");
//                });
//    }
    
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
