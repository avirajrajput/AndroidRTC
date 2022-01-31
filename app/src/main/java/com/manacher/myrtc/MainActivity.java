package com.manacher.myrtc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.manacher.rtc.wrbrtc.interfaces.RTCObserver;
import com.manacher.rtc.wrbrtc.services.AndroidRTC;
import com.manacher.rtc.wrbrtc.utils.IceCandidateServer;
import com.manacher.rtc.wrbrtc.utils.Offer;

import org.webrtc.DataChannel;

public class MainActivity extends AppCompatActivity implements RTCObserver, DataChannel.Observer {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AndroidRTC rtc = new AndroidRTC(this);

        SenderService senderService = new SenderService(this, "1234");

        findViewById(R.id.testButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                rtc.createCandidates();
//                findViewById(R.id.testButton).setEnabled(false);

                senderService.createOffer();
            }
        });

        findViewById(R.id.testButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        rtc.createOffer();

    }

    @Override
    public void onIceCandidate(IceCandidateServer iceCandidateServer) {
        Log.d("HHS98", "onIceCandidate: ");
    }

    @Override
    public void onInvitation(Offer offer, int type) {

    }

    @Override
    public void onBufferedAmountChange(long l) {

    }

    @Override
    public void onStateChange() {

    }

    @Override
    public void onMessage(DataChannel.Buffer buffer) {

    }
}