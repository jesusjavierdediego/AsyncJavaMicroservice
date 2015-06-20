package com.ms.app;

import com.ms.api.AsyncResource;
import com.ms.api.ObsResource;
import com.ms.api.RxResource;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

@ApplicationPath("/")
public class MSApplication extends Application {
    
    public static final String PROPERTIES_FILE = "configuration.properties";
    public static Properties properties = new Properties();
    
    private Properties readProperties() {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
    if (inputStream != null) {
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            // TODO Add your custom fail-over code here
            e.printStackTrace();
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
        return rootResources;
    }
 
}


