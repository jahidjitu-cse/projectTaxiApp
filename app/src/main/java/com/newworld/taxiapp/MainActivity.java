package com.newworld.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Thread thread= new Thread(){
            @Override
            public void run() {
                try {
                    sleep(6000);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    Intent wel_intent=new Intent (MainActivity.this, Choice_Activity.class);
                    startActivity(wel_intent);
                }
            }
        };
        thread.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}