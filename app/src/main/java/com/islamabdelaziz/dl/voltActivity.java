package com.islamabdelaziz.dl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class voltActivity extends AppCompatActivity {
    static TextView volttopic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volt);
        volttopic = (TextView) findViewById(R.id.voltTopic);

    }
}

