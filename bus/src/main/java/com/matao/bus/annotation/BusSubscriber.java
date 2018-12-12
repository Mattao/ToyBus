package com.matao.bus.annotation;

import com.matao.bus.ThreadMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used for annotating a public non-static method with one parameter.
 * This annotated method will run in the thread specified by threadMode
 * and run in UI thread default.
 *
 * <pre>
 * @BusReceiver(mode = ThreadMode.main)
 * public void onEvent(FooEvent event) {
 *     // do something...
 * }
 * </pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BusSubscriber {
    ThreadMode threadMode() default ThreadMode.Main;
}
