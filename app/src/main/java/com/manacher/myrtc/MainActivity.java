package com.manacher.myrtc;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.manacher.rtc.webrtc.interfaces.RTCObserver;
import com.manacher.rtc.webrtc.services.AndroidRTC;
import com.manacher.rtc.webrtc.utils.IceCandidateServer;
import com.manacher.rtc.webrtc.utils.Offer;
import org.webrtc.DataChannel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.testButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.testButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}