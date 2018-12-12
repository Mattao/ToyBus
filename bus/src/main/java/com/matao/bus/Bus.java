package com.matao.bus;

import com.matao.bus.annotation.BusSubscriber;
import com.matao.bus.scheduler.Schedulers;

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
        Set<Method> methods = Utils.findAnnotatedMethods(target.getClass(), BusSubscriber.class);

        if (methods == null || methods.isEmpty()) return;
        methodMap.put(target, methods);
    }

    public void unregister(Object target) {
        methodMap.remove(target);
    }

    public void post(final Object event) {
        Class<?> eventClazz = event.getClass();
        for (Map.Entry<Object, Set<Method>> entry : methodMap.entrySet()) {
            final Object target = entry.getKey();
            Set<Method> methods = entry.getValue();

            if (methods != null) {
                for (final Method method : methods) {
                    // eventClazz is the same as or the subclass of current method parameter type
                    if (method.getParameterTypes()[0].isAssignableFrom(eventClazz)) {
                        BusSubscriber annotation = method.getAnnotation(BusSubscriber.class);
                        EventMode eventMode = annotation.threadMode();
                        switch (eventMode) {
                            case Sender:
                                Schedulers.sender().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            method.invoke(target, event);
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        } catch (InvocationTargetException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                break;
                            case Thread:
                                Schedulers.thread().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            method.invoke(target, event);
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        } catch (InvocationTargetException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                break;
                            case Main:
                                Schedulers.main().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            method.invoke(target, event);
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        } catch (InvocationTargetException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                break;
                            default:
                        }
                    }
                }
            }
        }
    }
}
