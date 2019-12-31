package com.example.official;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONObject;

import java.util.Properties;
import java.util.Scanner;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class UploadScoresActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    private int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upload_scores);

        ProgressBar progressBar = findViewById(R.id.frameLayout).findViewById(R.id.uProgressBar);
        progressBar.setVisibility(View.INVISIBLE);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    Constants.CAMERA_PERMISSION_REQUEST);

        } else initialize();

    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
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

    private void initialize() {


        scannerView = findViewById(R.id.scan_qr_upload);
        scannerView.setResultHandler(this);

        selected = 1;
        setupRadioButtons();
    }

    private void setupRadioButtons() {

        RadioButton radioButton = findViewById(R.id.event1);
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                selected = 1;
            }
        });
        radioButton.setChecked(true);

        radioButton = findViewById(R.id.event2);
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                selected = 2;
            }
        });

        radioButton = findViewById(R.id.event3);
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                selected = 3;
            }
        });
    }

    @Override
    public void handleResult(Result result) {

        String scannedText = result.getText();
        TextView textView = findViewById(R.id.scanned_id);
        textView.setText(scannedText);

        resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        pause();
    }

    private void pause(){
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resume();
    }

    private void resume() {
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    public void btnUpload(View view) {

        ProgressBar progressBar = findViewById(R.id.uProgressBar);
        PostRequest request = new PostRequest(getResponseHandler(),progressBar,Constants.SERVICE_UPLOAD_SCORE);

        Properties postParams = new Properties();

        String scannedId = ((TextView)findViewById(R.id.scanned_id)).getText().toString();
        String score = ((EditText)findViewById(R.id.scoreEditText)).getText().toString();

        postParams.put(Constants.REQUEST_EVENT_ID_NAME,selected);
        postParams.put(Constants.REQUEST_TICKET_ID_NAME,scannedId);
        postParams.put(Constants.REQUEST_SCORE_NAME,score);

        pause();
        //scannerView.setVisibility(View.INVISIBLE);

        request.execute(postParams);
    }

    private PostResponseHandler getResponseHandler() {

        return new PostResponseHandler() {
            @Override
            public void handlePostResponse(String response) {

                Log.d(Constants.LOGTAG,"RESPONSE:"+response);

                if (response == null) {
                    Toast.makeText(getApplicationContext(),"Failed to connect to server",Toast.LENGTH_SHORT).show();
                } else {

                    try {
                        Properties obj = Utils.getJSONObject(new JSONObject(response));
                        String status = (String)obj.get(Constants.RESPONSE_STATUS_NAME);
                        String message = (String)obj.get(Constants.RESPONSE_MESSAGE_NAME);
                        Toast.makeText(getApplicationContext(),status + "! " + message,Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Log.e(Constants.LOGTAG, "Exception", e);
                    }

                }

                //scannerView.setVisibility(View.VISIBLE);
                resume();
            }
        };

    }
}
