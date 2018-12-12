package com.matao.bus.scheduler;

import com.matao.bus.Bus;

public class SenderScheduler implements Scheduler{

    private Bus bus;

    public SenderScheduler(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void post(Runnable runnable) {
        runnable.run();
    }
}
