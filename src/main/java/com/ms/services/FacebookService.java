package com.ms.services;

import org.jvnet.hk2.annotations.Service;
import com.ms.domain.FacebookUser;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import java.util.concurrent.Future;

@Service
public class FacebookService {

    private final WebTarget target = ClientBuilder.newClient()
            .target("http://graph.facebook.com/");
    /*
    https://jersey.java.net/documentation/latest/rx-client.html
    */
    public Future<FacebookUser> userAsync(String user) {
        return target
                .path("/{user}")
                .resolveTemplate("user", user)
                .request()
                .async()
                .get(new InvocationCallback<FacebookUser>() {
                    @Override
                    public void completed(FacebookUser facebookUser) {
                          //Callback: Do subsequent action when completed 
                        //Nest any other actions
                    }

                    @Override
                    public void failed(Throwable throwable) {
                           //throw exception and log event
                    }
                });
    }
}
