package com.matao.bus.scheduler;

import com.matao.bus.Bus;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorScheduler implements Scheduler {

    private Bus bus;
    private Executor executor;

    public ExecutorScheduler(Bus bus) {
        this.bus = bus;
        this.executor = Executors.newCachedThreadPool();
    }

    @Override
    public void post(Runnable runnable) {
        executor.execute(runnable);
    }
}
