package com.matao.bus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Method> findAnnotatedMethods(Class<?> type,
                                                    Class<? extends Annotation> annotation) {
        List<Method> methods = new ArrayList<>();
        // for now ignore super class, handle current class only
        for (Method method : type.getDeclaredMethods()) {

            // must be public
            if (!Modifier.isPublic(method.getModifiers())) {
                continue;
            }
            // must not be static
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            // must has only one parameter
            if (method.getParameterTypes().length != 1) {
                continue;
            }
            // must has specified annotation
            if (!method.isAnnotationPresent(annotation)) {
                continue;
            }

            methods.add(method);
        }
        return methods;
    }
}
