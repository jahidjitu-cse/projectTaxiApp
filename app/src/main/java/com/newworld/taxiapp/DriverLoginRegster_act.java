package com.newworld.taxiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.Loader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DriverLoginRegster_act extends AppCompatActivity {
    private Button DrivLoginBtn;
    private  Button DrivRegBtn;
    private TextView DrivRegLink;
    private TextView DrivStatus;
    private EditText DrivEmail;
    private  EditText DrivEmPass;

    private FirebaseAuth mAuth;
    private ProgressDialog loadBar;

    public DriverLoginRegster_act() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_regster);

        mAuth=FirebaseAuth.getInstance();

        DrivLoginBtn=(Button) findViewById(R.id.driv_login_btn);
        DrivRegBtn=(Button) findViewById(R.id.driv_reg_btn);
        DrivStatus=(TextView) findViewById(R.id.driv_status);
        DrivRegLink=(TextView) findViewById(R.id.driv_reg_link);
        DrivEmail=(EditText) findViewById(R.id.driv_email);
        DrivEmPass=(EditText) findViewById(R.id.driv_email_pass);
        loadBar=new ProgressDialog(this);

        DrivRegBtn.setVisibility(View.INVISIBLE);
        DrivRegBtn.setEnabled(false);
        DrivRegLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrivLoginBtn.setVisibility(View.INVISIBLE);
                DrivRegLink.setVisibility(View.INVISIBLE);
                DrivStatus.setText("Driver Register");
                DrivRegBtn.setVisibility(View.VISIBLE);
                DrivRegBtn.setEnabled(true);
                DrivEmail.getText().clear();
                DrivEmPass.getText().clear();
            }
        });

        DrivRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=DrivEmail.getText().toString();
                String password=DrivEmPass.getText().toString();
                DriverRegistration(email,password);
            }
        });


        DrivLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=DrivEmail.getText().toString();
                String password=DrivEmPass.getText().toString();
                SignInDriver(email,password);
            }
        });
    }

    private void SignInDriver(String email, String password) {
        if (TextUtils.isEmpty(email)){
            Toast.makeText(DriverLoginRegster_act.this,"Please Write An Email...",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(DriverLoginRegster_act.this,"Please Write The Password...",Toast.LENGTH_SHORT).show();
        }
        else {

            loadBar.setTitle("Driver Sign In");
            loadBar.setMessage("Please Wait,While Checking Your Account");
            loadBar.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(DriverLoginRegster_act.this, "Driver Logging Successfully Completed", Toast.LENGTH_SHORT).show();
                        DrivEmail.getText().clear();
                        DrivEmPass.getText().clear();
                        loadBar.dismiss();
                        Intent driverIntent=new Intent(DriverLoginRegster_act.this,Driver_Map_Activity.class);
                        startActivity(driverIntent);
                    }
                    else {
                        Toast.makeText(DriverLoginRegster_act.this, "Login Unsuccessfull, Please Try Again", Toast.LENGTH_SHORT).show();
                        loadBar.dismiss();
                    }
                }
            });
        }
    }

    private void DriverRegistration(String email, String password) {
        if (TextUtils.isEmpty(email)){
            Toast.makeText(DriverLoginRegster_act.this,"Please Write An Email...",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(DriverLoginRegster_act.this,"Please Write The Password...",Toast.LENGTH_SHORT).show();
        }
        else {

            loadBar.setTitle("Driver Registration");
            loadBar.setMessage("Please Wait,While Registering Your Data");
            loadBar.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(DriverLoginRegster_act.this, "Driver Registration Successfully Completed", Toast.LENGTH_SHORT).show();
                        DrivEmail.getText().clear();
                        DrivEmPass.getText().clear();
                        loadBar.dismiss();
                        Intent driverIntent=new Intent(DriverLoginRegster_act.this,Driver_Map_Activity.class);
                        startActivity(driverIntent);
                    }
                    else {
                        Toast.makeText(DriverLoginRegster_act.this, "Registration Unsuccessfull, Please Try Again", Toast.LENGTH_SHORT).show();
                        loadBar.dismiss();
                    }
                }
            });
        }
    }
}