package com.matao.toybus;

import android.os.Bundle;
import android.util.Log;

import com.matao.bus.EventMode;
import com.matao.bus.annotation.BusReceiver;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.launch_next_bt).setOnClickListener(v -> EventPostActivity.launch(this));
    }

    /**
     * This method will not be invoked.
     * <p>
     * Since event is an instance of StringBuilder,
     * and
     * <pre>
     *     event instanceof String == true
     * </pre>
     */
    @BusReceiver
    public void onStringEvent(String event) {
        Log.d(TAG, "onStringEvent() event = " + event);
    }

    /**
     * This method will not be invoked.
     * <p>
     * Since event is an instance of StringBuilder,
     * and
     * <pre>
     *     event instanceof Exception == false
     * </pre>
     */
    @BusReceiver
    public void onExceptionEvent(Exception event) {
        Log.d(TAG, "onExceptionEvent() event = " + event);
    }

    /**
     * This method will be invoked in the default main thread.
     * <p>
     * Since event is an instance of StringBuilder,
     * and
     * <pre>
     *     event instanceof CharSequence == true
     * </pre>
     */
    @BusReceiver
    public void onCharSequenceEvent(CharSequence event) {
        Log.d(TAG, "onCharSequenceEvent() event = " + event + ", thread: " + Thread.currentThread().getName());
    }

    /**
     * This method will be invoked in a new thread.
     * <p>
     * Since event is an instance of StringBuilder,
     * and
     * <pre>
     *     event instanceof Object == true
     * </pre>
     */
    @BusReceiver(mode = EventMode.Thread)
    public void onObjectEvent(Object event) {
        Log.d(TAG, "onObjectEvent() event = " + event + ", thread: " + Thread.currentThread().getName());
    }

    /**
     * This method will be invoked in the sender thread.
     * <p>
     * Since event is an instance of StringBuilder,
     * and
     * <pre>
     *     event instanceof StringBuilder == true
     * </pre>
     */
    @BusReceiver(mode = EventMode.Sender)
    public void onStringBuilderEvent(StringBuilder event) {
        Log.d(TAG, "onStringBuilderEvent() event = " + event + ", thread: " + Thread.currentThread().getName());
    }
}
