package com.matao.bus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static Set<Method> findAnnotatedMethods(final Class<?> type,
                                                   final Class<? extends Annotation> annotation) {
        Set<Method> methods = new HashSet<>();
        Class<?> clazz = type;
        while (clazz != null && !shouldSkipClass(clazz)) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (isAnnotatedMethod(method, annotation)) {
                    methods.add(method);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return methods;
    }

    // skip Object class, JDK class and Android class
    private static boolean shouldSkipClass(final Class<?> clazz) {
        String clazzName = clazz.getName();
        return Object.class.equals(clazz)
            || clazzName.startsWith("java.")
            || clazzName.startsWith("javax.")
            || clazzName.startsWith("android.")
            || clazzName.startsWith("com.android.");
    }

    private static boolean isAnnotatedMethod(final Method method,
                                             final Class<? extends Annotation> annotation) {
        // must be annotated by annotation
        if (!method.isAnnotationPresent(annotation)) {
            return false;
        }
        // must be public
        if (!Modifier.isPublic(method.getModifiers())) {
            return false;
        }
        // must not be static
        if (Modifier.isStatic(method.getModifiers())) {
            return false;
        }
        // filter bridge method
        if (method.isBridge()) {
            return false;
        }
        // must has only one parameter
        if (method.getParameterTypes().length != 1) {
            return false;
        }

        return true;
    }
}
