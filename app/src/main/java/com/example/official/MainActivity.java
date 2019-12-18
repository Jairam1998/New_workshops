package com.example.official;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=100;
    Button button;

    //TODO
    boolean cameraAccepted = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        String[] perms = {"android.permission.CAMERA"};

        int permsRequestCode = 200;
        requestPermissions(perms, permsRequestCode);*/

        boolean b = new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                if (cameraAccepted) {
                    Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Camera permission required",Toast.LENGTH_SHORT).show();
                }
            }
        },SPLASH_TIME_OUT);


    }
/*

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){

            case 200:

                //boolean locationAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                cameraAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;

                break;

        }

    }*/
}
