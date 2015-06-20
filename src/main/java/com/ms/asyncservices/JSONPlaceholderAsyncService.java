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
//        System.out.println("URL: " + MSApplication.properties.getProperty("endpoints.url.jsonplaceholder"));
//        System.out.println("param: "  + itemId);
        return target
                .path("/{itemId}")
                .resolveTemplate("itemId", itemId)
                .request()
                .async()
                .get(new InvocationCallback<JSONPlaceholderItem>() {
                    @Override
                    public void completed(JSONPlaceholderItem jSONplaceholderItem) {
                          //Callback: Do subsequent action when completed 
                        //Nest any other actions
                        System.out.println("SUCCESS JSON Item ");
                    }

                    @Override
                    public void failed(Throwable t) {
                           //throw exception and log event
                        System.out.println("FAILED JSON Item " );
                        t.printStackTrace();
                        
                    }
                });
    }
}
