package com.matao.bus.scheduler;

import com.matao.bus.Bus;

public class Schedulers {

    public static Scheduler sender(Bus bus) {
        return new SenderScheduler(bus);
    }

    public static Scheduler getMainThreadScheduler(Bus bus) {
        return HandlerScheduler.getMainThreadScheduler(bus);
    }

    public static Scheduler thread(Bus bus) {
        return new ExecutorScheduler(bus);
    }
}
