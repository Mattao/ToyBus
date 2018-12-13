package com.matao.bus.method;

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
public class MethodInfo {
    public final Method method;
    public final Class<?> targetType;
    public final Class<?> eventType;
    public final ThreadMode threadMode;
    public final String name;

    public MethodInfo(Method method, Class<?> targetType, ThreadMode threadMode) {
        this.method = method;
        this.targetType = targetType;
        this.eventType = method.getParameterTypes()[0];
        this.threadMode = threadMode;
        this.name = String.format("%s.%s(%s)", targetType.getName(), method.getName(), eventType.getName());
    }
}
