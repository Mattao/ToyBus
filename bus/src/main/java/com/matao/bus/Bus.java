package com.matao.bus;

import com.matao.bus.annotation.BusReceiver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by matao on 2018/12/12
 */
public class Bus {

    private static Bus bus;
    private Map<Object, Set<Method>> methodMap = new HashMap<>();

    public static Bus getDefault() {
        if (bus == null) {
            synchronized (Bus.class) {
                if (bus == null) {
                    bus = new Bus();
                }
            }
        }
        return bus;
    }

    public void register(Object target) {
        Set<Method> methods = Utils.findAnnotatedMethods(target.getClass(), BusReceiver.class);

        if (methods == null || methods.isEmpty()) return;
        methodMap.put(target, methods);
    }

    public void unregister(Object target) {
        methodMap.remove(target);
    }

    public void post(Object event) {
        Class<?> eventClazz = event.getClass();
        for (Map.Entry<Object, Set<Method>> entry : methodMap.entrySet()) {
            Object target = entry.getKey();
            Set<Method> methods = entry.getValue();

            if (methods != null) {
                for (Method method : methods) {
                    // eventClazz is the same as or the subclass of current method parameter type
                    if (method.getParameterTypes()[0].isAssignableFrom(eventClazz)) {
                        try {
                            method.invoke(target, event);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
