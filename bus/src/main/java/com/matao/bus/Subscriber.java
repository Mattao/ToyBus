package com.matao.bus;

import com.matao.bus.method.MethodInfo;

import java.lang.reflect.InvocationTargetException;

public class Subscriber {
    private final MethodInfo methodInfo;
    private final Object target;

    public Subscriber(MethodInfo methodInfo, Object target) {
        this.methodInfo = methodInfo;
        this.target = target;
    }

    public MethodInfo getMethodInfo() {
        return methodInfo;
    }

    public Object invoke(Object event) throws InvocationTargetException, IllegalAccessException {
        return methodInfo.method.invoke(target, event);
    }
}
