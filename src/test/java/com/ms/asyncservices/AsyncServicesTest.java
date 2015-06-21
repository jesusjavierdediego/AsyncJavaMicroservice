/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ms.asyncservices;

import com.ms.app.MSApplication;
import com.ms.domain.UserInfo;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.ServletDeploymentContext;

/**
 *
 * @author root
 */
public class AsyncServicesTest extends JerseyTest{
    
    @Override
    protected DeploymentContext configureDeployment() {
        return ServletDeploymentContext.builder(MSApplication.class).contextPath("/").build();
    }
    
    @Test
    public void testSyncClient() throws Exception {
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
    
}
