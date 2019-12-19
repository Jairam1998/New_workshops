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

                String id = data.getStringExtra(Constants.INTENT_QR_CODE_NAME);

                //TODO remove this line once actual QR codes are available
                //id = "CBW7282756";

                Log.d(Constants.LOGTAG,"SCANNED:"+id);

                ProgressBar progressBar = findViewById(R.id.progressBar);
                PostRequest request = new PostRequest(getResponseHandler(),progressBar,Constants.SERVICE_GET_DETAILS);
                Properties postParams = new Properties();
                postParams.put(Constants.REQUEST_ID_NAME,id);
                request.execute(postParams);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private PostResponseHandler getResponseHandler() {

        return  new PostResponseHandler() {
            @Override
            public void handlePostResponse(String response) {

                try {

                    String json = Utils.getDataJsonString(getApplicationContext(),response);

                    if (json != null) {

                        Properties obj = Utils.getJSONObject(new JSONObject(json));
                        if (obj.get(Constants.RESPONSE_EVENT_LIST_NAME) != null) {

                            Intent intent = new Intent(getApplicationContext(),EventsViewActivity.class);

                            String workshopsListJson = (String)obj.get(Constants.RESPONSE_EVENT_LIST_NAME);
                            intent.putExtra(Constants.INTENT_WORKSHOP_LIST_NAME,workshopsListJson);
                            Log.d(Constants.LOGTAG,workshopsListJson);

                            String participantDetailsJson = (String)obj.get(Constants.RESPONSE_PARTICIPANT_DETAILS_NAME);
                            intent.putExtra(Constants.INTENT_PARTICIPANT_DETAILS_NAME,participantDetailsJson);
                            Log.d(Constants.LOGTAG,participantDetailsJson);

                            startActivity(intent);

                        } else {

                            Intent intent = new Intent(getApplicationContext(),BulkWorkshopViewActivity.class);

                            String participantsListJson = (String)obj.get(Constants.RESPONSE_PARTICIPANT_LIST_NAME);
                            intent.putExtra(Constants.INTENT_PARTICIPANT_LIST_NAME,participantsListJson);

                            String workshopDetailsJson = (String)obj.get(Constants.RESPONSE_WORKSHOP_DETAILS_NAME);
                            intent.putExtra(Constants.INTENT_WORKSHOP_DETAILS_NAME,workshopDetailsJson);
                        }
                    }

                } catch (Exception e) {

                    Log.e(Constants.LOGTAG,"Exception",e);
                }
            }
        };


    }


    public void register(View view) {

        Intent intent = new Intent(getApplicationContext(),Register_ws_events.class);
        intent.putExtra(Constants.INTENT_ORG_ID_NAME,organizerId);
        startActivity(intent);

    }

}
