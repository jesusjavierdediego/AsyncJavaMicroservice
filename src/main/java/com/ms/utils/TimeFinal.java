package com.ms.utils;

import java.util.concurrent.Callable;


public class TimeFinal implements Callable<Long> {

    private final long time;
    
    public TimeFinal(long time) {
            this.time = time;
    }

    @Override
    public Long call() throws Exception {

        long timeFinal  = getTimeFinal(time);
        
        return  getTimeFinal(time);
    }
    
    private Long getTimeFinal(long time) throws InterruptedException {
           
            return (System.nanoTime() - time) / 1000000;
        }

}
