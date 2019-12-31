package com.example.official;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scan_code_actual extends AppCompatActivity  implements ZXingScannerView.ResultHandler{


    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_actual);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    Constants.CAMERA_PERMISSION_REQUEST);

        } else initialize();


    }



    private void initialize() {

        String message = getIntent().getStringExtra(Constants.INTENT_MESSAGE_NAME);

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

        scannerView = findViewById(R.id.scan_view);
        scannerView.setResultHandler(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull  String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.CAMERA_PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    initialize();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }



    @Override
    public void handleResult(Result result) {

        //scan_code.textView.setText(result.getText());
        EditText editText = findViewById(R.id.code);
        editText.setText(result.getText());
        scannerView.resumeCameraPreview(this);

    }


    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    public void btnDone(View view) {

        Intent returnIntent = new Intent();
        EditText editText = findViewById(R.id.code);
        String result = editText.getText().toString();
        returnIntent.putExtra(Constants.INTENT_QR_CODE_NAME,result);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

}
