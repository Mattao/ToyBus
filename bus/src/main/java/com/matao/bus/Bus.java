package com.matao.bus;

import com.matao.bus.method.MethodInfo;
import com.matao.bus.method.MethodUtils;
import com.matao.bus.scheduler.Schedulers;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Bus {

    private static Bus bus;
    private Map<Object, Set<MethodInfo>> methodInfoMap = new HashMap<>();

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
        Set<MethodInfo> methods = MethodUtils.findSubscriberMethodsByAnnotation(target.getClass());

        if (methods == null || methods.isEmpty()) return;
        methodInfoMap.put(target, methods);
    }

    public void unregister(Object target) {
        methodInfoMap.remove(target);
    }

    public void post(final Object event) {
        Class<?> eventType = event.getClass();
        for (Map.Entry<Object, Set<MethodInfo>> entry : methodInfoMap.entrySet()) {
            final Object target = entry.getKey();
            Set<MethodInfo> methodInfoSet = entry.getValue();

            if (methodInfoSet == null) return;

            for (final MethodInfo methodInfo : methodInfoSet) {
                if (methodInfo.eventType.isAssignableFrom(eventType)) {
                    switch (methodInfo.threadMode) {
                        case Sender:
                            Schedulers.sender().post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        methodInfo.method.invoke(target, event);
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
                                        methodInfo.method.invoke(target, event);
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
                                        methodInfo.method.invoke(target, event);
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
