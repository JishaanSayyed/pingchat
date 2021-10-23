package com.example.ping.Acitivites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ping.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class verificationActivity extends AppCompatActivity {
    EditText phoneNumber;
    Button getOTP;
    FirebaseAuth auth;
  // private final String ctryCode = "+91";
   //FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        auth = FirebaseAuth.getInstance();
//******************************************
        if(auth.getCurrentUser() != null ){
            Intent intent = new Intent(verificationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
//******************************************

        getSupportActionBar().hide();
        //auth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);


        TextView textView = findViewById(R.id.text_View2);
        String text = "We will send you an One Time Password on this mobile number";
        SpannableString ss = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan fcB = new ForegroundColorSpan(Color.BLACK);
        ss.setSpan(fcB, 20, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(boldSpan, 20, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);


        phoneNumber = findViewById(R.id.phoneNumber);
        getOTP = findViewById(R.id.getOTP);

        final ProgressBar progressBar = findViewById(R.id.progressSend);

        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!phoneNumber.getText().toString().trim().isEmpty()) {
                    if (phoneNumber.getText().toString().trim().length() == 13) {

                        progressBar.setVisibility(View.VISIBLE);
                        getOTP.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                phoneNumber.getText().toString(),
                                20,
                                TimeUnit.SECONDS,
                                verificationActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                        progressBar.setVisibility(View.GONE);
//                                        getOTP.setVisibility(View.VISIBLE);
//                                        Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
//                                        intent.putExtra("mobile", phoneNumber.getText().toString());
//                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                        progressBar.setVisibility(View.VISIBLE);
                                        getOTP.setVisibility(View.INVISIBLE);
                                        progressBar.setVisibility(View.INVISIBLE);
                                        getOTP.setVisibility(View.VISIBLE);

                                        Toast.makeText(verificationActivity.this,"Verification Failed", Toast.LENGTH_SHORT).show();

                                    }
//                                    private void isRegisteredUser(){
//                                        final String userId = ctryCode + phoneNumber;
//                                        db = FirebaseFirestore.getInstance();
//                                        db.collection("users")
//                                                .whereEqualTo("phone", userId)
//                                                .get()
//                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                        try{
//                                                            if(task.isSuccessful()){
//                                                                saveToken(userId);
//
//                                                                for (QueryDocumentSnapshot document : task.getResult()){
//                                                                    if(document.getId().equalsIgnoreCase(userId)){
//                                                                        Intent intent = new Intent(verificationActivity.this, MainActivity.class);
//                                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                                        startActivity(intent);
//                                                                        finish();
//                                                                        return;
//                                                                    }
//                                                                }
//                                                            }else {Toast.makeText(verificationActivity.this,"Database Error.", Toast.LENGTH_SHORT).show(); }
//                                                        } catch (Exception e) {
//                                                        }
//
//                                                    }
//                                                });
//
//                                    }
//                                    private void saveToken(final String userId){
//                                        FirebaseInstanceId.getInstance().getInstanceId()
//                                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                                                        if(!task.isSuccessful()){
//                                                            return;
//                                                        }
//                                                        String token = task.getResult().getToken();
//                                                        FirebaseFirestore.getInstance().collection("user").document(userId).update("token", token);
//
//                                                    }
//                                                });
//
//                                    }

                                    @Override
                                    public void onCodeSent(@NonNull @NotNull String backEndOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(backEndOTP, forceResendingToken);
                                        progressBar.setVisibility(View.GONE);
                                        getOTP.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                                        intent.putExtra("mobile", phoneNumber.getText().toString());
                                        intent.putExtra("backEndOTP", backEndOTP);
                                        startActivity(intent);
                                        Toast.makeText(verificationActivity.this,"Sending OTP...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );

                    } else {
                        Toast.makeText(verificationActivity.this, "Please Enter Correct Number.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(verificationActivity.this, "Please Enter Mobile Number.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}