package com.ms;

import com.ms.app.MSApplication;
import com.ms.domain.UserInfo;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;


public class ServicesTest extends JerseyTest{
    @Override
    protected DeploymentContext configureDeployment() {
        return ServletDeploymentContext.builder(MSApplication.class).contextPath("/").build();
    }
    
    @Test
    public void testSyncClient() throws Exception {
        // warmup
        target("ms").path("userInfo/jesusjavierdediego").request().get();

        final Response response = target("ms").path("userInfo/jesusjavierdediego").request(MediaType.APPLICATION_JSON).get();
        response.bufferEntity();
        
        final UserInfo userInfoResponse = response.readEntity(UserInfo.class);

        assertThat(userInfoResponse.getGitHubUser().getName(), is("Jesus de Diego"));
        assertThat(userInfoResponse.getJSONplaceholderItem().getUserId(), is("1"));

        //assertThat(userInfoResponse.getProcessingTime() > 4500, is(true));

        System.out.println(response.readEntity(UserInfo.class));
    }
    
    @Test
    public void testAsyncClient() throws Exception {
        // warmup
        target("msa").path("userInfo/jesusjavierdediego").request().get();

        final Response response = target("msa").path("userInfo/jesusjavierdediego").request(MediaType.APPLICATION_JSON).get();
        response.bufferEntity();
        
        final UserInfo userInfoResponse = response.readEntity(UserInfo.class);

        assertThat(userInfoResponse.getGitHubUser().getName(), is("Jesus de Diego"));
        assertThat(userInfoResponse.getJSONplaceholderItem().getUserId(), is("1"));

        //assertThat(userInfoResponse.getProcessingTime() > 4500, is(true));

        System.out.println(response.readEntity(UserInfo.class));
    }
    
    @Test
    public void testRxClient() throws Exception {
        // warmup
        target("msrx").path("userInfo/jesusjavierdediego").request().get();

        final Response response = target("msrx").path("userInfo/jesusjavierdediego").request(MediaType.APPLICATION_JSON).get();
        response.bufferEntity();
        
        final UserInfo userInfoResponse = response.readEntity(UserInfo.class);

        assertThat(userInfoResponse.getGitHubUser().getName(), is("Jesus de Diego"));
        assertThat(userInfoResponse.getJSONplaceholderItem().getUserId(), is("1"));

        //assertThat(userInfoResponse.getProcessingTime() > 4500, is(true));

        System.out.println(response.readEntity(UserInfo.class));
    }
    
    @Test
    public void testRxObsClient() throws Exception {
        // warmup
        target("msobs").path("userInfo/jesusjavierdediego").request().get();

        final Response response = target("msobs").path("userInfo/jesusjavierdediego").request(MediaType.APPLICATION_JSON).get();
        response.bufferEntity();
        
        final UserInfo userInfoResponse = response.readEntity(UserInfo.class);

        assertThat(userInfoResponse.getGitHubUser().getName(), is("Jesus de Diego"));
        assertThat(userInfoResponse.getJSONplaceholderItem().getUserId(), is("1"));

        //assertThat(userInfoResponse.getProcessingTime() > 4500, is(true));

        System.out.println(response.readEntity(UserInfo.class));
    }
    
    
}
