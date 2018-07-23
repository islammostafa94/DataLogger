package com.islamabdelaziz.dl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class temSensor extends AppCompatActivity {
    TextView temptext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tem_sensor);

           // Intent intent=getIntent();
           // String temp = intent.getStringExtra(home.EXTRA_TEMP);
            temptext = (TextView) findViewById(R.id.textView2);
           // temptext.setText("Holla" + home.btn.getText().toString());
            home.client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    if (topic.contains("test11") ) {
                        temptext.setText(new String(message.getPayload()));
                       // temp = new String(message.getPayload());
                       // btn.setText(new String(message.getPayload()));

                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });


    }

}
