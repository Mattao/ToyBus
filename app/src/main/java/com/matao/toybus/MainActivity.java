package com.matao.toybus;

import android.os.Bundle;
import android.util.Log;

import com.matao.bus.annotation.BusReceiver;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.launch_next_bt).setOnClickListener(v -> EventPostActivity.launch(this));
    }

    @BusReceiver
    public void onStringEvent(String event) {
        // 不会执行，因为event是StringBuilder，event instanceof String == false
        Log.d(TAG, "onStringEvent() event=" + event);
    }

    @BusReceiver
    public void onExceptionEvent(Exception event) {
        // 不会执行，因为event是StringBuilder，event instanceof Exception == false
        Log.d(TAG, "onExceptionEvent() event=" + event);
    }

    @BusReceiver
    public void onCharSequenceEvent(CharSequence event) {
        // 会执行，因为event是StringBuilder，event instanceof CharSequence == true
        Log.d(TAG, "onCharSequenceEvent() event=" + event);
    }

    @BusReceiver
    public void onObjectEvent(Object event) {
        // 会执行，因为event是StringBuilder，event instanceof Object == true
        Log.d(TAG, "onObjectEvent() event=" + event);
    }
}
