package com.ms.asyncservices;


import com.ms.app.MSApplication;

import org.jvnet.hk2.annotations.Service;
import com.ms.domain.JSONPlaceholderItem;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import java.util.concurrent.Future;

@Service
public class JSONPlaceholderAsyncService {

    private final WebTarget target = ClientBuilder.newClient().target(MSApplication.properties.getProperty("endpoints.url.jsonplaceholder"));
    
    public Future<JSONPlaceholderItem> itemAsync(String itemId) {
        return target
                .path("/{itemId}")
                .resolveTemplate("itemId", itemId)
                .request()
                .async()
                //As an example, how to include callback handler...
                .get(new InvocationCallback<JSONPlaceholderItem>() {
                    @Override
                    public void completed(JSONPlaceholderItem jSONplaceholderItem) {
                         //Callback: Do subsequent action when completed  
                    }

                    @Override
                    public void failed(Throwable t) {
                           //throw exception and log event
                    }
                });
    }
}
