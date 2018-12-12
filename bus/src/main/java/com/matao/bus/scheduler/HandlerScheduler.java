package com.matao.bus.scheduler;

import android.os.Handler;
import android.os.Looper;

import com.matao.bus.Bus;

public class HandlerScheduler implements Scheduler {

    private Bus bus;
    private Handler handler;

    public HandlerScheduler(Bus bus, Handler handler) {
        this.bus = bus;
        this.handler = handler;
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }

    static Scheduler getMainThreadScheduler(Bus bus) {
        return new HandlerScheduler(bus, new Handler(Looper.getMainLooper()));
    }
}
