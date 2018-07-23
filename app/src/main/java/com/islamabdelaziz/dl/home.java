package com.islamabdelaziz.dl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class home extends AppCompatActivity {
    static MqttAndroidClient client;
    static String temptopic = "islammostafa/feeds/voltage";
    String volttopic = "Nourmoee/feeds/test";
    static String btntopic = "Nourmoee/feeds/button";
    String btntopic2 = "islammostafa/feeds/test2";
    String message = "0";
    static String temp ;
    String volt;
    static Button btn;
    public static final String EXTRA_TEMP = "com.islamabdelaziz.dl.EXTRA_TEMP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String clientId = MqttClient.generateClientId();
        btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempActive();
            }
        });
        client =
                new MqttAndroidClient(this.getApplicationContext(), "tcp://io.adafruit.com:1883",
                        clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName("islammostafa");
        options.setPassword("2c493ac005eb4abcb03e42431070390a".toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                   setSub();
                    Toast.makeText(home.this, "Success", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(home.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if (topic.contains("volt") ) {
                    temp = new String(message.getPayload());
                    btn.setText(new String(message.getPayload()));

                }
             /*   else if (topic.contains("test"))
                {
                    volt.setText(new String(message.getPayload()));
                }*/
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }
    public static void setSub()
    {
        try{
            client.subscribe(temptopic,0);
            client.subscribe(btntopic,0);

        }
        catch (MqttException e){
            e.printStackTrace();
        }
    }
    public void tempActive(){
        Intent intent = new Intent(this,temSensor.class);
        intent.putExtra(EXTRA_TEMP,temp);
        startActivity(intent);


    }


}
