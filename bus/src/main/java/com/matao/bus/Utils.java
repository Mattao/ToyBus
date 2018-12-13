package com.matao.bus;

import android.support.annotation.Nullable;

import com.matao.bus.annotation.BusSubscriber;
import com.matao.bus.exception.BusException;
import com.matao.bus.model.SubscriberMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static Set<SubscriberMethod> findSubscriberMethods(final Object subscriber) {
        Set<SubscriberMethod> subscriberMethods = new HashSet<>();
        Class<?> clazz = subscriber.getClass();
        while (!shouldSkipClass(clazz)) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(BusSubscriber.class) && isValidMethod(method)) {
                    BusSubscriber busSubscriber = method.getAnnotation(BusSubscriber.class);
                    SubscriberMethod subscriberMethod = new SubscriberMethod(method, busSubscriber.threadMode());
                    subscriberMethods.add(subscriberMethod);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return subscriberMethods;
    }

    // skip Object class, JDK class and Android class
    private static boolean shouldSkipClass(@Nullable final Class<?> clazz) {
        if (clazz == null) return true;

        String clazzName = clazz.getName();
        return Object.class.equals(clazz)
            || clazzName.startsWith("java.")
            || clazzName.startsWith("javax.")
            || clazzName.startsWith("android.")
            || clazzName.startsWith("com.android.");
    }

    private static boolean isValidMethod(final Method method) {
        // must be public
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new BusException("event method: " + getMethodSignature(method)
                + " must be public!");
        }
        // must not be static
        if (Modifier.isStatic(method.getModifiers())) {
            throw new BusException("event method: " + getMethodSignature(method) +
                " must not be static!");
        }
        // must have only one parameter
        if (method.getParameterTypes().length != 1) {
            throw new BusException("event method: " + getMethodSignature(method)
                + " must have exact one parameter!");
        }

        // filter bridge method
        return !method.isBridge();
    }

    private static String getMethodSignature(final Method method) {
        return String.format("%s.%s()", method.getDeclaringClass().getSimpleName(), method.getName());
    }
}
