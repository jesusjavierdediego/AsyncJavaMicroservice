package com.ms.utils;

import java.util.concurrent.Callable;


public class TimeFinal implements Callable<Long> {

    @Override
    public Long call() throws Exception {

        long timeInitial = 0;
        
        return  (System.nanoTime() - timeInitial) / 1000000;
    }

}
