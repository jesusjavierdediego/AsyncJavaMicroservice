package com.ms.observableServices;


import com.ms.domain.FacebookUser;

import org.jvnet.hk2.annotations.Service;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import rx.Observable;
import org.glassfish.jersey.client.rx.*;
import org.glassfish.jersey.client.rx.rxjava.RxObservable;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvoker;


@Service
public class FacebookObsService {

    private Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://graph.facebook.com/");
    
    RxWebTarget<RxObservableInvoker> rxTarget = Rx.from(target, RxObservableInvoker.class);
    
    
    /*
    https://jersey.java.net/documentation/latest/rx-client.html
    */
    public Observable<FacebookUser> userObs(String user) {
        //return rxTarget
        return RxObservable.from(rxTarget)
                .path("/{user}")
                .resolveTemplate("user", user)
                .request()
                .rx()
                .get(FacebookUser.class)
                .onErrorReturn(throwable -> {
                    //errors.offer("Recommended: " + throwable.getMessage());
                    System.out.println(throwable.getMessage());
                    return new FacebookUser("", "", "");
                });
    }
   
    
}
