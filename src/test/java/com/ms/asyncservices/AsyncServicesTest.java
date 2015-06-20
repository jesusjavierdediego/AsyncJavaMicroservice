package com.ms.asyncservices;



import com.ms.app.MSApplication;
import com.ms.domain.UserInfo;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;

import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

/**
 * Invoke clients in Agent part of the application.
 *
 * @author Michal Gajdos (michal.gajdos at oracle.com)
 */
public class AsyncServicesTest extends JerseyTest {

    @Override
    protected DeploymentContext configureDeployment() {
        return ServletDeploymentContext.builder(MSApplication.class).contextPath("/").build();
    }
    
     @Path("hello")
    public static class HelloResource {
        @GET
        public String getHello() {
            return "Hello World!";
        }
    }
 
    @Override
    protected Application configure() {
        return new ResourceConfig(MSApplication.class);
    }
 
    @Test
    public void test() {
        final Response response = target("/msa/userInfo/carloslozano").request().get();
        response.bufferEntity();
        final UserInfo userInfo = response.readEntity(UserInfo.class);

        System.out.println("userInfo: " + userInfo);
        assertThat(userInfo.getGitHubUser().getType(), is("User"));
        assertThat(userInfo.getJSONplaceholderItem().getUserId(), is(1));
    }
    

//    @Test
//    public void testSyncClient() throws Exception {
//        // warmup
//        target("http://localhost:9300").path("/msa/userInfo/carloslozano").request().get();
//
//        final Response response = target("http://localhost:9300").path("/msa/userInfo/carloslozano").request().get();
//        response.bufferEntity();
//
//        final UserInfo userInfo = response.readEntity(UserInfo.class);
//
//        System.out.println("userInfo: " + userInfo);
//        assertThat(userInfo.getGitHubUser().getType(), is("User"));
//        assertThat(userInfo.getJSONplaceholderItem().getUserId(), is(1));

//        assertThat(userInfo.getProcessingTime() > 4500, is(true));
//
//        System.out.println(response.readEntity(String.class));
//        System.out.println("Processing Time: " + userInfo.getProcessingTime());
//  }
//
//    @Test
//    public void testAsyncClient() throws Exception {
//        // warmup
//        target("agent").path("async").request().get();
//
//        final Response response = target("agent").path("async").request().get();
//        response.bufferEntity();
//
//        final AgentResponse agentResponse = response.readEntity(AgentResponse.class);
//
//        assertThat(agentResponse.getVisited().size(), is(5));
//        assertThat(agentResponse.getRecommended().size(), is(5));
//
//        assertThat(agentResponse.getProcessingTime() > 850, is(true));
//        assertThat(agentResponse.getProcessingTime() < 4500, is(true));
//
//        System.out.println(response.readEntity(String.class));
//        System.out.println("Processing Time: " + agentResponse.getProcessingTime());
//    }
//
//    @Test
//    public void testRxObservableClient() throws Exception {
//        // warmup
//        target("agent").path("observable").request().get();
//
//        final Response response = target("agent").path("observable").request().get();
//        response.bufferEntity();
//
//        final AgentResponse agentResponse = response.readEntity(AgentResponse.class);
//
//        assertThat(agentResponse.getVisited().size(), is(5));
//        assertThat(agentResponse.getRecommended().size(), is(5));
//
//        assertThat(agentResponse.getProcessingTime() > 850, is(true));
//        assertThat(agentResponse.getProcessingTime() < 4500, is(true));
//
//        System.out.println(response.readEntity(String.class));
//        System.out.println("Processing Time: " + agentResponse.getProcessingTime());
//    }
//
//    @Test
//    public void testRxCompletionStageClient() throws Exception {
//        // warmup
//        target("agent").path("completion").request().get();
//
//        final Response response = target("agent").path("completion").request().get();
//        response.bufferEntity();
//
//        final AgentResponse agentResponse = response.readEntity(AgentResponse.class);
//
//        assertThat(agentResponse.getVisited().size(), is(5));
//        assertThat(agentResponse.getRecommended().size(), is(5));
//
//        assertThat(agentResponse.getProcessingTime() > 850, is(true));
//        assertThat(agentResponse.getProcessingTime() < 4500, is(true));
//
//        System.out.println(response.readEntity(String.class));
//        System.out.println("Processing Time: " + agentResponse.getProcessingTime());
//    }
//
//    @Test
//    public void testHystrixRxObservableClient() throws Exception {
//        // warmup
//        target("agent").path("hystrix").request().get();
//
//        final Response response = target("agent").path("hystrix").request().get();
//        response.bufferEntity();
//
//        final AgentResponse agentResponse = response.readEntity(AgentResponse.class);
//
//        assertThat(agentResponse.getVisited().size(), is(5));
//        assertThat(agentResponse.getRecommended().size(), is(5));
//
//        assertThat(agentResponse.getProcessingTime() > 850, is(true));
//        assertThat(agentResponse.getProcessingTime() < 950, is(true));
//
//        System.out.println(response.readEntity(String.class));
//        System.out.println("Processing Time: " + agentResponse.getProcessingTime());
//    }

}