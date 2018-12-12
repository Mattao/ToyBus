package com.matao.bus;

/**
 * Method annotated by @BusSubscriber will be invoked in the following 3 type threads.
 * <p>
 * Sender - run in the thread where post happens.
 * Main - run in Android UI thread.
 * Thread - run in a new thread scheduled by thread pool.
 */
public enum EventMode {
    Sender, Main, Thread
}
