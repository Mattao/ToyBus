package com.matao.bus;

import java.lang.reflect.InvocationTargetException;

public class EventEmitter implements Runnable{

    public final Subscriber subscriber;
    public final Object event;
    public final ThreadMode threadMode;

    public EventEmitter(Subscriber subscriber, Object event) {
        this.subscriber = subscriber;
        this.event = event;
        this.threadMode = subscriber.methodInfo.threadMode;
    }

    @Override
    public void run() {
        try {
            subscriber.invoke(event);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
