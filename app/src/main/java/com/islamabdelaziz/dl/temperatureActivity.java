package com.islamabdelaziz.dl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class temperatureActivity extends AppCompatActivity {
    static TextView temptopic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        temptopic = (TextView) findViewById(R.id.tempTopic);

    }
}