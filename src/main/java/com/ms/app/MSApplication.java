package com.ms.app;

import com.ms.api.AsyncResource;
import com.ms.api.ObsResource;
import com.ms.api.RxResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class MSApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        s.add(AsyncResource.class);
        s.add(RxResource.class);
        s.add(ObsResource.class);
        return s;
    }
 
}


