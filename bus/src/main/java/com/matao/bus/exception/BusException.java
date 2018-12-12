package com.matao.bus.exception;

/**
 * Created by matao on 2018/12/12
 */
public class BusException extends RuntimeException {

    public BusException() {
    }

    public BusException(String message) {
        super(message);
    }

    public BusException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusException(Throwable cause) {
        super(cause);
    }
}
