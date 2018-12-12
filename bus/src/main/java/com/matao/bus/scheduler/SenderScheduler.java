package com.matao.bus.scheduler;

public class SenderScheduler implements Scheduler {

    @Override
    public void post(Runnable runnable) {
        runnable.run();
    }
}
