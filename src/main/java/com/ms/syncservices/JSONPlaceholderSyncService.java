package com.ms.syncservices;


import com.ms.app.MSApplication;
import org.jvnet.hk2.annotations.Service;
import com.ms.domain.JSONPlaceholderItem;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;


@Service
public class JSONPlaceholderSyncService {

    private final WebTarget target = ClientBuilder.newClient().target(MSApplication.properties.getProperty("endpoints.url.jsonplaceholder"));
    
    public JSONPlaceholderItem itemSync(String itemId) {

        return target
                .path("/{itemId}")
                .resolveTemplate("itemId", itemId)
                .request()
                .get(JSONPlaceholderItem.class);
    }
}
