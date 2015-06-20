package com.ms.utils;

import com.ms.app.MSApplication;
import org.jvnet.hk2.annotations.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class TaskExecutor implements Executor {

    private final Executor delegate = Executors.newFixedThreadPool(Integer.parseInt(MSApplication.properties.getProperty("application.threads.pool")));

    @Override
    public void execute(Runnable command) {
        delegate.execute(command);
    }
}
