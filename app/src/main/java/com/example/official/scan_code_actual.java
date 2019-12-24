package com.example.official;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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

        String message = getIntent().getStringExtra(Constants.INTENT_MESSAGE_NAME);

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_scan_code_actual);

        scannerView = findViewById(R.id.scan_view);
        scannerView.setResultHandler(this);
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
