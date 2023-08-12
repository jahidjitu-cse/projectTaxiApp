package com.newworld.taxiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Customer_phone_login_act extends AppCompatActivity {

    private CountryCodePicker cpp;
    private EditText phoneEdittext;
    private PinView pinView;
    private TextView textView;
    private ConstraintLayout phoneLayout;
    private String Selected_country_code="+880";
    private static final int CREDENTIAL_PICKER_REQUEST =120 ;
    private ProgressBar progressBar;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResentToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_phone_login);


        cpp=(CountryCodePicker) findViewById(R.id.cpp);
        phoneEdittext= (EditText) findViewById(R.id.phoneNum);
        pinView=(PinView) findViewById(R.id.pinView);
        phoneLayout= (ConstraintLayout) findViewById(R.id.phoneLayout);
        textView=(TextView) findViewById(R.id.num);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        mAuth= FirebaseAuth.getInstance();

//country code
        cpp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                Selected_country_code=cpp.getSelectedCountryCodeWithPlus();
            }
        });

//text watcher
        phoneEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()==10){
                    sendOtp();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//pinview
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()==6){
                    //Toast.makeText(phone_login_activity.this, "6 Digit OTP", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.VISIBLE);


                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(mVerificationId,pinView.getText().toString().trim());
                    signInWithAuthCredential(credential);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//auto phone select api
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();


        PendingIntent intent = Credentials.getClient(Customer_phone_login_act.this).getHintPickerIntent(hintRequest);
        try
        {
            startIntentSenderForResult(intent.getIntentSender(), CREDENTIAL_PICKER_REQUEST, null, 0, 0, 0,new Bundle());
        }
        catch (IntentSender.SendIntentException e)
        {
            e.printStackTrace();
        }


//otp callbacks
        callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code=phoneAuthCredential.getSmsCode();
                if(code!=null){
                    pinView.setText(code);

                    signInWithAuthCredential(phoneAuthCredential);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Customer_phone_login_act.this, "Something Went Wrong,Try Again", Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
                phoneLayout.setVisibility(View.VISIBLE);
                pinView.setVisibility(View.GONE);
            }
            @Override
            public void onCodeSent(@NonNull String verificationID,@NonNull PhoneAuthProvider.ForceResendingToken token){
                super.onCodeSent(verificationID,token);

                mVerificationId=verificationID;
                mResentToken=token;
                Toast.makeText(Customer_phone_login_act.this, "6 Digit OTP Sent", Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
                phoneLayout.setVisibility(View.GONE);
                pinView.setVisibility(View.VISIBLE);
            }
        };
    }



    private void sendOtp() {
        progressBar.setVisibility(View.VISIBLE);

        String phoneNumber= Selected_country_code+phoneEdittext.getText().toString();

        PhoneAuthOptions options= PhoneAuthOptions.newBuilder(mAuth)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setPhoneNumber(phoneNumber)
                .setActivity(Customer_phone_login_act.this)
                .setCallbacks(callbacks).build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    //
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_OK)
        {
            Credential credentials = data.getParcelableExtra(Credential.EXTRA_KEY);
            phoneEdittext.setText(credentials.getId().substring(3));
            //Toast.makeText(this, "MOB"+credentials.getId().substring(3), Toast.LENGTH_SHORT).show();

        }
        else if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE)
        {
            Toast.makeText(Customer_phone_login_act.this, "No Phone Numbers Found", Toast.LENGTH_LONG).show();
        }
    }

    private void signInWithAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Customer_phone_login_act.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(Customer_phone_login_act.this, Customer_Map_Activity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(Customer_phone_login_act.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(Customer_phone_login_act.this, CustomerLoginResister_act.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}