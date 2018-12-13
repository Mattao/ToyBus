package com.matao.bus;

import com.matao.bus.model.MethodInfo;
import com.matao.bus.scheduler.Scheduler;
import com.matao.bus.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Bus {

    private static Bus bus;
    private Scheduler threadScheduler;
    private Scheduler mainScheduler;
    private Scheduler senderScheduler;
    private Map<Object, Set<Subscriber>> subscriberMap = new HashMap<>();

    private Bus() {
        threadScheduler = Schedulers.thread();
        mainScheduler = Schedulers.main();
        senderScheduler = Schedulers.sender();
    }

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
        Set<Subscriber> subscriberSet = Utils.findSubscribersByAnnotation(target);

        if (subscriberSet == null || subscriberSet.isEmpty()) return;
        subscriberMap.put(target, subscriberSet);
    }

    public void unregister(Object target) {
        subscriberMap.remove(target);
    }

    public void post(final Object event) {
        Class<?> eventType = event.getClass();
        for (Set<Subscriber> subscriberSet : subscriberMap.values()) {
            for (final Subscriber subscriber : subscriberSet) {
                final MethodInfo methodInfo = subscriber.methodInfo;
                if (methodInfo.eventType.isAssignableFrom(eventType)) {
                    sendEvent(new EventEmitter(subscriber, event));
                }
            }
        }
    }

    private void sendEvent(EventEmitter emitter) {
        switch (emitter.threadMode) {
            case Main:
                mainScheduler.post(emitter);
                break;
            case Thread:
                threadScheduler.post(emitter);
                break;
            case Sender:
                senderScheduler.post(emitter);
                break;
            default:
        }
    }
}
