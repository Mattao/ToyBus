package com.matao.bus;

import com.matao.bus.annotation.BusReceiver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by matao on 2018/12/12
 */
public class Bus {

    private static Bus bus;
    private Map<Object, List<Method>> methodMap = new HashMap<>();

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
        List<Method> methods = Utils.findAnnotatedMethods(target.getClass(), BusReceiver.class);

        if (methods == null || methods.isEmpty()) return;
        methodMap.put(target, methods);
    }

    public void unregister(Object target) {
        methodMap.remove(target);
    }

    public void post(Object event) {
        Class<?> eventClazz = event.getClass();
        for (Map.Entry<Object, List<Method>> entry : methodMap.entrySet()) {
            Object target = entry.getKey();
            List<Method> methods = entry.getValue();

            if (methods != null) {
                for (Method method : methods) {
                    // if parameter type of BusReceiver's method is the same as the posted event type
                    // for now ignore inherited class type
                    if (eventClazz.equals(method.getParameterTypes()[0])) {
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
