package com.manacher.myrtc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.manacher.rtc.services.AndroidRTC;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AndroidRTC service = AndroidRTC.initialized(this);
    }
}