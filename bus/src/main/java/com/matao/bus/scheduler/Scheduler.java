package com.matao.bus.scheduler;

public interface Scheduler {
    void post(Runnable runnable);
}
