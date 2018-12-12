package com.matao.bus.scheduler;

import android.os.Handler;
import android.os.Looper;

public class Schedulers {

    public static Scheduler sender() {
        return new SenderScheduler();
    }

    public static Scheduler main() {
        return new HandlerScheduler(new Handler(Looper.getMainLooper()));
    }

    public static Scheduler thread() {
        return new ExecutorScheduler();
    }
}
