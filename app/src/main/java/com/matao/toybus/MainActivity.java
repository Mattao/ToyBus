package com.matao.toybus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.matao.bus.Bus;
import com.matao.bus.annotation.BusReceiver;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.launch_next_bt).setOnClickListener(v -> EventPostActivity.launch(this));

        Bus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bus.getDefault().unregister(this);
    }

    @BusReceiver
    public void onEvent(Event event) {
        Toast.makeText(this, "event received", Toast.LENGTH_SHORT).show();
    }
}
