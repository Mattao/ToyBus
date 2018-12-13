package com.matao.bus.model;

import com.matao.bus.ThreadMode;

import java.lang.reflect.Method;

/**
 * extract bus subscriber method info
 * <pre>
 * @BusSubscriber(threadMode = ThreadMode.main)
 * public void onEvent(FooEvent event) {
 *     // do something...
 * }
 * </pre>
 */
public class SubscriberMethod {
    public final Method method;
    public final Class<?> eventType;
    public final ThreadMode threadMode;

    public SubscriberMethod(Method method, ThreadMode threadMode) {
        this.method = method;
        this.eventType = method.getParameterTypes()[0];
        this.threadMode = threadMode;
    }
}
