package com.matao.bus;

import java.lang.reflect.InvocationTargetException;

public class EventEmitter implements Runnable{

    public final Subscription subscription;
    public final Object event;
    public final ThreadMode threadMode;

    public EventEmitter(Subscription subscription, Object event) {
        this.subscription = subscription;
        this.event = event;
        this.threadMode = subscription.subscriberMethod.threadMode;
    }

    @Override
    public void run() {
        try {
            subscription.invoke(event);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
