package com.matao.bus;

import com.matao.bus.method.MethodInfo;
import com.matao.bus.method.MethodUtils;
import com.matao.bus.scheduler.Schedulers;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Bus {

    private static Bus bus;
    private Map<Object, Set<Subscriber>> subscriberMap = new HashMap<>();

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
        Set<Subscriber> subscriberSet = new HashSet<>();
        for (MethodInfo methodInfo : MethodUtils.findSubscriberMethodsByAnnotation(target.getClass())) {
            Subscriber subscriber = new Subscriber(methodInfo, target);
            subscriberSet.add(subscriber);
        }

        if (subscriberMap == null || subscriberMap.isEmpty()) return;
        subscriberMap.put(target, subscriberSet);
    }

    public void unregister(Object target) {
        subscriberMap.remove(target);
    }

    public void post(final Object event) {
        Class<?> eventType = event.getClass();
        for (Set<Subscriber> subscriberSet : subscriberMap.values()) {
            for (final Subscriber subscriber : subscriberSet) {
                final MethodInfo methodInfo = subscriber.getMethodInfo();
                if (methodInfo.eventType.isAssignableFrom(eventType)) {
                    switch (methodInfo.threadMode) {
                        case Sender:
                            Schedulers.sender().post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        subscriber.invoke(event);
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
                                        subscriber.invoke(event);
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
                                        subscriber.invoke(event);
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
