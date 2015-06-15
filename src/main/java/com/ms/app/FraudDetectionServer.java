package com.ms.app;


import com.google.common.util.concurrent.AbstractIdleService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import java.util.LinkedHashSet;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import java.util.Set;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import com.ms.utils.TaskExecutor;
import com.ms.services.*;

public final class FraudDetectionServer extends AbstractIdleService{

    private static final int DEFAULT_PORT = 9300;
    private static final String BASE_URI = "http://localhost";
    private final HttpServer httpServer;
    private static final int SHUTDOWN_TIME = 5;

    
    public FraudDetectionServer() throws Exception {
        URI endpoint = new URI(BASE_URI + ":" + DEFAULT_PORT);
        httpServer = GrizzlyHttpServerFactory.createHttpServer(endpoint, getResourceConfig());
    }

    
    @Override
    protected void startUp() throws Exception {
        httpServer.start();
        System.out.println("Server started on port " + DEFAULT_PORT + ".");
    }

    @Override
    protected void shutDown() throws Exception {
        httpServer.shutdown(SHUTDOWN_TIME, TimeUnit.SECONDS);
    }

    public static void main(final String[] args) throws Exception{
        System.out.println("HTTP hello world server starting ...");
        new FraudDetectionServer().startAndWait();
        
    }
    
    public static ResourceConfig getResourceConfig() {
        FraudDetectionApplication app = new FraudDetectionApplication();
        Set<Class<?>> resources = new LinkedHashSet<>(app.getClasses());
        resources.add(JacksonJsonProvider.class);
        ResourceConfig resourceConfig = new ResourceConfig(resources);
        
        resourceConfig.packages("com.ms.api");
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(FacebookService.class).to(FacebookService.class);
                bind(GitHubService.class).to(GitHubService.class);
                bind(TaskExecutor.class).to(TaskExecutor.class);
            }
        });
        return resourceConfig;
    }
}
