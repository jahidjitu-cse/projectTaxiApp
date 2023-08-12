package com.newworld.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ride_welcome_act extends AppCompatActivity {
    private Button welDriverbtn;
    private  Button welCustbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welDriverbtn=(Button) findViewById(R.id.wel_drive_btn);
        welCustbtn=(Button) findViewById(R.id.wel_cust_btn);

        welDriverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegistrerDri=new Intent(ride_welcome_act.this, DriverLoginRegster_act.class);
                startActivity(LoginRegistrerDri);
            }
        });

        welCustbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterCus=new Intent(ride_welcome_act.this, CustomerLoginRegister_act.class);
                startActivity(LoginRegisterCus);
            }
        });
    }
}