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

    Button login;
    EditText username,pin;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.login);
        username = (EditText)findViewById(R.id.username);
        pin = (EditText)findViewById(R.id.pin);
        /*
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticate(username.toString(),pin.toString());
            }
        });*/
    }


    public void authenticate(View view) {


        /*
        if(username.equalsIgnoreCase("Jayaram") ) {
            if (pin.equalsIgnoreCase("podapunda")) {
                startActivity(new Intent(getApplicationContext(), organiser.class));

            }
        }
        else{

            System.out.println("wrong credentials");
        }*/


        ProgressBar progressBar = findViewById(R.id.progressBar);
        PostRequest request = new PostRequest(this,progressBar,Constants.SERVICE_CHECK_LOGIN);

        String usernameString = this.username.getText().toString();
        String passwordString = this.pin.getText().toString();

        Properties postParams = new Properties();
        postParams.put(Constants.REQUEST_USERNAME_NAME,usernameString);
        postParams.put(Constants.REQUEST_PASSWORD_NAME,passwordString);

        request.execute(postParams);

    }

    public void handlePostResponse(String jsonResponse) {

        String message = "";
        Properties response = null;



        try {
            response = Utils.getJSONObject(new JSONObject(jsonResponse));
            message = response.get(Constants.RESPONSE_STATUS_NAME).toString();

        } catch (Exception e) {
            Log.e(Constants.LOGTAG,"Exception",e);
        }

        if (Constants.RESPONSE_SUCCESS_VALUE.equals(message)) {

            Intent intent = new Intent(getApplicationContext(),organiser.class);

            int access = Integer.parseInt(response.get(Constants.RESPONSE_ORG_ACCESS_NAME).toString());
            intent.putExtra(Constants.INTENT_ORG_ACCESS_NAME,access);

            int organizerId = Integer.parseInt(response.get(Constants.RESPONSE_ORG_ID_NAME).toString());
            intent.putExtra(Constants.INTENT_ORG_ID_NAME,organizerId);

            startActivity(intent);

        } else {

            Toast.makeText(getApplicationContext(),"Invalid username or password",Toast.LENGTH_LONG).show();
        }
    }
}
