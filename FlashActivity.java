package com.example.advncd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class FlashActivity extends AppCompatActivity {
 @Override
protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
             WindowManager.LayoutParams.FLAG_FULLSCREEN);

     setContentView(R.layout.activity_flash);

     new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
             Intent i = new Intent(FlashActivity.this, login.class);

             // on below line we are
             // starting a new activity.
             startActivity(i);

             // on the below line we are finishing
             // our current activity.
             finish();
         }
     }, 500);

 }}