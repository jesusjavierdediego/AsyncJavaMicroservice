package com.ms.observableServices;



import com.ms.app.MSApplication;
import com.ms.domain.JSONPlaceholderItem;

import org.jvnet.hk2.annotations.Service;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import rx.Observable;
import org.glassfish.jersey.client.rx.*;
import org.glassfish.jersey.client.rx.rxjava.RxObservable;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvoker;


@Service
public class JSONPlaceholderObsService {
    

    private final WebTarget target = ClientBuilder.newClient().target(MSApplication.properties.getProperty("endpoints.url.jsonplaceholder"));
    
    private final RxWebTarget<RxObservableInvoker> rxTarget = Rx.from(target, RxObservableInvoker.class);
    

    public Observable<JSONPlaceholderItem> itemObs(String itemId) {
        return RxObservable.from(rxTarget)
                .path("/{itemId}")
                .resolveTemplate("itemId", itemId)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .rx()
                .get(JSONPlaceholderItem.class);
                
    }
   
    
}
