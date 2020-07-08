package com.vnak.vdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    SeekBar speek,huong;
    TextView txtND,txtDA;
    int maxSpeek = 255;
    int minSpeek = (-1)*255;
    JSONObject data;
    private Socket socket;
    {
        try {
            socket = IO.socket("http://192.168.1.19:3000/");
        }catch (URISyntaxException ex){
            Log.e("LOI_Connect",ex.toString());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDA = findViewById(R.id.txtDA);
        txtND = findViewById(R.id.txtND);
        speek = findViewById(R.id.run);
        speek.setMax(maxSpeek-minSpeek);
        speek.setProgress(0-minSpeek);
        huong = findViewById(R.id.quay);
        huong.setMax(150-30);
        huong.setProgress(90-30);
        data = new JSONObject();
        addEvents();
        socket.on("dht", dht);
        socket.connect();
    }

    public Emitter.Listener dht = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject doam = (JSONObject) args[0];
                    try {
                        if (doam.has("temperature")) {
                            txtDA.setText(doam.getString("temperature"));
                        }
                        if (doam.has("humidity")) {
                            txtND.setText(doam.getString("humidity"));
                        }
                    } catch (Exception ex) {

                    }
                }
            });
        }
    };

    private void addEvents() {
        speek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = speek.getProgress()-255;
                try {
                    data.put("type", "car");
                    data.put("value", value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("control", data);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                speek.setProgress(0-minSpeek);;
                try {
                    data.put("type", "car");
                    data.put("value", speek.getProgress()-255);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("control", data);
            }
        });

        huong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = huong.getProgress()+30;
                try {
                    data.put("type", "servo");
                    data.put("value", value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("control", data);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                huong.setProgress(60);
                try {
                    data.put("type", "servo");
                    data.put("value", huong.getProgress()+30);
                    JSONObject dht = new JSONObject();
                    data.put("type", "dht");
                    data.put("value", "");
                    socket.emit("control", data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("control", data);
            }
        });
    }


}