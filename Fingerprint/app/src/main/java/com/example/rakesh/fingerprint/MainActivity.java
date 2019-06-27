package com.example.rakesh.fingerprint;

import android.hardware.fingerprint.FingerprintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.multidots.fingerprintauth.AuthErrorCodes;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

public class MainActivity extends AppCompatActivity implements FingerPrintAuthCallback {
    FingerPrintAuthHelper mFingerPrintAuthHelper;
int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //start finger print authentication
        mFingerPrintAuthHelper.startAuth();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mFingerPrintAuthHelper.stopAuth();
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        Toast.makeText(this, "Cannot recognize", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNoFingerPrintRegistered() {

    }

    @Override
    public void onBelowMarshmallow() {
        Toast.makeText(this, "Your device doesn't support", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        count++;
        Toast.makeText(this, ""+count99, Toast.LENGTH_SHORT).show();
   // onstart();.

        onResume();
    //    onStart();
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        switch (errorCode) {    //Parse the error code for recoverable/non recoverable error.
            case AuthErrorCodes.CANNOT_RECOGNIZE_ERROR:
                Toast.makeText(this, "Cannot recognize", Toast.LENGTH_SHORT).show();
                //Cannot recognize the fingerprint scanned.
                break;
            case AuthErrorCodes.NON_RECOVERABLE_ERROR:
                //This is not recoverable error. Try other options for user authentication. like pin, password.
                break;
            case AuthErrorCodes.RECOVERABLE_ERROR:
                //Any recoverable error. Display message to the user.
                break;
        }
    }
}
