package com.ms.api;

import com.ms.syncservices.GitHubSyncService;
import com.ms.syncservices.JSONPlaceholderSyncService;

import com.ms.domain.GitHubUser;
import com.ms.domain.JSONPlaceholderItem;
import com.ms.domain.UserInfo;
import com.ms.utils.Utils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/ms")
@Produces("application/json")
public class SyncResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SyncResource.class);
    private final JSONPlaceholderSyncService jSONPlaceholderService = new JSONPlaceholderSyncService();
    private final GitHubSyncService gitHubService = new GitHubSyncService();

    @GET
    @Path("/userInfo/{user}")
    @Produces(MediaType.APPLICATION_JSON) 
    public UserInfo userInfoSync(Response response, @PathParam("user") String user) {
        final long timeInitial = System.nanoTime();
        GitHubUser gitHubUser = gitHubService.userSync(user);
        JSONPlaceholderItem jsonItem = jSONPlaceholderService.itemSync(Utils.getRandom().toString());
        UserInfo ui = new UserInfo(jsonItem, gitHubUser);
        
        LOGGER.debug("Synchronous operation performed in: " + (System.nanoTime() - timeInitial) / 1000000 + " ms");
        return ui;
        
    }

}