package com.matao.bus;

import android.util.Log;

public class Logger {
    private static final String TAG = "ToyBus";

    public static void v(String message) {
        Log.v(TAG, message);
    }

    public static void d(String message) {
        Log.d(TAG, message);
    }
}
