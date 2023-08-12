package com.newworld.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choice_Activity extends AppCompatActivity {
    private Button RideBtn;
    private Button PercelBtn;
    private Button MechanicBtn;
    private Button AccessoriesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        RideBtn=(Button) findViewById(R.id.ride_btn);
        RideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rideWelcome=new Intent(Choice_Activity.this, Ride_Welcome_Activity.class);
                startActivity(rideWelcome);
            }
        });
    }
}