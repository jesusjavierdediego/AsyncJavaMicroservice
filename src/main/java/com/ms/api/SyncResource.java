package com.ms.api;

import com.ms.syncservices.GitHubSyncService;
import com.ms.syncservices.JSONPlaceholderSyncService;

import com.ms.domain.GitHubUser;
import com.ms.domain.JSONPlaceholderItem;
import com.ms.domain.UserInfo;
import com.ms.utils.Utils;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ms")
@Produces("application/json")
public class SyncResource {


    @Inject
    private GitHubSyncService gitHubService;
    
    @Inject
    private JSONPlaceholderSyncService jSONPlaceholderService;
    

    @GET
    @Path("/userInfo/{user}")
    @Produces(MediaType.APPLICATION_JSON) 
    public UserInfo userInfoSync(Response response, @PathParam("user") String user) {
        final long time = System.nanoTime();
        GitHubUser gitHubUser = gitHubService.userSync(user);
        JSONPlaceholderItem jsonItem = jSONPlaceholderService.itemSync(Utils.getRandom().toString());
        
        return new UserInfo(jsonItem, gitHubUser);
        
    }

}