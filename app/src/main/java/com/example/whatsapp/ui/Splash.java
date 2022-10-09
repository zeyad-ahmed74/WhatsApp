package com.example.whatsapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.whatsapp.R;
import com.example.whatsapp.SignUp;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              Intent in = new Intent(Splash.this,SignUp.class);
              startActivity(in);
              finish();
          }
      },3000);


        getSupportActionBar().hide();

    }
}