package com.ms.rxservices;


import com.ms.app.MSApplication;
import com.ms.domain.JSONPlaceholderItem;

import org.jvnet.hk2.annotations.Service;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.rx.jsr166e.RxCompletableFuture;
import jersey.repackaged.jsr166e.CompletableFuture;

@Service
public class JSONPlaceholderRxService {

    private final WebTarget target = RxCompletableFuture.newClient().target(MSApplication.properties.getProperty("endpoints.url.jsonplaceholder"));
    
    public CompletableFuture<JSONPlaceholderItem> itemRx(String itemId) {

        return RxCompletableFuture.from(target)
            .path("/{itemId}")
            .resolveTemplate("itemId", itemId)
            .request(MediaType.APPLICATION_JSON_TYPE)
            .rx()
            .get(JSONPlaceholderItem.class);
    }
}
