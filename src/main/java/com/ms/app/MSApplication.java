package com.ms.app;

import com.ms.api.AsyncResource;
import com.ms.api.ObsResource;
import com.ms.api.RxResource;
import com.ms.api.SyncResource;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationPath("/")
public class MSApplication extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(MSApplication.class);
    public static final String PROPERTIES_FILE = "configuration.properties";
    public static Properties properties = new Properties();
    
    private Properties readProperties() {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
    if (inputStream != null) {
        try {
            properties.load(inputStream);
            LOGGER.debug("Successful loading of  properties configuration");
        } catch (IOException e) {
            LOGGER.error("Failure when trying load properties configuration: ", e.getMessage());
        }
    }
    return properties;
}

    @Override
    public Set<Class<?>> getClasses() {
        
        readProperties();
        
        Set<Class<?>> rootResources = new HashSet<>();
        
        rootResources.add(AsyncResource.class);
        rootResources.add(RxResource.class);
        rootResources.add(ObsResource.class);
        rootResources.add(SyncResource.class);
        
        return rootResources;
    }
}


