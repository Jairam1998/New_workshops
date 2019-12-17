package com.example.official;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Properties;

public class organiser extends AppCompatActivity {


    Button register,scan;
    static TextView textView;

    private int organizerId, access;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organiser);

        register = (Button)findViewById(R.id.org_register);
        scan = (Button)findViewById(R.id.org_scan);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register_ws_events.class));
            }
        });
        textView = (TextView)findViewById(R.id.org_result_text);

        organizerId = getIntent().getIntExtra(Constants.INTENT_ORG_ID_NAME,-1);
        access = getIntent().getIntExtra(Constants.INTENT_ORG_ACCESS_NAME,-1);

        Log.d(Constants.LOGTAG,organizerId + " " + access);

        if (access == 0) {
            Toast.makeText(getApplicationContext(),"Logged in as a volunteer",Toast.LENGTH_LONG).show();
            register.setVisibility(View.INVISIBLE);
        }
        else Toast.makeText(getApplicationContext(),"Logged in as an organizer",Toast.LENGTH_LONG).show();

    }


    public void scanQR(View view) {

        Intent intent = new Intent(getApplicationContext(),scan_code_actual.class);
        startActivityForResult(intent,1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == 1) {

            if(resultCode == Activity.RESULT_OK){

                String result = data.getStringExtra(Constants.INTENT_QR_CODE_NAME);

                //TODO remove this line once actual QR codes are available
                //result = "CBW7282756";

                if (result.charAt(0) == 'P') {

                    //TODO handle scanning participant ID

                } else {

                    startWorkshopsViewActivity(result);
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void startWorkshopsViewActivity(String orderId) {

        PostResponseHandler handler = new PostResponseHandler() {
            @Override
            public void handlePostResponse(String response) {

                Properties responseObj = null;
                Log.d(Constants.LOGTAG,"JSONResponse: " + response);

                try {
                    responseObj = Utils.getJSONObject(new JSONObject(response));
                    Intent intent = new Intent(getApplicationContext(),WorkshopsViewActivity.class);

                    String participantDetails = responseObj.get(Constants.RESPONSE_PARTICIPANT_DETAILS_NAME).toString();
                    intent.putExtra(Constants.INTENT_PARTICIPANT_DETAILS_NAME,participantDetails);

                    String workshopsList = responseObj.get(Constants.RESPONSE_WORKSHOPS_LIST_NAME).toString();
                    intent.putExtra(Constants.INTENT_WORKSHOP_LIST_NAME,workshopsList);

                    startActivity(intent);

                } catch (Exception e) { Log.e(Constants.LOGTAG,"Exception",e); }

            }
        };


        ProgressBar progressBar = findViewById(R.id.progressBar);
        PostRequest request = new PostRequest(handler,progressBar,Constants.SERVICE_GET_DETAILS_BY_ORDER_ID);

        Properties postParams = new Properties();
        postParams.put(Constants.REQUEST_ORDER_ID_NAME,orderId);
        request.execute(postParams);

    }

    //private String getParticipantIdByOrderId

    public void register(View view) {


    }

}
