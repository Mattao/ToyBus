package com.matao.bus;

import com.matao.bus.model.SubscriberMethod;
import com.matao.bus.scheduler.Scheduler;
import com.matao.bus.scheduler.Schedulers;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class Bus {

    private static Bus bus;
    private Scheduler threadScheduler;
    private Scheduler mainScheduler;
    private Scheduler senderScheduler;
    private List<Subscription> subscriptions = new CopyOnWriteArrayList<>();

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

    public void register(Object subscriber) {
        Set<SubscriberMethod> SubscriberMethods = Utils.findSubscriberMethods(subscriber);
        if (SubscriberMethods == null || SubscriberMethods.isEmpty()) return;

        for (SubscriberMethod subscriberMethod : SubscriberMethods) {
            subscribe(subscriber, subscriberMethod);
        }
    }

    private void subscribe(Object subscriber, SubscriberMethod subscriberMethod) {
        Subscription subscription = new Subscription(subscriber, subscriberMethod);
        subscriptions.add(subscription);
    }

    public void unregister(Object subscriber) {
        Iterator<Subscription> iterator = subscriptions.iterator();
        while (iterator.hasNext()) {
            Subscription subscription = iterator.next();
            if (subscription.subscriber == subscriber) {
                iterator.remove();
            }
        }
    }

    public void post(final Object event) {
        Class<?> eventType = event.getClass();
        for (Subscription subscription : subscriptions) {
            SubscriberMethod subscriberMethod = subscription.subscriberMethod;
            if (subscriberMethod.eventType.isAssignableFrom(eventType)) {
                sendEvent(new EventEmitter(subscription, event));
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
