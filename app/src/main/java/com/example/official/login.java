package com.example.official;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Properties;

public class login extends AppCompatActivity implements PostResponseHandler {


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }


    public void authenticate(View view) {

        ProgressBar progressBar = findViewById(R.id.progressBar);
        PostRequest request = new PostRequest(this,progressBar,Constants.SERVICE_CHECK_LOGIN);

        EditText editText = findViewById(R.id.username);
        String usernameString = editText.getText().toString();

        editText = findViewById(R.id.pin);
        String passwordString = editText.getText().toString();

        Properties postParams = new Properties();
        postParams.put(Constants.REQUEST_USERNAME_NAME,usernameString);
        postParams.put(Constants.REQUEST_PASSWORD_NAME,passwordString);

        request.execute(postParams);

    }

    public void handlePostResponse(String jsonResponse) {

        Log.d(Constants.LOGTAG,"JSONRESPONSE:"+jsonResponse);

        String status = null;
        Properties response = null;
        String message = null;


        if (jsonResponse.length() > 0) {

            try {
                response = Utils.getJSONObject(new JSONObject(jsonResponse));
                status = (String)response.get(Constants.RESPONSE_STATUS_NAME);
                message = (String)response.get(Constants.RESPONSE_MESSAGE_NAME);

            } catch (Exception e) {
                Log.e(Constants.LOGTAG,"Exception",e);
            }

            if (Constants.RESPONSE_SUCCESS_VALUE.equals(status)) {

                Intent intent = new Intent(getApplicationContext(), organiser.class);

                int access = Integer.parseInt((String)response.get(Constants.RESPONSE_ORG_ACCESS_NAME));
                intent.putExtra(Constants.INTENT_ORG_ACCESS_NAME, access);

                int organizerId = Integer.parseInt((String)response.get(Constants.RESPONSE_ORG_ID_NAME));
                intent.putExtra(Constants.INTENT_ORG_ID_NAME, organizerId);

                startActivity(intent);

            } else {

                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }

        } else {

            Toast.makeText(getApplicationContext(),"Failed to connect to server :(",Toast.LENGTH_LONG).show();
        }
    }
}
