package com.example.official;

import android.Manifest;
import android.app.Activity;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class CameraPermission {

    public static void request(Activity activity, int code) {

        // Permission is not granted
        // Should we show an explanation?
        Log.d(Constants.LOGTAG,"inside function");


        /*
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.CAMERA)) {

            Log.d(Constants.LOGTAG,"explanation needed");
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {

            Log.d(Constants.LOGTAG,"no explanation needed");
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA},code);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }*/

    }
}
