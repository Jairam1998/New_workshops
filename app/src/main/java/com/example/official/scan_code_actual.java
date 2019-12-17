package com.example.official;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scan_code_actual extends AppCompatActivity  implements ZXingScannerView.ResultHandler{


    ZXingScannerView ScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        Toast.makeText(getApplicationContext(),"Scan QR Code",Toast.LENGTH_LONG).show();
        setContentView(ScannerView);
    }

    @Override
    public void handleResult(Result result) {

        //scan_code.textView.setText(result.getText());

        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.INTENT_QR_CODE_NAME,result.getText());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

        //onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }

}
