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
    
//     @Override
//    protected TestContainerFactory getTestContainerFactory() {
//        return new GrizzlyWebTestContainerFactory();
//    }
    
//    @Override
//    protected DeploymentContext configureDeployment() {
//        //return ServletDeploymentContext.builder(MSApplication.class).contextPath("/").build();
//        ResourceConfig config = new ResourceConfig(AsyncResource.class);
//        config.register(new AbstractBinder() {
//            @Override
//            protected void configure() {
//                
//                bind(GitHubAsyncService.class).to(GitHubAsyncService.class);
//                bind(JSONPlaceholderAsyncService.class).to(JSONPlaceholderAsyncService.class);
//                bind(TaskExecutor.class).to(TaskExecutor.class);
//                bind(UserInfo.class).to(UserInfo.class);
//            }
//        });
//        //return ServletDeploymentContext.forServlet(new ServletContainer(config)).build();
//        //return ServletDeploymentContext.newInstance(config);
//        return ServletDeploymentContext.builder(MSApplication.class).contextPath("/").build();
//        
//    }
    @Test
    public void testAsyncClient() throws Exception {
        // warmup
        target("msa").path("userInfo/carloslozano").request().get();

        final Response response = target("msa").path("userInfo/carloslozano").request(MediaType.APPLICATION_JSON).get();
        response.bufferEntity();
        
        final UserInfo userInfoResponse = response.readEntity(UserInfo.class);

        assertThat(userInfoResponse.getGitHubUser().getName(), is("Carlos Lozano"));
        //assertThat(agentResponse.getJSONplaceholderItem().getId(), is(>10));

        //assertThat(agentResponse.getProcessingTime() > 4500, is(true));

        System.out.println(response.readEntity(UserInfo.class));
        //System.out.println("Processing Time: " + agentResponse.getProcessingTime());
    }
    
    @Test
    public void testRxSyncClient() throws Exception {
        // warmup
        target("msrx").path("userInfo/carloslozano").request().get();

        final Response response = target("msrx").path("userInfo/carloslozano").request(MediaType.APPLICATION_JSON).get();
        response.bufferEntity();
        
        final UserInfo userInfoResponse = response.readEntity(UserInfo.class);

        assertThat(userInfoResponse.getGitHubUser().getName(), is("Carlos Lozano"));
        //assertThat(agentResponse.getJSONplaceholderItem().getId(), is(>10));

        //assertThat(agentResponse.getProcessingTime() > 4500, is(true));

        System.out.println(response.readEntity(UserInfo.class));
        //System.out.println("Processing Time: " + agentResponse.getProcessingTime());
    }
    
    @Test
    public void testObsClient() throws Exception {
        // warmup
        target("msobs").path("userInfo/carloslozano").request().get();

        final Response response = target("msobs").path("userInfo/carloslozano").request(MediaType.APPLICATION_JSON).get();
        response.bufferEntity();
        
        final UserInfo userInfoResponse = response.readEntity(UserInfo.class);

        assertThat(userInfoResponse.getGitHubUser().getName(), is("Carlos Lozano"));
        //assertThat(agentResponse.getJSONplaceholderItem().getId(), is(>10));

        //assertThat(agentResponse.getProcessingTime() > 4500, is(true));

        System.out.println(response.readEntity(UserInfo.class));
        //System.out.println("Processing Time: " + agentResponse.getProcessingTime());
    }
    
    @Test
    public void testSyncClient() throws Exception {
        // warmup
        target("ms").path("userInfo/carloslozano").request().get();

        final Response response = target("ms").path("userInfo/carloslozano").request(MediaType.APPLICATION_JSON).get();
        response.bufferEntity();
        
        final UserInfo userInfoResponse = response.readEntity(UserInfo.class);

        assertThat(userInfoResponse.getGitHubUser().getName(), is("Carlos Lozano"));
        //assertThat(agentResponse.getJSONplaceholderItem().getId(), is(>10));

        //assertThat(agentResponse.getProcessingTime() > 4500, is(true));

        System.out.println(response.readEntity(UserInfo.class));
        //System.out.println("Processing Time: " + agentResponse.getProcessingTime());
    }
}
