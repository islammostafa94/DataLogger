package com.islamabdelaziz.dl;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
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
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    static MqttAndroidClient client;
    static String temptopic = "Nourmoee/feeds/temp";
    static String volttopic = "islammostafa/feeds/voltage";
    static String currenttopic = "islammostafa/feeds/current";

    static Button tempBtn;
    static Button currentBtn;
    static Button voltBtn;
    private AlphaAnimation buttonClick = new AlphaAnimation(0.8F, 0.6F);

    //  static TextView connView ;
    MqttConnectOptions options = new MqttConnectOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String clientId = MqttClient.generateClientId();

        //connView = (TextView) findViewById(R.id.textView) ;
        client =
                new MqttAndroidClient(this.getApplicationContext(), "tcp://io.adafruit.com:1883",
                        clientId);

        pahoConnect();
        // setSub();
        tempBtn = (Button) findViewById(R.id.tempBtn);
        tempBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempActive();
                tempBtn.startAnimation(buttonClick);


            }
        });

        voltBtn = (Button) findViewById(R.id.voltBtn);
        voltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltActive();
            }
        });

        currentBtn = (Button) findViewById(R.id.currentBtn);
        currentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentActive();
            }
        });
        //setSub();

    }



    public void pahoConnect() {

        try {
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
            options.setUserName("Nourmoee");
            options.setPassword("0e0b6a416d5148d9aaf194585cb31989".toCharArray());
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    //connView.setText("Connected");
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                    setSub();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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
                if(topic.contains("volt"))
                {
                    voltActivity.volttopic.setText(new String(message.getPayload()));
                }

                if(topic.contains("current"))
                {
                    currentActivity.currentTopic.setText(new String(message.getPayload()));
                }

                if(topic.contains("temp"))
                {
                    temperatureActivity.temptopic.setText(new String(message.getPayload()));
                }

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
            client.subscribe(volttopic,0);
            client.subscribe(currenttopic,0);

        }
        catch (MqttException e){
            e.printStackTrace();
        }
    }

    public void tempActive()
    {
        Intent intent = new Intent(this,temperatureActivity.class);
        startActivity(intent);
    }

    public void voltActive()
    {
        Intent intent = new Intent(this,voltActivity.class);
        startActivity(intent);
    }

    public void currentActive()
    {
        Intent intent = new Intent(this,currentActivity.class);
        startActivity(intent);
    }


}
