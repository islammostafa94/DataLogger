package com.islamabdelaziz.dl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class currentActivity extends AppCompatActivity {
    static TextView currentTopic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current);
        currentTopic = (TextView) findViewById(R.id.currentTopic);

    }
}