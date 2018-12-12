package com.matao.toybus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.matao.bus.Bus;

public class EventPostActivity extends AppCompatActivity {

    private Button postEventBt;

    public static void launch(Context context) {
        Intent intent = new Intent(context, EventPostActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_post);

        postEventBt = findViewById(R.id.post_event_bt);
        postEventBt.setOnClickListener(v -> Bus.getDefault().post(new Event()));
    }
}
