package com.example.ping.Acitivites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {
    EditText inputOne, inputTwo, inputThree, inputFour, inputFive, inputSix;
    String getOTPBackEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);



        getSupportActionBar().hide();



        inputOne = findViewById(R.id.inputOne);
        inputTwo = findViewById(R.id.inputTwo);
        inputThree = findViewById(R.id.inputThree);
        inputFour = findViewById(R.id.inputFour);
        inputFive = findViewById(R.id.inputFive);
        inputSix = findViewById(R.id.inputSix);

        TextView textView = findViewById(R.id.phoneLabel);
        textView.setText(String.format("Enter OTP send to " + getIntent().getStringExtra("mobile")));

        getOTPBackEnd = getIntent().getStringExtra("backEndOTP");
        final Button button = findViewById(R.id.button);
        final ProgressBar progressBar = findViewById(R.id.progressOTP);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!inputOne.getText().toString().trim().isEmpty() && !inputTwo.getText().toString().trim().isEmpty()) {
                    String OTP = inputOne.getText().toString() +
                            inputTwo.getText().toString() +
                            inputThree.getText().toString() +
                            inputFour.getText().toString() +
                            inputFive.getText().toString() +
                            inputSix.getText().toString();
                    if (getOTPBackEnd != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        button.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getOTPBackEnd, OTP);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.GONE);
                                        button.setVisibility(View.VISIBLE);
                                        Toast.makeText(OTPActivity.this, "Logged In", Toast.LENGTH_SHORT).show();

                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(OTPActivity.this, ProfileActivity.class);
                                            startActivity(intent);
                                            finishAffinity();



                                        } else { Toast.makeText(OTPActivity.this, "Please Enter Correct OTP.", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

                    }else {Toast.makeText(OTPActivity.this, "Please Check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }

                }else {Toast.makeText(OTPActivity.this, "Please Enter All Numbers.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        nextInput();

        TextView resendOTP = findViewById(R.id.resendOTP);

        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        OTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(OTPActivity.this,"Verification Failed", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull @NotNull String newBackEndOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(newBackEndOTP, forceResendingToken);
                                getOTPBackEnd = newBackEndOTP;

                                Toast.makeText(OTPActivity.this,"OTP Send Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

            }
        });

    }

    private void nextInput() {
        inputOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputTwo.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputThree.requestFocus();
                } else {
                    inputOne.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputFour.requestFocus();
                } else {
                    inputTwo.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputFive.requestFocus();
                } else {
                    inputThree.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputFive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputSix.requestFocus();
                } else {
                    inputFour.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputSix.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputSix.requestFocus();
                } else {
                    inputFive.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}