package com.manacher.myrtc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.manacher.rtc.services.ManacherService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ManacherService service = ManacherService.initialized(this);
    }
}