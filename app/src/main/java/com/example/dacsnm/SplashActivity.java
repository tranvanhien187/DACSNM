package com.example.dacsnm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        findViewById(R.id.btn_pvp).setOnClickListener(view->{
            startActivity(new Intent(this,MainActivity.class));
        });

        findViewById(R.id.btn_pvf).setOnClickListener(view->{
            startActivity(new Intent(this,TwoPlayersActivity.class));
        });
    }
}