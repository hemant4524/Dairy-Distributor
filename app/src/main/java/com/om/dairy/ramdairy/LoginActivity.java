package com.om.dairy.ramdairy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends  AppCompatActivity implements View.OnClickListener {


    public static final int RC_SIGN_IN = 001;
    private static final String TAG = LoginActivity.class.getSimpleName();


    // [START declare_auth]
    private FirebaseAuth mAuth;
// [END declare_auth]

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private EditText phoneNumberField, smsCodeVerificationField;
    private Button startVerficationButton, verifyPhoneButton;

    private String verificationid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //intialized firebase auth
        mAuth = FirebaseAuth.getInstance();

        phoneNumberField = (EditText) findViewById(R.id.phone_number_edt);
        smsCodeVerificationField = (EditText)findViewById(R.id.sms_code_edt);
        startVerficationButton = (Button) findViewById(R.id.start_auth_button);
        verifyPhoneButton = (Button) findViewById(R.id.verify_auth_button);

//        startVerficationButton.setOnClickListener(this);
//        verifyPhoneButton.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationid = s;
            }
        };
    }

    private void startPhoneNumberVerification(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks


    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid

                                smsCodeVerificationField.setError("Invalid code.");

                            }

                        }
                    }
                });
    }

    private boolean validatePhoneNumberAndCode() {
        String phoneNumber = phoneNumberField.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberField.setError("Invalid phone number.");
            return false;
        }


        return true;
    }

    private boolean validateSMSCode() {
        String code = smsCodeVerificationField.getText().toString();
        if (TextUtils.isEmpty(code)) {
            smsCodeVerificationField.setError("Enter verification Code.");
            return false;
        }

        return true;
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.start_auth_button:
                if (!validatePhoneNumberAndCode()) {
                    return;
                }
                startPhoneNumberVerification(phoneNumberField.getText().toString());
                break;
            case R.id.verify_auth_button:
                if (!validateSMSCode()) {
                    return;
                }

                verifyPhoneNumberWithCode(verificationid, smsCodeVerificationField.getText().toString());
                break;
        }
    }
}