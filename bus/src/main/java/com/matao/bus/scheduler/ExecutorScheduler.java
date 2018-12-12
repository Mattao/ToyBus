package com.matao.bus.scheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorScheduler implements Scheduler {

    private Executor executor;

    public ExecutorScheduler() {
        this.executor = Executors.newCachedThreadPool();
    }

    @Override
    public void post(Runnable runnable) {
        executor.execute(runnable);
    }
}
