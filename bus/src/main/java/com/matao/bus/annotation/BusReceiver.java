package com.matao.bus.annotation;

import com.matao.bus.EventMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used for annotating a public non-static method with one parameter.
 * This annotated method will run in the thread specified by mode
 * and run in UI thread default.
 *
 * <pre>
 * @BusReceiver(mode = EventMode.main)
 * public void onEvent(FooEvent event) {
 *     // do something...
 * }
 * </pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BusReceiver {
    EventMode mode() default EventMode.Main;
}
