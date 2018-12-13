package com.matao.bus;

import android.support.annotation.NonNull;

import com.matao.bus.model.SubscriberMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Subscription {
    public final Object subscriber;
    public final SubscriberMethod subscriberMethod;

    public Subscription(Object subscriber, SubscriberMethod subscriberMethod) {
        this.subscriber = subscriber;
        this.subscriberMethod = subscriberMethod;
    }

    public Object invoke(Object event) throws InvocationTargetException, IllegalAccessException {
        return subscriberMethod.method.invoke(subscriber, event);
    }

    @NonNull
    @Override
    public String toString() {
        Method method = subscriberMethod.method;
        Class<?> eventType = subscriberMethod.eventType;
        return String.format("%s.%s(%s)", subscriber.getClass().getName(), method.getName(), eventType.getName());
    }
}
