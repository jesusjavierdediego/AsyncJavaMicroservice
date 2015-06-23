package com.ms.app;

import com.ms.asyncservices.GitHubAsyncService;
import com.google.common.util.concurrent.AbstractIdleService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import java.util.LinkedHashSet;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.ms.asyncservices.JSONPlaceholderAsyncService;
import com.ms.observableServices.GitHubObsService;
import com.ms.observableServices.JSONPlaceholderObsService;
import com.ms.rxservices.GitHubRxService;
import com.ms.rxservices.JSONPlaceholderRxService;
import com.ms.syncservices.GitHubSyncService;
import com.ms.syncservices.JSONPlaceholderSyncService;
import com.ms.utils.CORSFilter;
import org.glassfish.jersey.server.ResourceConfig;
import java.util.Set;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ms.utils.TaskExecutor;


public final class MSServer extends AbstractIdleService {
    /*
    LOG.trace("Hello World!");
LOG.debug("How are you today?");
LOG.info("I am fine.");
LOG.warn("I love programming.");
LOG.error("I am programming.");
    */
    private static final Logger LOGGER = LoggerFactory.getLogger(MSServer.class);

    //private static final int DEFAULT_PORT = Integer.parseInt(MSApplication.properties.getProperty("service.url.port"));
    private static final int DEFAULT_PORT = Integer.parseInt("9300");

    //private static final String BASE_URI = MSApplication.properties.getProperty("service.url.base");
    private static final String BASE_URI = "http://localhost";
    //private static final int SHUTDOWN_TIME = Integer.parseInt(MSApplication.properties.getProperty("application.time.shutdown"));
    private static final int SHUTDOWN_TIME = Integer.parseInt("5");
    private final HttpServer httpServer;
    
    

    public MSServer() throws Exception {
        //https://grizzly.java.net/
        URI endpoint = new URI(BASE_URI + ":" + DEFAULT_PORT);
        httpServer = GrizzlyHttpServerFactory.createHttpServer(endpoint, getResourceConfig());
    }

    @Override
    protected void startUp() throws Exception {
        httpServer.start();
        LOGGER.debug("Server started on port  {}", DEFAULT_PORT);
    }

    @Override
    protected void shutDown() throws Exception {
        httpServer.shutdown(SHUTDOWN_TIME, TimeUnit.SECONDS);
    }

    public static void main(final String[] args) throws Exception {
        LOGGER.debug("HTTP hello world server starting ...");
        new MSServer().startAndWait();

    }

    public static ResourceConfig getResourceConfig() {
        MSApplication app = new MSApplication();
        Set<Class<?>> resources = new LinkedHashSet<>(app.getClasses());
        resources.add(JacksonJsonProvider.class);
        ResourceConfig resourceConfig = new ResourceConfig(resources);

        resourceConfig.packages("com.ms.api");
        resourceConfig.register(CORSFilter.class);
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(TaskExecutor.class).to(TaskExecutor.class);

                bind(JSONPlaceholderSyncService.class).to(JSONPlaceholderSyncService.class);
                bind(JSONPlaceholderAsyncService.class).to(JSONPlaceholderAsyncService.class);
                bind(JSONPlaceholderRxService.class).to(JSONPlaceholderRxService.class);
                bind(JSONPlaceholderObsService.class).to(JSONPlaceholderObsService.class);
                
                bind(GitHubSyncService.class).to(GitHubSyncService.class);
                bind(GitHubAsyncService.class).to(GitHubAsyncService.class);
                bind(GitHubRxService.class).to(GitHubRxService.class);
                bind(GitHubObsService.class).to(GitHubObsService.class);
            }
        });
        return resourceConfig;
    }
}
