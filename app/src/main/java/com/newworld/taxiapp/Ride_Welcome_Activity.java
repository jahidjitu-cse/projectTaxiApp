package com.newworld.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ride_Welcome_Activity extends AppCompatActivity {

    private Button welDriverbtn;
    private  Button welCustbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_welcome);
        welDriverbtn=(Button) findViewById(R.id.wel_drive_btn);
        welCustbtn=(Button) findViewById(R.id.wel_cust_btn);

        welDriverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterDri=new Intent(Ride_Welcome_Activity.this, DriverLoginRegister_act.class);
                startActivity(LoginRegisterDri);
            }
        });

        welCustbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterCus=new Intent(Ride_Welcome_Activity.this, CustomerLoginResister_act.class);
                startActivity(LoginRegisterCus);
            }
        });
    }
}