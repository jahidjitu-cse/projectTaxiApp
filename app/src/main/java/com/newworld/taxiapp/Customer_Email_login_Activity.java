package com.newworld.taxiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Customer_Email_login_Activity extends AppCompatActivity {

    private Button CustLogInBtn;
    private  Button CustRegBtn;
    private TextView CustRegLink;
    private  TextView CustStatus;
    private EditText CustEmail;
    private EditText CustEmPass;
    private FirebaseAuth mAuth;
    private ProgressDialog loadBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_email_login);

        mAuth=FirebaseAuth.getInstance();

        CustLogInBtn =(Button) findViewById(R.id.cust_login_btn);
        CustRegBtn=(Button) findViewById(R.id.cust_reg_btn);
        CustRegLink=(TextView) findViewById(R.id.cust_reg_link);
        CustStatus=(TextView) findViewById(R.id.cust_status);
        CustEmail=(EditText) findViewById(R.id.cust_email);
        CustEmPass=(EditText) findViewById(R.id.cust_email_pass);
        loadBar=new ProgressDialog(this);

        CustRegBtn.setVisibility(View.INVISIBLE);
        CustRegBtn.setEnabled(false);
        CustRegLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustLogInBtn.setVisibility(View.INVISIBLE);
                CustRegLink.setVisibility(View.INVISIBLE);
                CustStatus.setText("Customer Register");
                CustRegBtn.setVisibility(View.VISIBLE);
                CustRegBtn.setEnabled(true);
                CustEmail.getText().clear();
                CustEmPass.getText().clear();
            }
        });

        CustRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=CustEmail.getText().toString();
                String password=CustEmPass.getText().toString();
                CustomerRegistration(email,password);
            }
        });

        CustLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=CustEmail.getText().toString();
                String password=CustEmPass.getText().toString();
                SignInCustomer(email,password);
            }
        });
    }

    private void SignInCustomer(String email, String password) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(Customer_Email_login_Activity.this, "Please Write An Email", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(Customer_Email_login_Activity.this, "Please Write The Password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadBar.setTitle("Customer Sign In");
            loadBar.setMessage("Please Wait,While Checking Your Account");
            loadBar.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Customer_Email_login_Activity.this, "Customer Logging Successfully Completed", Toast.LENGTH_SHORT).show();
                        CustEmail.getText().clear();
                        CustEmPass.getText().clear();
                        loadBar.dismiss();
                        Intent customerIntent= new Intent(Customer_Email_login_Activity.this,Customer_Map_Activity.class);
                        startActivity(customerIntent);
                    }
                    else {
                        Toast.makeText(Customer_Email_login_Activity.this, "LogIn Unsuccessful,Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void CustomerRegistration(String email, String password) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(Customer_Email_login_Activity.this, "Please Write An Email", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(Customer_Email_login_Activity.this, "Please Write The Password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadBar.setTitle("Customer Registration");
            loadBar.setMessage("Please Wait,While Registering Your Data");
            loadBar.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Customer_Email_login_Activity.this, "Customer Registration Successfully Completed", Toast.LENGTH_SHORT).show();
                        CustEmail.getText().clear();
                        CustEmPass.getText().clear();
                        loadBar.dismiss();
                        Intent customerIntent= new Intent(Customer_Email_login_Activity.this,Customer_Map_Activity.class);
                        startActivity(customerIntent);
                    }
                    else {
                        Toast.makeText(Customer_Email_login_Activity.this, "Registration Unsuccessful,Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}