package com.matao.bus;

import android.support.annotation.NonNull;

import com.matao.bus.model.MethodInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Subscriber {
    public final Object target;
    public final MethodInfo methodInfo;

    public Subscriber(Object target, MethodInfo methodInfo) {
        this.target = target;
        this.methodInfo = methodInfo;
    }

    public Object invoke(Object event) throws InvocationTargetException, IllegalAccessException {
        return methodInfo.method.invoke(target, event);
    }

    @NonNull
    @Override
    public String toString() {
        Method method = methodInfo.method;
        Class<?> eventType = methodInfo.eventType;
        return String.format("%s.%s(%s)", target.getClass().getName(), method.getName(), eventType.getName());
    }
}
