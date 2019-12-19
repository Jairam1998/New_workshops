package com.example.official;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Register_ws_events extends AppCompatActivity {

    int orgId;

    Button mOrder4, mOrder5, mEvent, mGo;

    TextView mItemSelected;
    String[] jan4WorkshopNames, jan5WorkshopNames, eventNames;
    int [] jan4WorkshopPrices, jan5WorkshopPrices;
    int [] jan4WorkshopIds, jan5WorkshopIds, eventIds;


    ArrayList<Integer> mUserItems = new ArrayList<>();

    String jan4 = "", jan5 = "", event = "";

    private int jan4Selected = -1, jan5Selected = -1, eventSelected = -1;
    int jan4Price = 0, jan5Price = 0, eventPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        orgId = getIntent().getIntExtra(Constants.INTENT_ORG_ID_NAME,-1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ws_events);

        mOrder4 = (Button) findViewById(R.id.btnOrder4);
        mOrder5 = (Button) findViewById(R.id.btnOrder5);
        mEvent = (Button) findViewById(R.id.btnEvents);

        mGo = findViewById(R.id.btnGo);
        mGo.setEnabled(false);

        jan4WorkshopNames = getResources().getStringArray(R.array.jan4_workshop_names);
        jan5WorkshopNames = getResources().getStringArray(R.array.jan5_workshop_names);
        eventNames = getResources().getStringArray(R.array.event_names);

        jan4WorkshopIds = getResources().getIntArray(R.array.jan4_workshop_ids);
        jan5WorkshopIds = getResources().getIntArray(R.array.jan5_workshop_ids);
        eventIds = getResources().getIntArray(R.array.event_ids);

        jan4WorkshopPrices = getResources().getIntArray(R.array.jan4_workshop_prices);
        jan5WorkshopPrices = getResources().getIntArray(R.array.jan5_workshop_prices);

        mItemSelected = (TextView) findViewById(R.id.tvItemSelected);
        EditText editText = findViewById(R.id.username);

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                String tmp = s.toString();
                if (tmp.length() == 4) {

                    PostResponseHandler handler = new PostResponseHandler() {
                        @Override
                        public void handlePostResponse(String response) {
                            try {

                                String responseObj = Utils.getDataJsonString(getApplicationContext(),response);
                                Properties obj = Utils.getJSONObject(new JSONObject(responseObj));

                                String participantDetailsJson = (String)obj.get(Constants.RESPONSE_PARTICIPANT_DETAILS_NAME);
                                TextView textView = findViewById(R.id.participantExists);

                                if (Constants.RESPONSE_NULL_VALUE.equals(participantDetailsJson)) {
                                    textView.setText("Participant does not exist");
                                    mGo.setEnabled(false);
                                } else {
                                    Properties participant = Utils.getJSONObject(new JSONObject(participantDetailsJson));
                                    String email = (String)participant.get(Constants.DB_PARTICIPANT_EMAIL_NAME);
                                    textView.setText(email);
                                    mGo.setEnabled(true);
                                }

                                String eventListJson = (String)obj.get(Constants.RESPONSE_EVENT_LIST_NAME);
                                List<Properties> eventList = Utils.getJSONObjects(eventListJson);

                                ArrayList<Integer> dates = new ArrayList<>();
                                for (Properties props : eventList) {
                                    String date = (String)props.get(Constants.DB_EVENT_DATE_NAME);
                                    int d = Integer.parseInt(date.substring(0,2));
                                    dates.add(d);
                                    Log.d(Constants.LOGTAG,"DATE:"+d);
                                }

                                int eventsDate = getResources().getInteger(R.integer.events_date);

                                if (dates.contains(4)) mOrder4.setEnabled(false);
                                else mOrder4.setEnabled(true);

                                if (dates.contains(5)) mOrder5.setEnabled(false);
                                else mOrder5.setEnabled(true);

                                if (dates.contains(eventsDate)) mEvent.setEnabled(false);
                                else mEvent.setEnabled(true);

                            } catch (Exception e) { Log.e(Constants.LOGTAG,"Exception",e); }
                        }
                    };

                    ProgressBar progressBar = findViewById(R.id.pProgressBar);
                    PostRequest request = new PostRequest(handler,progressBar,Constants.SERVICE_GET_DETAILS);

                    String participantId = ((EditText)findViewById(R.id.username)).getText().toString();
                    Properties postParams = new Properties();
                    postParams.put(Constants.REQUEST_ID_NAME,participantId);

                    request.execute(postParams);
                }
            }

        });

        mOrder4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Register_ws_events.this);
                mBuilder.setTitle(R.string.jan4_workshops_title);
                mBuilder.setSingleChoiceItems(jan4WorkshopNames, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        jan4Selected = jan4WorkshopIds[position];
                        jan4 = jan4WorkshopNames[position];
                        jan4Price = jan4WorkshopPrices[position];
                        showChosenEvents();
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        jan4Selected = -1;
                        jan4Price = 0;
                        jan4 = "";
                        showChosenEvents();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });

        mOrder5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Register_ws_events.this);
                mBuilder.setTitle(R.string.jan5_workshops_title);
                mBuilder.setSingleChoiceItems(jan5WorkshopNames, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        jan5Selected = jan5WorkshopIds[position];
                        jan5Price = jan5WorkshopPrices[position];
                        jan5 = jan5WorkshopNames[position];
                        showChosenEvents();
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        jan5Selected = -1;
                        jan5Price = 0;
                        jan5 = "";
                        showChosenEvents();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });

        mEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Register_ws_events.this);
                mBuilder.setTitle(R.string.jan5_workshops_title);
                mBuilder.setSingleChoiceItems(eventNames, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        eventSelected = eventIds[position];
                        eventPrice = 259;
                        event = eventNames[position];
                        showChosenEvents();
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        eventSelected = -1;
                        event = "";
                        eventPrice = 0;
                        showChosenEvents();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });


    }

    private void showChosenEvents() {
        String tmp = "";
        if (jan4.length() > 0) tmp += jan4 + " (" + jan4Price + ")\n";
        if (jan5.length() > 0) tmp += jan5 + " (" + jan5Price + ")\n";
        if (event.length() > 0) tmp += event + " (" + eventPrice + ")\n";
        if (tmp.length() > 0) {
            int sum = jan4Price + jan5Price + eventPrice;
            tmp += "Total (" + sum + ")";
        }
        mItemSelected.setText(tmp);
    }

    private PostResponseHandler getResponseHandler() {


        return new PostResponseHandler() {
            @Override
            public void handlePostResponse(String response) {

                try {

                    Properties responseObj = Utils.getJSONObject(new JSONObject(response));

                    String message = (String)responseObj.get(Constants.RESPONSE_MESSAGE_NAME);
                    String status = (String)responseObj.get(Constants.RESPONSE_STATUS_NAME);

                    if (Constants.RESPONSE_SUCCESS_VALUE.equals(status)) {
                        if (jan4Selected != -1) mOrder4.setEnabled(false);
                        if (jan5Selected != -1) mOrder5.setEnabled(false);
                        if (eventSelected != -1) mEvent.setEnabled(false);
                    }

                    Toast.makeText(getApplicationContext(),status + "! " + message,Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Log.d(Constants.LOGTAG,"Exception",e);
                }

            }
        };
    }

    public void btnGO(View view) {

        EditText editText = findViewById(R.id.username);
        String participantId = editText.getText().toString();

        if (jan4Selected == -1 && jan5Selected == -1 && eventSelected == -1) {
            Toast.makeText(getApplicationContext(), "No events selected", Toast.LENGTH_LONG).show();
        } else {

            ProgressBar progressBar = findViewById(R.id.progressBar);

            Properties postParams = new Properties();
            if (jan4Selected != -1) {
                postParams.put(Constants.REQUEST_JAN4_WORKSHOP_ID_NAME,jan4Selected+"");
                Log.d(Constants.LOGTAG,"WORKSHOPID:"+jan4Selected);
            }
            if (jan5Selected != -1) {
                postParams.put(Constants.REQUEST_JAN5_WORKSHOP_ID_NAME,jan5Selected+"");
                Log.d(Constants.LOGTAG,"WORKSHOPID:"+jan5Selected);
            }
            if (eventSelected != -1) {
                postParams.put(Constants.REQUEST_EVENT_ID_NAME,eventSelected+"");
                Log.d(Constants.LOGTAG,"EVENTID:"+eventSelected);
            }
            postParams.put(Constants.REQUEST_ORG_ID_NAME,orgId);
            postParams.put(Constants.REQUEST_PARTICIPANT_ID_NAME,participantId);

            PostRequest request = new PostRequest(getResponseHandler(),progressBar,Constants.SERVICE_PUT_DETAILS);
            request.execute(postParams);
        }

    }

}
