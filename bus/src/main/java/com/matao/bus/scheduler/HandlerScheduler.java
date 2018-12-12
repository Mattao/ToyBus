package com.matao.bus.scheduler;

import android.os.Handler;

public class HandlerScheduler implements Scheduler {

    private Handler handler;

    public HandlerScheduler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }
}
