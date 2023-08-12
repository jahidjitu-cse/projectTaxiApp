package com.newworld.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverLoginRegister_act extends AppCompatActivity {
    private  Button phnlog,emaillog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_register);
        phnlog=(Button) findViewById(R.id.phonelogin);
        emaillog =(Button) findViewById(R.id.emaillogin);
        phnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DriverLoginRegister_act.this, Driver_phone_login_activity.class);
                startActivity(intent);
            }
        });
        emaillog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DriverLoginRegister_act.this, Driver_Email_login_Activity.class);
                startActivity(intent);
            }
        });
    }
}